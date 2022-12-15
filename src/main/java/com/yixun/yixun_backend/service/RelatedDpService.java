package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.RelatedDpDTO;
import com.yixun.yixun_backend.entity.RelatedDp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_related_dp】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface RelatedDpService extends IService<RelatedDp> {
    RelatedDpDTO cutIntoRelatedDpDTO(RelatedDp relatedDp);
    List<RelatedDpDTO> cutIntoRelatedDpDTOList(List<RelatedDp> relatedDpList);
    public Result GetRelatedDpsCard(String province, String city);
    public Result GetRelatedDpDetail(int DP_id);
}
