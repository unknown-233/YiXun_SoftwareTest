package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import org.springframework.stereotype.Service;

/**
* @author hunyingzhong
* @description 针对表【yixun_web_user】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class WebUserServiceImpl extends ServiceImpl<WebUserMapper, WebUser>
    implements WebUserService{

}




