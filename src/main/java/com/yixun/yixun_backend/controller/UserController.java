package com.yixun.yixun_backend.controller;


import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/UserInfo")
//貌似可以解决跨域问题
@RestController
public class UserController {
    @Autowired
    private WebUserMapper webUserMapper;

    @Autowired
    private AddressMapper addressMapper;

    @GetMapping("/hello")
    public String hello()
    {
        return "hello world";
    }

    @GetMapping("/GetUserInfo")
    //查询所有用户
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
}
