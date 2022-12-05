package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_web_user】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class WebUserServiceImpl extends ServiceImpl<WebUserMapper, WebUser>
    implements WebUserService{

    private WebUserMapper webUserMapper;
//    public boolean CheckPassword(@RequestBody Map<String,Object> inputData){
//        Result result=new Result();
//        try{
//            String phoneKey="user_id";
//            String passwordKey="user_password";
//            int id=0;
//            String password="";
//            if(inputData.containsKey(phoneKey)){
//                id=(int)inputData.get(phoneKey);
//            }
//            if(inputData.containsKey(passwordKey)){
//                password=(String)inputData.get(passwordKey);
//            }
//
//            WebUser user1 = webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("USER_ID", id).eq("USER_PASSWORDS", password));
//            result.data.put("")
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            result.data.put("message","用户不正确或输入密码有误！");
//            return result;
//        }
//        return result;
//    }

}




