package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.dto.ClueRepoInfoDTO;
import com.yixun.yixun_backend.entity.CluesReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_clues_report】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.CluesReport
*/
public interface CluesReportMapper extends BaseMapper<CluesReport> {

    List<ClueRepoInfoDTO> cutIntoCluesRepoList(List<CluesReport> records);
}




