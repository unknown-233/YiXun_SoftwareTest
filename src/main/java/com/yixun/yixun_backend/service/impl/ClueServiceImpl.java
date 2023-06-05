package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.CluesReportMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
* @author hunyingzhong
* @description 针对表【yixun_clue】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue>
    implements ClueService{
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private CluesReportMapper cluesReportMapper;
    @Resource
    private OssUploadService ossUploadService;
    public ClueDTO cutIntoClueDTO(Clue clue){
        ClueDTO dto=new ClueDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setClueId(clue.getClueId());
        dto.setClueContent(clue.getClueContent());
        dto.setClueDate(TimeTrans.myToString(clue.getClueDate()));
        dto.setSearchinfoId(clue.getSearchinfoId());
        dto.setWhetherConfirmed(clue.getWhetherConfirmed());
        if(clue.getVolActId()!=null){
            dto.setVolActId(clue.getVolActId());
        }
        else{
            Integer volActId = null;
        }
        return dto;
    }

    public List<ClueDTO> cutIntoClueDTOList(List<Clue> clueList){
        List<ClueDTO> dtoList=new ArrayList<>();
        for(Clue clue : clueList){
            ClueDTO dto=cutIntoClueDTO(clue);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //删除寻人线索（连锁删除相关线索举报）
    public Boolean deleteClue(int clueID)
    {
        try{
            Clue clue=clueMapper.selectById(clueID);
            clue.setIsactive("N");
            clueMapper.updateById(clue);
            QueryWrapper<CluesReport> wrapper = new QueryWrapper<CluesReport>();
            wrapper.eq("CLUE_ID",clueID);
            List<CluesReport> cluesReportList=cluesReportMapper.selectList(wrapper);
            for(CluesReport cluesReport:cluesReportList)
            {
                cluesReport.setIsreviewed("Y");
                cluesReport.setIspass("Y");
                cluesReportMapper.updateById(cluesReport);
            }
            return Boolean.TRUE;
             } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
    public String GetCluesNumber()
    {
        try
        {
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            String count=String.valueOf(clueMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
    public Result GetAllCLuesPublished(int user_id, int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Clue> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            wrapper.eq("USER_ID",user_id).eq("ISACTIVE","Y");
            IPage iPage = clueMapper.selectPage(page, wrapper);
            List<ClueDTO> dtoList=cutIntoClueDTOList((List<Clue>)iPage.getRecords());
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.data.put("clue_list", dtoList);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result AddOneClue(@RequestBody Map<String, Object> inputMap){
        try
        {
            Result result = new Result();
            int user_id=(int)inputMap.get("user_id");
            int searchinfo_id=(int)inputMap.get("searchinfo_id");
            String clue_content=(String)inputMap.get("clue_content");
            String date=(String)inputMap.get("date");
            String detailTime=(String)inputMap.get("detailTime");
            String province=(String)inputMap.get("province");
            String city=(String)inputMap.get("city");
            String area=(String)inputMap.get("area");
            String detailAddress=(String)inputMap.get("detailAddress");
            //String picture=(String)inputMap.get("picture");
            List<String> array = new ArrayList<>();

            int pic_num=(int)inputMap.get("picNum");
            if(pic_num>0){
                array = (List<String>) inputMap.get("clue_pic");

            }
            //图片结束
            Clue newClue = new Clue();
            newClue.setUserId(user_id);
            newClue.setSearchinfoId(searchinfo_id);
            newClue.setClueContent(clue_content);
            newClue.setIsactive("Y");
            newClue.setWhetherConfirmed("N");
            newClue.setClueDate(new Date());
            if(!Objects.equals(date, "") && date!=null)
                newClue.setClueDay(new SimpleDateFormat("yyyy-MM-dd").parse(date));
            if(!Objects.equals(detailTime, "") && detailTime!=null)
                newClue.setDetailTime(detailTime);
            if(!Objects.equals(province, "") && province!=null)
                newClue.setProvince(province);
            if(!Objects.equals(city, "") && city!=null)
                newClue.setCity(city);
            if(!Objects.equals(area, "") && area!=null)
                newClue.setArea(area);
            if(!Objects.equals(detailAddress, "") && detailAddress!=null)
                newClue.setDetailAddress(detailAddress);
            clueMapper.insert(newClue);

            //图片
            List<String> url_array = new ArrayList<>();
            if(array.size()>0){
                List<Clue> tmpList = clueMapper.selectList(new QueryWrapper<Clue>().orderByDesc("CLUE_ID"));
                Clue clue = tmpList.get(0);
                for (int i = 0; i < array.size(); i++) {
                    String img_base64= array.get(i);
                    // 对每个元素执行操作
                    String url= AddCluePic(img_base64,clue.getClueId(),i);
                    url_array.add(url);
                }
                String joinedString = String.join(",", url_array);
                clue.setPicture(joinedString);
                clueMapper.updateById(clue);
            }
            //图片结束
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public String AddCluePic(String img_base64,int clueId,int i){
        String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
        img_base64 = img_base64.split("base64,")[1];

        byte[] img_bytes = Base64.getDecoder().decode(img_base64);
        ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

        String path = "clue_pic/" + Integer.toString(clueId)+"_"+i + type;
        ossUploadService.uploadfile(stream, path);
        String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
        return imgurl;
    }
    public Result DeleteClueByUser(int userid, int clueid)
    {
        Result result = new Result();
        if(!deleteClue(clueid))
        {
            return Result.error();
        }
        result.status = true;
        result.errorCode = 200;
        return result;
    }
    public Result GetClueDetail(int clueId){
        try
        {
            Result result = new Result();
            Clue clue=clueMapper.selectById(clueId);
            result.data.put("clue_day",TimeTrans.myToDateString(clue.getClueDay()));
            result.data.put("detail_time",clue.getDetailTime());
            result.data.put("province",clue.getProvince());
            result.data.put("city",clue.getCity());
            //图片list
            String pictureURLs = clue.getPicture(); // 获取数据库中的图片URL字符串，例如："http://.../1.png, http://.../2.png"
            List<String> picList = Arrays.asList(pictureURLs.split("\\s*,\\s*")); // 将图片URL字符串以逗号分隔符拆分成字符串列表

            result.data.put("pic_list", picList); // 将字符串列表写入result.data的pic_list字段

            result.data.put("area",clue.getArea());
            result.data.put("detail_address",clue.getDetailAddress());
            result.data.put("clue_content",clue.getClueContent());
            result.data.put("whether_confirmed",clue.getWhetherConfirmed());
            if(clue.getVolActId()!=null){
                result.data.put("clue_volActId",clue.getVolActId());
            }
            else{
                result.data.put("clue_volActId",null);
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}




