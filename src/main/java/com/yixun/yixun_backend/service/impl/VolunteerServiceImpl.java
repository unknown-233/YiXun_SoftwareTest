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
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;

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
    private AddressMapper addressMapper;
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private ClueMapper clueMapper;

    public VolInfoDTO cutIntoVolInfoDTO(Volunteer vol){
        //用户
        WebUser user=webUserMapper.selectById(vol.getVolUserId());
        //地址
        Address address=addressMapper.selectById(user.getAddressId());

        VolInfoDTO dto=new VolInfoDTO();
        dto.setUser_state(user.getUserState());
        dto.setUser_id(user.getUserId());
        dto.setUser_name(user.getUserName());
        //dto.setFundation_time(TimeTrans.myToString(user.getFundationTime()));
        dto.setPhone_num(user.getPhoneNum());
        dto.setMail_num(user.getMailboxNum());
        dto.setVol_id(vol.getVolId());

        dto.setProvince_id(address.getProvinceId());
        dto.setCity_id(address.getCityId());
        dto.setArea_id(address.getAreaId());
        dto.setDetail(address.getDetail());

        //跟进的寻人信息数
        QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
        wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_followup where VOL_ID ="+vol.getVolId()).eq("WHETHER_FOUND","N");
        dto.setInfo_followup_num(searchinfoMapper.selectCount(wrapper));
        //参加的活动数
        QueryWrapper<VolActivity> wrapperAct = new QueryWrapper<VolActivity>();
        wrapperAct.inSql("VOL_ACT_ID","select VOL_ACT_ID from yixun_recruited where VOL_ID="+vol.getVolId());
        wrapperAct.gt("END_TIME",new Date());
        dto.setAct_num(volActivityMapper.selectCount(wrapperAct));

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
    public Result UpdateSearchinfoToFound(int searchinfoId){
        try
        {
            Result result=new Result();
            Searchinfo searchinfo=searchinfoMapper.selectById(searchinfoId);
            searchinfo.setWhetherFound("Y");
            searchinfoMapper.updateById(searchinfo);

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateClueConfirmed(@RequestBody Map<String, Object> inputMap){
        try
        {
            Result result=new Result();
            int clueId=(int) inputMap.get("clueId");
            String confirmText=(String) inputMap.get("textarea");
            String checkMan=(String) inputMap.get("checkMan");
            String phoneNumber=(String) inputMap.get("phoneNumber");
            Clue clue=clueMapper.selectById(clueId);
            clue.setWhetherConfirmed("Y");
            clue.setConfirmText(confirmText);
            clue.setConfirmMen(checkMan);
            clue.setPhonenum(phoneNumber);
            clueMapper.updateById(clue);

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    public Result GetVolByDistinct(String city)
    {
        try
        {
            Result result=new Result();
            QueryWrapper<Address> wrapper = new QueryWrapper<Address>();
            wrapper.eq("AREA_ID",city);
            //没写完，还在写
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e)
        {
            return Result.error();
        }
    }
}




