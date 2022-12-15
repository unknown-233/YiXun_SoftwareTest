package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolApplyInfoDTO;
import com.yixun.yixun_backend.entity.VolApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_apply】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolApplyService extends IService<VolApply> {
    VolApplyInfoDTO cutIntoVolApplyInfoList(VolApply apply);
    List<VolApplyInfoDTO> cutIntoVolApplyInfoDTOList(List<VolApply> list);
    public Result AddVolApply(@RequestBody Map<String, Object> inputMap);
    public Result GetIsReviewApply(int user_id);
}
