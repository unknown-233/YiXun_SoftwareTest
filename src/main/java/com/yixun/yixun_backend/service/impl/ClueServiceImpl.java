package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
}




