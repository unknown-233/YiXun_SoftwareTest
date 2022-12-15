package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import com.yixun.yixun_backend.service.CluesReportService;
import com.yixun.yixun_backend.mapper.CluesReportMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_clues_report】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class CluesReportServiceImpl extends ServiceImpl<CluesReportMapper, CluesReport>
    implements CluesReportService{
    @Resource
    private CluesReportMapper cluesReportMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;
        public ClueRepoInfoDTO cutIntoClueRepoInfoDTO(CluesReport repo) {
            ClueRepoInfoDTO clueRepoInfoDTO=new ClueRepoInfoDTO();
            clueRepoInfoDTO.setClue_repo_id(repo.getClueReportId());
            clueRepoInfoDTO.setClue_id(repo.getClueId());
            clueRepoInfoDTO.setUser_id(repo.getUserId());
            clueRepoInfoDTO.setRepo_content(repo.getReportContent());
            clueRepoInfoDTO.setRepo_time(TimeTrans.myToString(repo.getReportTime()));
            clueRepoInfoDTO.setIspass(repo.getIspass());
            return clueRepoInfoDTO;
        }
        public List<ClueRepoInfoDTO> cutIntoCluesRepoList(List<CluesReport> list){
            List<ClueRepoInfoDTO> dtoList=new ArrayList<>();
            for(CluesReport repo : list){
                ClueRepoInfoDTO dto=cutIntoClueRepoInfoDTO(repo);
                dtoList.add(dto);
            }
            return dtoList;
        }

    public Result AddOneClueReport(@RequestBody Map<String, Object> inputMap){
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
    }




