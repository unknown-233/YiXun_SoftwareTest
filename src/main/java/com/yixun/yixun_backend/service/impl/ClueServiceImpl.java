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
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ClueDTO cutIntoClueDTO(Clue clue){
        ClueDTO dto=new ClueDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setClueId(clue.getClueId());
        dto.setClueContent(clue.getClueContent());
        dto.setClueDate(TimeTrans.myToString(clue.getClueDate()));
        dto.setSearchinfoId(clue.getSearchinfoId());
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
}




