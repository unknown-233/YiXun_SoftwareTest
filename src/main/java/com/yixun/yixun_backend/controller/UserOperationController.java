package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/UserOperation")
@RestController
public class UserOperationController {
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private CluesReportMapper cluesReportMapper;
    @Resource
    private InfoReportMapper infoReportMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;
    @Resource
    private SearchinfoFocusMapper searchinfoFocusMapper;
    @Resource
    private SearchinfoMapper searchinfoMapper;

    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private OssUploadService ossUploadService;

    @Resource
    private ClueService clueService;
    //2.1 展示用户发布的所有寻人信息
    @GetMapping("/GetAllSearchInfoPublished")
    public Result GetAllSearchInfoPublished(int user_id,int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("USER_ID",user_id).eq("ISACTIVE","Y");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("searchInfo_list", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //2.2 展示用户发布的所有寻人线索
    @GetMapping("/GetAllCLuesPublished")
    public Result GetAllCLuesPublished(int user_id, int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Clue> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            wrapper.eq("USER_ID",user_id).eq("ISACTIVE","Y");
            IPage iPage = clueMapper.selectPage(page, wrapper);
            List<ClueDTO> dtoList=clueService.cutIntoClueDTOList((List<Clue>)iPage.getRecords());
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

    //2.3 展示用户关注的所有寻人信息（LXK）
    @GetMapping("/GetFocusInfo")
    public Result GetFocusInfo(int userid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pagenum, pagesize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_focus where USER_ID ="+userid);
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("follow_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //5.1 发布寻人信息（LXK)
    @GetMapping("/AddSearchPeopleInfo")
    public Result AddSearchPeopleInfo(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result=new Result();
            int user_id = (int)inputMap.get("user_id");
            String search_type =(String)inputMap.get("search_type");
            String sought_people_name = (String)inputMap.get("sought_people_name");
            String sought_people_gender =(String)inputMap.get("sought_people_gender");
            String sought_people_height =(String)inputMap.get("sought_people_height");
            String sought_people_detail = (String)inputMap.get("sought_people_detail");
            String sought_people_birthday =(String)inputMap.get("sought_people_birthday");
            String sought_people_state = (String)inputMap.get("sought_people_state");
            String isreport = (String)inputMap.get("isreport");
            String searchinfo_lostdate = (String)inputMap.get("searchinfo_lostdate");
            String contact_method = (String)inputMap.get("contact_method");

            Searchinfo newSearchInfo = new Searchinfo();
            newSearchInfo.setUserId(user_id);
            newSearchInfo.setSearchType(search_type);
            newSearchInfo.setSoughtPeopleName(sought_people_name);
            newSearchInfo.setSoughtPeopleGender(sought_people_gender);
            newSearchInfo.setSoughtPeopleHeight(sought_people_height);
            newSearchInfo.setSoughtPeopleDetail(sought_people_detail);
            newSearchInfo.setSoughtPeopleBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sought_people_birthday));
            newSearchInfo.setSoughtPeopleState(sought_people_state);
            newSearchInfo.setIsreport(isreport);
            newSearchInfo.setSearchinfoLostdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(searchinfo_lostdate));
            newSearchInfo.setContactMethod(contact_method);

            //result.data.put("searchInfo_id", SearchinfoId);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    //5.1-2上传寻人信息图片
    @PutMapping("/AddSearchInfoPic")
    public Result AddSearchInfoPic(@RequestBody Map<String, Object> inputData){
        try{
            Result result=new Result();
            String idKey = "searchInfo_id";
            int searchInfo_id = 0;
            String img_base64Key = "searchInfo_pic";
            String img_base64 = "";
            if (inputData.containsKey(idKey)) {
                searchInfo_id = (int) inputData.get(idKey);
            }
            if (inputData.containsKey(img_base64Key)) {
                img_base64 = (String) inputData.get(img_base64Key);
            }
            Searchinfo searchinfo = searchinfoMapper.selectOne(new QueryWrapper<Searchinfo>().eq("NEWS_ID", searchInfo_id));
            String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
            img_base64 = img_base64.split("base64,")[1];

            byte[] img_bytes = Base64.getDecoder().decode(img_base64);
            ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

            String path = "searchInfo_pic/" + Integer.toString(searchInfo_id) + type;
            ossUploadService.uploadfile(stream, path);
            String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
            searchinfo.setSearchinfoPhotoUrl(imgurl);
            searchinfoMapper.updateById(searchinfo);

            result.data.put("searchInfo_pic", imgurl);

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //发布线索
    @PostMapping("/AddSearchPeopleClue")
    public Result AddSearchPeopleClue(@RequestBody Map<String, Object> inputMap){
        try
        {
            Result result = new Result();
            int user_id=(int)inputMap.get("user_id");
            int searchinfo_id=(int)inputMap.get("searchinfo_id");
            String clue_content=(String)inputMap.get("clue_content");

            Clue newClue = new Clue();
            newClue.setUserId(user_id);
            newClue.setSearchinfoId(searchinfo_id);
            newClue.setClueContent(clue_content);
            newClue.setIsactive("Y");
            newClue.setClueDate(new Date());
            clueMapper.insert(newClue);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //发布线索举报
    @PostMapping("/AddSearchClueReport")
    public Result AddSearchClueReport(@RequestBody Map<String, Object> inputMap){
        try
        {
            Result result = new Result();
            int user_id=(int)inputMap.get("user_id");
            int clue_id=(int)inputMap.get("clue_id");
            String report_content=(String)inputMap.get("report_content");

            CluesReport newCluesReport = new CluesReport();
            newCluesReport.setUserId(user_id);
            newCluesReport.setClueId(clue_id);
            newCluesReport.setReportContent(report_content);
            newCluesReport.setReportTime(new Date());
            newCluesReport.setIsreviewed("N");
            newCluesReport.setIspass("N");
            newCluesReport.setAdministratorId(administratorsMapper.selectRandomOne().getAdministratorId());
            cluesReportMapper.insert(newCluesReport);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //寻人信息举报
    @PostMapping("/AddSearchInfoReport")
    public Result AddSearchInfoReport(@RequestBody Map<String, Object> inputMap){
        try
        {
            Result result = new Result();
            int user_id=(int)inputMap.get("user_id");
            int searchinfo_id=(int)inputMap.get("searchinfo_id");
            String report_content=(String)inputMap.get("report_content");

            InfoReport infoReport = new InfoReport();
            infoReport.setUserId(user_id);
            infoReport.setSearchinfoId(searchinfo_id);
            infoReport.setReportContent(report_content);
            infoReport.setIsreviewed("N");
            infoReport.setIspass("N");
            infoReport.setReportTime(new Date());
            infoReport.setAdministratorId(administratorsMapper.selectRandomOne().getAdministratorId());
            infoReportMapper.insert(infoReport);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //关注寻人信息
    @GetMapping("/UserFocus")
    public Result UserFocus(int userid, int infoid)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<SearchinfoFocus> wrapper = new QueryWrapper<SearchinfoFocus>();
            wrapper.eq("USER_ID",userid).eq("SEARCHINFO_ID",infoid);
            int ifApply=searchinfoFocusMapper.selectCount(wrapper);
            if(ifApply==1) //已关注
            {
                searchinfoFocusMapper.delete(wrapper);//从searchinfoFocus表中删去这条数据
                result.data.put("state", false);
            }
            else
            {
                SearchinfoFocus focus = new SearchinfoFocus();
                focus.setSearchinfoId(infoid);
                focus.setUserId(userid);
                focus.setFocustime(new Date());
                searchinfoFocusMapper.insert(focus);
                result.data.put("state", true);
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //用户删除自己发布的寻人信息
    @DeleteMapping("/UserDeleteInfo")
    public Result UserDeleteInfo(int userid, int infoid){
        try
        {
            Result result = new Result();
            Searchinfo searchinfo=searchinfoMapper.selectById(infoid);
            searchinfo.setIsactive("N");
            searchinfoMapper.updateById(searchinfo);
            //删除寻人信息相关举报
            QueryWrapper<InfoReport> wrapper = new QueryWrapper<InfoReport>();
            wrapper.eq("SEARCHINFO_ID",infoid);
            List<InfoReport> infoRepoList=infoReportMapper.selectList(wrapper);
            for(InfoReport inforepo:infoRepoList)
            {
                inforepo.setIsreviewed("Y");
                inforepo.setIspass("Y");
                infoReportMapper.updateById(inforepo);
            }
            //删除相关线索和举报
            QueryWrapper<Clue> clueWrapper = new QueryWrapper<Clue>();
            clueWrapper.eq("SEARCHINFO_ID",infoid);
            List<Clue> clueList=clueMapper.selectList(clueWrapper);
            for(Clue clue:clueList)
            {
                if(!clueService.deleteClue(clue.getClueId()))
                {
                    return Result.error();
                }
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //用户删除自己发布的寻人线索 *
    @DeleteMapping("/UserDeleteClue")
    public Result UserDeleteClue(int userid, int clueid)
    {
        Result result = new Result();
        if(!clueService.deleteClue(clueid))
        {
            return Result.error();
        }
        result.status = true;
        result.errorCode = 200;
        return result;
    }

}

