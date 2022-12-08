package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.UserInfoDTO;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.WebUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_web_user】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface WebUserService extends IService<WebUser> {
    UserInfoDTO cutIntoUserInfoDTO(WebUser user);
    List<UserInfoDTO> cutIntoUserInfoList(List<WebUser> userList);
}
