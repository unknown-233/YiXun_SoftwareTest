package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.Income;
import com.yixun.yixun_backend.service.IncomeService;
import com.yixun.yixun_backend.mapper.IncomeMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【yixun_income】的数据库操作Service实现
* @createDate 2023-04-25 19:41:46
*/
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income>
    implements IncomeService{

}




