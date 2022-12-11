package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.dto.InfoRepoInfoDTO;
import com.yixun.yixun_backend.dto.VolApplyInfoDTO;
import com.yixun.yixun_backend.entity.VolApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_apply】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.VolApply
*/
public interface VolApplyMapper extends BaseMapper<VolApply> {
    List<VolApplyInfoDTO> cutIntoVolApplyInfoDTOList(List<VolApply> list);
}




