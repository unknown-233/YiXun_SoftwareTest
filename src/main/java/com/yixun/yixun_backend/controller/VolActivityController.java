package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/VolAct")
@RestController
public class VolActivityController {
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private VolActivityService volActivityService;
    @Resource
    private AddressMapper addressMapper;

    //4.1.1 获取志愿活动
    @GetMapping("/ShowVolActivityList")
    public Result ShowVolActivityList(int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<VolActivity> page = new Page<>(pageNum, pageSize);
            QueryWrapper<VolActivity> wrapper = new QueryWrapper<VolActivity>();
            String localTime=TimeTrans.myToString(new Date());
            wrapper.orderByAsc("EXP_TIME").gt("EXP_TIME",localTime);
            IPage iPage = volActivityMapper.selectPage(page,wrapper);
            List<VolActivityDTO> dtoList=volActivityService.cutIntoVolActivityDTOList((List<VolActivity>)iPage.getRecords());
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

    //4.1.2 搜索志愿活动
    @PostMapping("/SearchVolActivity")
    public Result SearchVolActivity(@RequestBody Map<String, Object> inputMap)
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
            List<VolActivityDTO> dtoList=volActivityService.cutIntoVolActivityDTOList((List<VolActivity>)iPage.getRecords());
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

    //4.1.3 获取志愿活动详细信息 =====> *
    //volActInfo: "/api/volActInfo",
    @GetMapping("/ShowSingleVolActivity")
    public Result ShowSingleVolActivity(int VolActId)
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
            result.data.put("activity_contactMethod", volActivity.getContactMethod());
            result.data.put("activity_content", volActivity.getActContent());

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
}
