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
    public Result GetVolActRecruited(int userId, int pagenum, int pagesize);
    public Result GetIfAppliedActivity(int userId,int volAct_id);
    public Result UpdateRecruitedState(int userId, int VolActId);
}
