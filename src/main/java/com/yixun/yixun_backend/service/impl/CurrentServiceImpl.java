package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.Current;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.FundOutMapper;
import com.yixun.yixun_backend.service.CurrentService;
import com.yixun.yixun_backend.mapper.CurrentMapper;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author dell
* @description 针对表【yixun_current】的数据库操作Service实现
* @createDate 2023-04-25 18:13:05
*/
@Service
public class CurrentServiceImpl extends ServiceImpl<CurrentMapper, Current>
    implements CurrentService{
    @Resource
    private CurrentMapper currentMapper;
    public Result GetTotalFinance()
    {
        try {
            Result result = new Result();
            Current current=currentMapper.selectById(1);
            result.data.put("fund_in_total",current.getTotalIncome());
            result.data.put("fund_out_total",current.getTotalOutcome());
            result.data.put("current_fund",current.getCurrentMoney());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }

}




