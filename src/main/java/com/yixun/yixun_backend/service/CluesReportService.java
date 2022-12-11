package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_clues_report】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface CluesReportService extends IService<CluesReport> {
    ClueRepoInfoDTO cutIntoClueRepoInfoDTO(CluesReport repo);
    List<ClueRepoInfoDTO> cutIntoCluesRepoList(List<CluesReport> list);
}
