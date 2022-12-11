package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.dto.InfoRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.yixun.yixun_backend.entity.InfoReport;
import com.yixun.yixun_backend.service.InfoReportService;
import com.yixun.yixun_backend.mapper.InfoReportMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_info_report】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class InfoReportServiceImpl extends ServiceImpl<InfoReportMapper, InfoReport>
    implements InfoReportService{

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
}




