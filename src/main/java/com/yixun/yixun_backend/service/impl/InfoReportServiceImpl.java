package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.dto.InfoRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.yixun.yixun_backend.entity.InfoReport;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import com.yixun.yixun_backend.mapper.CluesReportMapper;
import com.yixun.yixun_backend.service.InfoReportService;
import com.yixun.yixun_backend.mapper.InfoReportMapper;
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
* @description 针对表【yixun_info_report】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class InfoReportServiceImpl extends ServiceImpl<InfoReportMapper, InfoReport>
    implements InfoReportService{
    @Resource
    private InfoReportMapper infoReportMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;

    public InfoRepoInfoDTO cutIntoInfoRepoInfoDTO(InfoReport repo) {
        InfoRepoInfoDTO infoRepoInfoDTO=new InfoRepoInfoDTO();
        infoRepoInfoDTO.setInfo_repo_id(repo.getInfoReportId());
        infoRepoInfoDTO.setSearch_info_id(repo.getSearchinfoId());
        infoRepoInfoDTO.setUser_id(repo.getUserId());

        infoRepoInfoDTO.setRepo_content(repo.getReportContent());
        infoRepoInfoDTO.setRepo_time(TimeTrans.myToString(repo.getReportTime()));
        infoRepoInfoDTO.setIspass(repo.getIspass());
        return infoRepoInfoDTO;
    }
    public List<InfoRepoInfoDTO> cutIntoInfoRepoList(List<InfoReport> list){
        List<InfoRepoInfoDTO> dtoList=new ArrayList<>();
        for(InfoReport repo : list){
            InfoRepoInfoDTO dto=cutIntoInfoRepoInfoDTO(repo);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result AddOneInfoReport(@RequestBody Map<String, Object> inputMap){
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
}




