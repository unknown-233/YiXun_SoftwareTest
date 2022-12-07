package com.yixun.yixun_backend.controller;

import io.jsonwebtoken.Claims;
import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yixun.yixun_backend.entity.Administrators;
import com.yixun.yixun_backend.entity.Volunteer;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import com.yixun.yixun_backend.mapper.VolunteerMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.utils.JWTutils;
import com.yixun.yixun_backend.utils.Result;
import io.jsonwebtoken.Claims;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRegistration.Dynamic;
import java.math.BigInteger;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/LogIn")
public class LogInController {
    //用到哪些类就写哪些（实例化）
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;
    @Resource
    private WebUserService webUserService;

    @PostMapping("/LogInCheck")
    public Result LogInCheck(@RequestBody Map<String,Object> inputData){
        Result message = new Result();
        String phoneKey="user_phone";
        String passwordKey="user_password";
        Long phone= Long.valueOf(0);
        String password="";
        String strphone="";
        if(inputData.containsKey(phoneKey)){
            phone=(Long) inputData.get(phoneKey);
        }
        if(inputData.containsKey(passwordKey)){
            password=(String)inputData.get(passwordKey);
        }
        try{
            WebUser user = webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("PHONE_NUM", phone).eq("USER_PASSWORDS", password));
            if(user!=null){
                if (user.getIsactive() == "N" || user.getUserState() == "N"){
                    message.data.put("message","账号已被注销或封禁");
                    return message;
                }
                Date date = new Date();
                user.setLastloginTime(date);
                webUserMapper.update(user,new UpdateWrapper<WebUser>().eq("PHONE_NUM", phone).eq("USER_PASSWORDS", password));
                //保存数据库
                Volunteer volunteer = volunteerMapper.selectOne(new QueryWrapper<Volunteer>().eq("VOL_USER_ID", user.getUserId()));
                if(volunteer!=null){
                    String token= JWTutils.generateToken(Integer.toString(user.getUserId()),user.getUserPasswords());
                    message.data.put("user_token",token);
                    String vol_token= JWTutils.generateToken(Integer.toString(volunteer.getVolId()),user.getUserPasswords());
                    message.data.put("identity", "volunteer");
                    message.data.put("vol_id", volunteer.getVolId());
                    message.data.put("user_id", volunteer.getVolUserId());
                }
                else{
                    String token= JWTutils.generateToken(Integer.toString(user.getUserId()),user.getUserPasswords());
                    message.data.put("user_token",token);
                    message.data.put("identity", "user");
                    message.data.put("id", user.getUserId());
                }
            }
            else{
                Administrators administrator = administratorsMapper.selectOne(new QueryWrapper<Administrators>().eq("ADMINISTRATOR_PHONE", strphone).eq("ADMINISTRATOR_CODE", password));
                message.data.put("identity", "administrator");
                message.data.put("id", administrator.getAdministratorId());
                String token= JWTutils.generateToken(Integer.toString(administrator.getAdministratorId()),administrator.getAdministratorCode());
                message.data.put("user_token",token);
            }
        }
        catch (Exception e){
            message.data.put("message","用户不正确或密码错误！");
            return message;
        }
        message.status = true;
        message.errorCode = 200;
        return message;
    }
//    @GetMapping("/ParseToken")
//    public int ParseToken(String token){
//
//        int id=JWTutils.getCurrentUser(token);
//        return id;
//    }
//    @GetMapping("/ifTokenLegal")
//    public boolean ifTokenLegal(String token){
//
//        return JWTutils.ifLegal(token);
//    }
}
