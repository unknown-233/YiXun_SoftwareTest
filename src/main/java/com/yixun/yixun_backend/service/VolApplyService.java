package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolApplyInfoDTO;
import com.yixun.yixun_backend.entity.VolApply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_apply】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolApplyService extends IService<VolApply> {
    VolApplyInfoDTO cutIntoVolApplyInfoList(VolApply apply);
    List<VolApplyInfoDTO> cutIntoVolApplyInfoDTOList(List<VolApply> list);
}
