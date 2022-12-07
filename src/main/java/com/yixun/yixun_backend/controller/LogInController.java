package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.utils.OssUploadService;
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
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/LogIn")
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
    @Resource
    private OssUploadService ossUploadService;

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

    @PutMapping("/UpLoadInfo")
    public Result UpLoadInfo(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String userIdKey = "user_id";
            int userId = 0;
            String userGenderKey = "user_gender";
            String userGender = "";
            String userProvinceKey = "user_province";
            String userProvince = "";
            String userCityKey = "user_city";
            String userCity = "";
            String userAreaKey = "user_area";
            String userArea = "";
            String userAddressKey = "user_address";
            String userAddress = "";
            String userHeadKey = "user_head";
            String img_base64 = "";
            if (inputData.containsKey(userIdKey)) {
                userId = (int) inputData.get(userIdKey);
            }
            if (inputData.containsKey(userGenderKey)) {
                userGender = (String) inputData.get(userGenderKey);
            }
            if (inputData.containsKey(userProvinceKey)) {
                userProvince = (String) inputData.get(userProvinceKey);
            }
            if (inputData.containsKey(userCityKey)) {
                userCity = (String) inputData.get(userCityKey);
            }
            if (inputData.containsKey(userAreaKey)) {
                userArea = (String) inputData.get(userAreaKey);
            }
            if (inputData.containsKey(userAddressKey)) {
                userAddress = (String) inputData.get(userAddressKey);
            }
            if (inputData.containsKey(userHeadKey)) {
                img_base64 = (String) inputData.get(userHeadKey);
            }
            WebUser user=webUserMapper.selectById(userId);
            if(userProvince!=""){
                Address address=new Address();
                address.setDetail(userAddress);
                address.setAreaId(userArea);
                address.setProvinceId(userProvince);
                address.setCityId(userCity);
                addressMapper.insert(address);
                user.setAddressId(address.getAddressId());
            }
            user.setUserGender(userGender);
            if(img_base64!=""){
                String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
                img_base64 = img_base64.split("base64,")[1];

                byte[] img_bytes = Base64.getDecoder().decode(img_base64);
                ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

                String path = "user_head/" + Integer.toString(userId) + type;
                ossUploadService.uploadfile(stream, path);
                String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
                user.setUserHeadUrl(imgurl);
                webUserMapper.updateById(user);
                result.data.put("img_url", imgurl);
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
