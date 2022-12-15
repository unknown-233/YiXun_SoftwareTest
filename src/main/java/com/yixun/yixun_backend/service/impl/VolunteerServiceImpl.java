package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.UserInfoDTO;
import com.yixun.yixun_backend.dto.VolInfoDTO;
import com.yixun.yixun_backend.dto.VolunteerDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.VolunteerService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_volunteer】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer>
    implements VolunteerService{
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private SearchinfoFollowupMapper searchinfoFollowupMapper;
    @Resource
    private RecruitedMapper recruitedMapper;
    @Resource
    private VolunteerMapper volunteerMapper;

    public VolInfoDTO cutIntoVolInfoDTO(Volunteer vol){
        //用户
        WebUser user=webUserMapper.selectById(vol.getVolUserId());

        VolInfoDTO dto=new VolInfoDTO();
        dto.setUser_state(user.getUserState());
        dto.setUser_id(user.getUserId());
        dto.setUser_name(user.getUserName());
        dto.setFundation_time(TimeTrans.myToString(user.getFundationTime()));
        dto.setPhone_num(user.getPhoneNum());
        dto.setMail_num(user.getMailboxNum());
        dto.setVol_id(vol.getVolId());
        dto.setVol_time(vol.getVolTime());

        //跟进的寻人信息数
        QueryWrapper<SearchinfoFollowup> wrapperFollow = new QueryWrapper<SearchinfoFollowup>();
        wrapperFollow.eq("VOL_ID",vol.getVolId());
        dto.setInfo_followup_num(searchinfoFollowupMapper.selectCount(wrapperFollow));
        //参加的活动数
        QueryWrapper<Recruited> wrapperAct= new QueryWrapper<Recruited>();
        wrapperAct.eq("VOL_ID",vol.getVolId());
        dto.setAct_num(recruitedMapper.selectCount(wrapperAct));

        return dto;
    }

    public List<VolInfoDTO> cutIntoVolInfoList(List<Volunteer> volList){
        List<VolInfoDTO> dtoList=new ArrayList<>();
        for(Volunteer vol : volList){
            VolInfoDTO dto=this.cutIntoVolInfoDTO(vol);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public String GetVolunteerNumber()
    {
        try
        {
            QueryWrapper<Volunteer> wrapper = new QueryWrapper<Volunteer>();
            String count=String.valueOf(volunteerMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
    public Result GetTenVolunteer()
    {
        try
        {
            Result result=new Result();
            List<VolunteerDTO> dtoList=volunteerMapper.selectTopTenVolDTO();
            result.data.put("AllVolActivity_list", dtoList);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}




