package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.UserInfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;

//my code
import java.util.Random;

/**
* @author hunyingzhong
* @description 针对表【yixun_web_user】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class WebUserServiceImpl extends ServiceImpl<WebUserMapper, WebUser>
    implements WebUserService{

    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private InfoReportMapper infoReportMapper;
    @Resource
    private CluesReportMapper cluesReportMapper;
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;
    @Resource
    private OssUploadService ossUploadService;
    @Resource
    private ClueMapper clueMapper;



    public UserInfoDTO cutIntoUserInfoDTO(WebUser user){
        UserInfoDTO dto=new UserInfoDTO();
        dto.setIsactive(user.getIsactive());
        dto.setUser_state(user.getUserState());
        dto.setUser_id(user.getUserId());
        dto.setUser_name(user.getUserName());
        dto.setPhone_num(user.getPhoneNum());
        dto.setFundation_time(TimeTrans.myToString(user.getFundationTime()));
        //发布的寻人信息数
        QueryWrapper<Searchinfo> wrapperSearchinfo = new QueryWrapper<Searchinfo>();
        wrapperSearchinfo.eq("USER_ID",user.getUserId());
        dto.setSearch_info_num(searchinfoMapper.selectCount(wrapperSearchinfo));
        //举报的信息数
        QueryWrapper<InfoReport> wrapperInfoReport = new QueryWrapper<InfoReport>();
        wrapperInfoReport.eq("USER_ID",user.getUserId());
        dto.setInfo_report_num(infoReportMapper.selectCount(wrapperInfoReport));
        //发布的线索数
        QueryWrapper<Clue> wrapperClue = new QueryWrapper<Clue>();
        wrapperClue.eq("USER_ID",user.getUserId());
        dto.setClue_num(clueMapper.selectCount(wrapperClue));
        //举报的线索数
        QueryWrapper<CluesReport> wrapperClueReport = new QueryWrapper<CluesReport>();
        wrapperClueReport.eq("USER_ID",user.getUserId());
        dto.setClue_report_num(cluesReportMapper.selectCount(wrapperClueReport));

        return dto;
    }

    public List<UserInfoDTO> cutIntoUserInfoList(List<WebUser> userList){
        List<UserInfoDTO> dtoList=new ArrayList<>();
        for(WebUser user : userList){
            UserInfoDTO dto=cutIntoUserInfoDTO(user);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result IfCorrectToLogIn(@RequestBody Map<String,Object> inputData){
        Result message = new Result();
        String phoneKey="user_phone";
        String passwordKey="user_password";
        Long phone= Long.valueOf(0);
        String password="";
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
                Administrators administrator = administratorsMapper.selectOne(new QueryWrapper<Administrators>().eq("ADMINISTRATOR_PHONE", phone).eq("ADMINISTRATOR_CODE", password));
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
    public Result AddUserInfomation(@RequestBody Map<String,Object> inputData){
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
    public Result AddWebUser(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String userPhoneKey = "user_phone";
            Long userPhone =  Long.valueOf(0);
            String userPasswordKey = "user_password";
            String userPassword = "";
            String userNameKey = "user_name";
            String userName = "";
            String userEmailKey = "user_email";
            String userEmail = "";
            if (inputData.containsKey(userPhoneKey)) {
                userPhone = (Long) inputData.get(userPhoneKey);
            }
            if (inputData.containsKey(userPasswordKey)) {
                userPassword = (String) inputData.get(userPasswordKey);
            }
            if (inputData.containsKey(userNameKey)) {
                userName = (String) inputData.get(userNameKey);
            }
            if (inputData.containsKey(userEmailKey)) {
                userEmail = (String) inputData.get(userEmailKey);
            }
            WebUser user=webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("PHONE_NUM",userPhone));
            if(user != null)
            {
                return Result.error();
            }
            else{
                WebUser newUser = new WebUser();
                newUser.setUserName(userName);
                newUser.setPhoneNum(userPhone);
                newUser.setMailboxNum(userEmail);
                newUser.setUserPasswords(userPassword);
                webUserMapper.insert(newUser);
                List<WebUser> tmpList = webUserMapper.selectList(new QueryWrapper<WebUser>().orderByDesc("USER_ID"));
                WebUser newAddedUser = tmpList.get(0);
                result.data.put("user_id",newAddedUser.getUserId());
                result.errorCode = 200;
                result.status = true;
            }
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetUserInfomation(int user_id)
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
    public Result UpdateUserPassword(@RequestBody Map<String, Object> inputMap)
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
    public Result UpdateUserInfomation(@RequestBody Map<String, Object> inputData){
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
                    address1.setProvinceId(userProvince);
                    address1.setCityId(userCity);
                    address1.setAreaId(userArea);
                    address1.setDetail(userAddress);
                    addressMapper.insert(address1);
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
    public Result upDateUserProfilePic(@RequestBody Map<String, Object> inputData){
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

    //my code
    public String SendEmailVerification(String email){
        try{
            // 随机生成6位验证码
            Random random = new Random();
            String code = "";//要生成的验证码
            for(int i=0; i<6; i++) {
                code += random.nextInt(10);
            }

            ArrayList<String> emailArray = new ArrayList<>();
            //测试，收取邮件的邮箱，可以填写自己的发送邮件的邮箱
            emailArray.add(email);

            MailSenderUtil.sendMailToUserArray(emailArray,MailConst.NOTIFICATION_MAIL_TITLE,MailConst.NOTIFICATION_MAIL_CONTENT+code);

            return code;
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    //复写了之前的，别忘了删掉
    public Result N_UpdateUserPassword(@RequestBody Map<String, Object> inputMap)
    {
        try{
            Result result=new Result();
            int userid=(int)inputMap.get("user_id");
            String oldPassword = (String)inputMap.get("user_password");
            String newPassword = (String)inputMap.get("new_password");
            String verification = (String)inputMap.get("user_verification");
            String right_veri = (String)inputMap.get("verification");
            if(verification==right_veri)
            {
                WebUser user=webUserMapper.selectById(userid);
                if(user.getUserPasswords().equals(oldPassword)) {
                    user.setUserPasswords(newPassword);
                    webUserMapper.updateById(user);
                }else{
                    return Result.error();
                }
            }
            else{
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
    public Result N_AddWebUser(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String userPhoneKey = "user_phone";
            Long userPhone =  Long.valueOf(0);
            String userPasswordKey = "user_password";
            String userPassword = "";
            String userNameKey = "user_name";
            String userName = "";
            String userEmailKey = "user_email";
            String userEmail = "";
            if (inputData.containsKey(userPhoneKey)) {
                userPhone = (Long) inputData.get(userPhoneKey);
            }
            if (inputData.containsKey(userPasswordKey)) {
                userPassword = (String) inputData.get(userPasswordKey);
            }
            if (inputData.containsKey(userNameKey)) {
                userName = (String) inputData.get(userNameKey);
            }
            if (inputData.containsKey(userEmailKey)) {
                userEmail = (String) inputData.get(userEmailKey);
            }
            String verification = (String)inputData.get("user_verification");
            String right_veri = (String)inputData.get("verification");
            WebUser user=webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("PHONE_NUM",userPhone));
            if(!verification.equals (right_veri)){
                return Result.error();
            }
            if(user != null)
            {
                return Result.error();
            }
            else{
                WebUser newUser = new WebUser();
                newUser.setUserName(userName);
                newUser.setPhoneNum(userPhone);
                newUser.setMailboxNum(userEmail);
                newUser.setUserPasswords(userPassword);
                webUserMapper.insert(newUser);
                List<WebUser> tmpList = webUserMapper.selectList(new QueryWrapper<WebUser>().orderByDesc("USER_ID"));
                WebUser newAddedUser = tmpList.get(0);
                result.data.put("user_id",newAddedUser.getUserId());
                result.errorCode = 200;
                result.status = true;
            }
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    public Result N_IfCorrectToLogIn(@RequestBody Map<String,Object> inputMap){
        Result message = new Result();
        try{
            String email = (String)inputMap.get("user_email");
            String password = (String)inputMap.get("user_password");
            WebUser user = webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("MAILBOX_NUM", email).eq("USER_PASSWORDS", password));
            if(user!=null){
                if (user.getIsactive() == "N" || user.getUserState() == "N"){
                    message.data.put("message","账号已被注销或封禁");
                    return message;
                }
                Date date = new Date();
                user.setLastloginTime(date);
                webUserMapper.updateById(user);
                //webUserMapper.update(user,new UpdateWrapper<WebUser>().eq("PHONE_NUM", phone).eq("USER_PASSWORDS", password));
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
                Administrators administrator = administratorsMapper.selectOne(new QueryWrapper<Administrators>().eq("ADMINISTRATOR_EMAIL", email).eq("ADMINISTRATOR_CODE", password));
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

}




