package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.UserInfoDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.WebUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_web_user】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface WebUserService extends IService<WebUser> {
    UserInfoDTO cutIntoUserInfoDTO(WebUser user);
    List<UserInfoDTO> cutIntoUserInfoList(List<WebUser> userList);
    public Result IfCorrectToLogIn(@RequestBody Map<String,Object> inputData);
    public Result AddUserInfomation(@RequestBody Map<String,Object> inputData);
    public Result AddWebUser(@RequestBody Map<String,Object> inputData);
    public Result GetUserInfomation(int user_id);
    public Result UpdateUserPassword(@RequestBody Map<String, Object> inputMap);
    public Result UpdateUserInfomation(@RequestBody Map<String, Object> inputData);
    public Result upDateUserProfilePic(@RequestBody Map<String, Object> inputData);
}
