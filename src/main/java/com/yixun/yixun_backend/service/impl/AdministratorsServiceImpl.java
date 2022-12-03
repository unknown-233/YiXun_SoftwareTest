package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.domain.Administrators;
import com.yixun.yixun_backend.service.AdministratorsService;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import org.springframework.stereotype.Service;

/**
* @author hunyingzhong
* @description 针对表【yixun_administrators】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class AdministratorsServiceImpl extends ServiceImpl<AdministratorsMapper, Administrators>
    implements AdministratorsService{

}




