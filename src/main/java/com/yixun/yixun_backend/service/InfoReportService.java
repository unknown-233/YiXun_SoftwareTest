package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.InfoRepoInfoDTO;
import com.yixun.yixun_backend.entity.InfoReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_info_report】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface InfoReportService extends IService<InfoReport> {
    InfoRepoInfoDTO cutIntoInfoRepoInfoDTO(InfoReport repo);
    List<InfoRepoInfoDTO> cutIntoInfoRepoList(List<InfoReport> list);
}
