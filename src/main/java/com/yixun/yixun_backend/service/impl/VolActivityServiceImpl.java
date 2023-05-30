package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.Recruited;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.RecruitedMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolActivityServiceImpl extends ServiceImpl<VolActivityMapper, VolActivity>
    implements VolActivityService{
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private RecruitedMapper recruitedMapper;
    @Resource
    private WebUserMapper webUserMapper;
    public List<WebUser> getPageOfData(int pageNum, int pageSize, List<WebUser> dtoList) {
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, dtoList.size());

        if (startIndex >= endIndex) {
            // 页数超出范围或数据为空，返回空列表或抛出异常
            return new ArrayList<>();
        }

        return dtoList.subList(startIndex, endIndex);
    }
    public VolActivityDTO cutIntoVolActivityDTO(VolActivity volActivity){
        VolActivityDTO dto=new VolActivityDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setVolActId(volActivity.getVolActId());
        dto.setVolActName(volActivity.getVolActName());
        dto.setExpTime(TimeTrans.myToString(volActivity.getExpTime()));
        if(volActivity.getEndTime()!=null){
            dto.setEndTime(TimeTrans.myToString(volActivity.getEndTime()));
        }
        else{
            dto.setEndTime("");
        }
        dto.setEndTime(TimeTrans.myToString(volActivity.getEndTime()));
        Address address=addressMapper.selectById(volActivity.getAddressId());
        dto.setArea(address.getAreaId());
        dto.setCity(address.getCityId());
        dto.setProvince(address.getProvinceId());
        dto.setDetail(address.getDetail());
        dto.setNeedpeople(volActivity.getNeedpeople());
        dto.setActPicUrl(volActivity.getActPicUrl());
        dto.setContactMethod(volActivity.getContactMethod());
        dto.setSignupPeople(volActivity.getSignupPeople());
        dto.setReleaseTime(TimeTrans.myToString(volActivity.getReleaseTime()));
        return dto;
    }

    public List<VolActivityDTO> cutIntoVolActivityDTOList(List<VolActivity> volActivityList){
        List<VolActivityDTO> dtoList=new ArrayList<>();
        for(VolActivity volActivity : volActivityList){
            VolActivityDTO dto=cutIntoVolActivityDTO(volActivity);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result GetVolActivities(int volId, int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<VolActivity> page = new Page<>(pageNum, pageSize);
            QueryWrapper<VolActivity> wrapper = new QueryWrapper<VolActivity>();
            wrapper.eq("INITIATOR",volId);
            wrapper.orderByAsc("VOL_ACT_ID");
            IPage iPage = volActivityMapper.selectPage(page,wrapper);
            List<VolActivityDTO> dtoList=cutIntoVolActivityDTOList((List<VolActivity>)iPage.getRecords());
            result.data.put("activity_list", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetVolActivityByWord(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result = new Result();
            int pageNum=(int)inputMap.get("pageNum");
            int pageSize=(int)inputMap.get("pageSize");
            String searchContent=(String)inputMap.get("search");
            Page<VolActivity> page = new Page<>(pageNum, pageSize);
            QueryWrapper<VolActivity> wrapper = new QueryWrapper<VolActivity>();
            wrapper.like("VOL_ACT_NAME",searchContent).orderByDesc("EXP_TIME");
            IPage iPage = volActivityMapper.selectPage(page,wrapper);
            List<VolActivityDTO> dtoList=cutIntoVolActivityDTOList((List<VolActivity>)iPage.getRecords());
            result.data.put("activity_list", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetVolActivityDetail(int VolActId)
    {
        try
        {
            Result result = new Result();
            VolActivity volActivity =volActivityMapper.selectById(VolActId);
            Address address;
            if(volActivity.getAddressId()!= null && addressMapper.selectById(volActivity.getAddressId())!=null) {
                address = addressMapper.selectById(volActivity.getAddressId());
                result.data.put("activity_province", address.getProvinceId());
                result.data.put("activity_city", address.getCityId());
                result.data.put("activity_area", address.getAreaId());
                result.data.put("activity_address", address.getDetail());
            }
            else {
                address = addressMapper.selectById(volActivity.getAddressId());
                result.data.put("activity_province", null);
                result.data.put("activity_city", null);
                result.data.put("activity_area", null);
                result.data.put("activity_address", null);
            }
            result.data.put("activity_id", volActivity.getVolActId());
            result.data.put("activity_name", volActivity.getVolActName());
            result.data.put("activity_pic", volActivity.getActPicUrl());
            result.data.put("activity_needpeople", volActivity.getNeedpeople());
            result.data.put("activity_signupPeople", volActivity.getSignupPeople());
            result.data.put("activity_expTime", TimeTrans.myToString(volActivity.getExpTime()));
            result.data.put("activity_endTime", TimeTrans.myToString(volActivity.getEndTime()));
            result.data.put("activity_contactMethod", volActivity.getContactMethod());
            result.data.put("activity_content", volActivity.getActContent());
            if(volActivity.getClueId()!=null){
                result.data.put("activity_clueId", volActivity.getClueId());
            }
            else{
                result.data.put("activity_clueId",null);
            }

            var is_overdue = volActivity.getExpTime().after(new Date());
            result.data.put("is_overdue", is_overdue);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetAllRecruited(int actId,int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<Recruited> wrapper=new QueryWrapper<>();
            wrapper.eq("VOL_ACT_ID",actId);
            List<Recruited> recruitedList=recruitedMapper.selectList(wrapper);
            List<WebUser> userList=new ArrayList<>();
            for(Recruited r:recruitedList){
                WebUser user=webUserMapper.selectById(r.getVolId());
                userList.add(user);
            }
            List<WebUser> resultList=getPageOfData(pageNum,pageSize,userList);
            result.data.put("users_list",resultList);
            result.data.put("total",userList.size());
            result.data.put("getcount", resultList.size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
            catch (Exception e) {
            return Result.error();
        }
    }
}




