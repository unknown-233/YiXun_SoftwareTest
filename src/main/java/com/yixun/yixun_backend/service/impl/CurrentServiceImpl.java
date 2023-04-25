package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.Current;
import com.yixun.yixun_backend.service.CurrentService;
import com.yixun.yixun_backend.mapper.CurrentMapper;
import org.springframework.stereotype.Service;

/**
* @author dell
* @description 针对表【yixun_current】的数据库操作Service实现
* @createDate 2023-04-25 18:13:05
*/
@Service
public class CurrentServiceImpl extends ServiceImpl<CurrentMapper, Current>
    implements CurrentService{

}




