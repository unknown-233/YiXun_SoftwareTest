package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.SearchinfoFollowup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo_followup】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface SearchinfoFollowupService extends IService<SearchinfoFollowup> {
    public Result GetFollowUpInfo(int volid, int pagenum, int pagesize);
    public Result RefuseFollowUp(int volid, int search_info_id);

}
