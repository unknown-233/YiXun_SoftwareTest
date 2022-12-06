package com.yixun.yixun_backend.controller;


import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/api/UserInfo")
@RestController
public class UserController {
    @Resource
    private WebUserMapper webUserMapper;

    @Resource
    private AddressMapper addressMapper;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello world";
    }

    //1.2 显示个人信息
    @GetMapping("/GetUserInfo")
    public Result GetUserInfo(int user_id)
    {
        Result result = new Result();
        WebUser user=webUserMapper.selectById(user_id);
        result.data.put("user_id", user.getUserId());
        result.data.put("user_name", user.getUserName());
        result.data.put("user_password", user.getUserPasswords());
        result.data.put("user_head", user.getUserName());
        result.data.put("user_fundationtime", user.getFundationTime());
        result.data.put("user_phonenum", user.getPhoneNum());
        result.data.put("user_mailbox", user.getMailboxNum());
        result.data.put("user_gender", user.getUserGender());

        if (user.getAddressId() != null)
        {
            Address address = addressMapper.selectById(user.getAddressId());
            result.data.put("user_province", address.getProvinceId());
            result.data.put("user_city", address.getCityId());
            result.data.put("user_area", address.getAreaId());
            result.data.put("user_address", address.getDetail());
        }
        else
        {
            result.data.put("user_province", null);
            result.data.put("user_city", null);
            result.data.put("user_area", null);
            result.data.put("user_address", null);
        }
        result.status = true;
        result.errorCode = 200;
        return result;
    }

    //1.3-1 修改个人密码
    @PutMapping("/ChangePassword")
    public Result ChangePassword(@RequestBody Map<String, Object> inputMap)
    {
        try
       {
            String idKey = "user_id";
            String oldPasswordKey = "user_password";
            String newPasswordKey = "new_password";
            Result result=new Result();
            int userId=0;
            String oldPassword="";
            String newPassword="";
            if (inputMap.containsKey(idKey) && inputMap.containsKey(oldPasswordKey) && inputMap.containsKey(newPasswordKey)){
                userId = (int) inputMap.get(idKey);
                oldPassword = (String)inputMap.get(oldPasswordKey);
                newPassword = (String)inputMap.get(newPasswordKey);
            } else{
                return Result.error();
            }
            WebUser user=webUserMapper.selectById(userId);
            if(user.getUserPasswords().equals(oldPassword)) {
                user.setUserPasswords(newPassword);
                webUserMapper.updateById(user);
            }else{
                return Result.error();
            }
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}
