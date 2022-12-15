package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.SearchinfoFocus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo_focus】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface SearchinfoFocusService extends IService<SearchinfoFocus> {
    public Result GetFocusInfo(int userid, int pagenum, int pagesize);
    public Result GetUserFocus(int userid, int infoid);
}
