package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.Recruited;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

/**
* @author hunyingzhong
* @description 针对表【yixun_recruited】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface RecruitedService extends IService<Recruited> {
    public Result GetVolActRecruited(int volid, int pagenum, int pagesize);
    public Result GetIfAppliedActivity(int vol_id,int volAct_id);
    public Result UpdateRecruitedState(int VolId, int VolActId);
}
