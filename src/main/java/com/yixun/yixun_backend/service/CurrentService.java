package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.Current;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

/**
* @author dell
* @description 针对表【yixun_current】的数据库操作Service
* @createDate 2023-04-25 18:13:05
*/
public interface CurrentService extends IService<Current> {
    public Result GetTotalFinance();
    public double GetTotalIncome();
}
