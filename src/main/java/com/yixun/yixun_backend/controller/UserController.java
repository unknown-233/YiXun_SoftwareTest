package com.yixun.yixun_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/UserInfo")
@RestController
public class UserController {
    @Resource
    private WebUserMapper webUserMapper;

    @Resource
    private AddressMapper addressMapper;
    @Resource
    private OssUploadService ossUploadService;

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
        result.data.put("user_head", user.getUserHeadUrl());
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

    //1.3-2  修改个人信息-不含头像
    @PutMapping("/ChangeUserInfo")
    public Result ChangeUserInfo(@RequestBody Map<String, Object> inputData){
        try{
            Result result=new Result();
            String uerNameKey="user_name";
            String userName="";
            String userEmailKey="user_email";
            String userEmail="";
            String userProvinceKey="user_province";
            String userProvince="";
            String userCityKey="user_city";
            String userCity="";
            String userAreaKey="user_area";
            String userArea="";
            String userAddressKey="user_address";
            String userAddress="";
            String userIdKey="user_id";
            int userId=0;
            String userPhoneKey="user_phone";
            long userPhone=0;

            if(inputData.containsKey(uerNameKey)){
                userName=(String)inputData.get(uerNameKey);
            }
            if(inputData.containsKey(userEmailKey)){
                userEmail=(String)inputData.get(userEmailKey);
            }
            if(inputData.containsKey(userProvinceKey)){
                userProvince=(String)inputData.get(userProvinceKey);
            }
            if(inputData.containsKey(userCityKey)){
                userCity=(String)inputData.get(userCityKey);
            }
            if(inputData.containsKey(userAreaKey)){
                userArea=(String)inputData.get(userAreaKey);
            }
            if(inputData.containsKey(userAddressKey)){
                userAddress=(String)inputData.get(userAddressKey);
            }
            if(inputData.containsKey(userIdKey)){
                userId=(int)inputData.get(userIdKey);
            }
            if(inputData.containsKey(userPhoneKey)){
                userPhone=(long)inputData.get(userPhoneKey);
            }
            WebUser user=webUserMapper.selectById(userId);
            user.setUserName(userName);
            user.setPhoneNum(userPhone);
            user.setMailboxNum(userEmail);
            if(userProvince!=""){
                Address address=addressMapper.selectById(user.getAddressId());
                if (address==null){
                    Address address1=new Address();
                    address.setProvinceId(userProvince);
                    address.setCityId(userCity);
                    address.setAreaId(userArea);
                    address.setDetail(userAddress);
                    addressMapper.insert(address);
                }
                else{
                    address.setProvinceId(userProvince);
                    address.setCityId(userCity);
                    address.setAreaId(userArea);
                    address.setDetail(userAddress);
                    addressMapper.updateById(address);
                }
            }

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    //1.3-3 上传/修改头像图片
    @PutMapping("/upLoadUserHead")
    public Result upLoadUserHead(@RequestBody Map<String, Object> inputData){
        try{
            Result result=new Result();
            String userIdKey="user_id";
            int userId=0;
            String img_base64Key = "user_head";
            String img_base64 = "";
            if(inputData.containsKey(userIdKey)){
                userId=(int)inputData.get(userIdKey);
            }
            if (inputData.containsKey(img_base64Key)) {
                img_base64 = (String) inputData.get(img_base64Key);
            }

            WebUser user=webUserMapper.selectById(userId);
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
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}
