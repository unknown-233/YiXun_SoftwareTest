package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_clues_report】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface CluesReportService extends IService<CluesReport> {
    ClueRepoInfoDTO cutIntoClueRepoInfoDTO(CluesReport repo);
    List<ClueRepoInfoDTO> cutIntoCluesRepoList(List<CluesReport> list);
    public Result AddOneClueReport(@RequestBody Map<String, Object> inputMap);
}
