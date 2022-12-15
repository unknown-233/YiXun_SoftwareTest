package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.InfoRepoInfoDTO;
import com.yixun.yixun_backend.entity.InfoReport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_info_report】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface InfoReportService extends IService<InfoReport> {
    InfoRepoInfoDTO cutIntoInfoRepoInfoDTO(InfoReport repo);
    List<InfoRepoInfoDTO> cutIntoInfoRepoList(List<InfoReport> list);
    public Result AddOneInfoReport(@RequestBody Map<String, Object> inputMap);
}
