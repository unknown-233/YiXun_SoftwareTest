package com.yixun.yixun_backend.service.impl;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.UserInfoDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ClueMapper clueMapper;
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
    public UserInfoDTO cutIntoUserInfoDTO(WebUser user){
        UserInfoDTO dto=new UserInfoDTO();
        dto.setIsactive(user.getIsactive());
        dto.setUser_state(user.getUserState());
        dto.setUser_id(user.getUserId());
        dto.setUser_name(user.getUserName());
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

}




