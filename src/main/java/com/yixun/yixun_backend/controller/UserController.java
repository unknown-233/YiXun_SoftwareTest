package com.yixun.yixun_backend.controller;



import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

@RequestMapping("/api/UserInfo")
@RestController
public class UserController {
    @Resource
    private WebUserService webUserService;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello world";
    }

    //1.2 显示个人信息
    @GetMapping("/GetUserInfo")
    public Result GetUserInfo(int user_id)
    {
        Result result=new Result();
        result=webUserService.GetUserInfomation(user_id);
        return result;
    }

    //1.3-1 修改个人密码
    //这里进行了改动
    @PutMapping("/ChangePassword")
    public Result ChangePassword(@RequestBody Map<String, Object> inputMap)
    {
        Result result=new Result();
        result=webUserService.UpdateUserPassword(inputMap);
        return result;
    }

    //1.3-2  修改个人信息-不含头像
    @PutMapping("/ChangeUserInfo")
    public Result ChangeUserInfo(@RequestBody Map<String, Object> inputData){
        Result result=new Result();
        result=webUserService.UpdateUserInfomation(inputData);
        return result;
    }
    //1.3-3 上传/修改头像图片
    @PutMapping("/upLoadUserHead")
    public Result upLoadUserHead(@RequestBody Map<String, Object> inputData){
        Result result=new Result();
        result=webUserService.upDateUserProfilePic(inputData);
        return result;
    }
}
