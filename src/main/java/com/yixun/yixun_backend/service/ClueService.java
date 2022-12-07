package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.entity.Clue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_clue】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface ClueService extends IService<Clue> {
    ClueDTO cutIntoClueDTO(Clue clue);
    List<ClueDTO> cutIntoClueDTOList(List<Clue> clueList);
}
