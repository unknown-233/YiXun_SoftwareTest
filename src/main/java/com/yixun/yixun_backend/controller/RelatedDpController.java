package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.dto.RelatedDpDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.RelatedDp;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.RelatedDpMapper;
import com.yixun.yixun_backend.service.RelatedDpService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/RelatedDp")
@RestController
public class RelatedDpController {
    @Resource
    private RelatedDpMapper relatedDpMapper;
    @Resource
    private RelatedDpService relatedDpMapperService;
    @Resource
    private AddressMapper addressMapper;
    //8.1 展示相关部门信息卡片
    @GetMapping("/GetRelatedDps")
    public Result GetRelatedDps(String province,String city)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<RelatedDp> wrapper = new QueryWrapper<RelatedDp>();
            wrapper.eq("CITY_ID",city).orderByAsc("AREA_ID");
            List<RelatedDp> dpList=relatedDpMapper.selectList(wrapper);
            List<RelatedDpDTO> dtoList=new ArrayList<>();
            if(dpList.isEmpty())
                return Result.error();
            else{
                String area=dpList.get(0).getAreaId();
                dtoList.add(relatedDpMapperService.cutIntoRelatedDpDTO(dpList.get(0)));
                for(int i=1;i<dpList.size();i++){
                    if(!dpList.get(i).getAreaId().equals(area))
                    {
                        result.data.put(area,new ArrayList<>(dtoList));
                        dtoList.clear();
                        area=dpList.get(i).getAreaId();
                        dtoList.add(relatedDpMapperService.cutIntoRelatedDpDTO(dpList.get(i)));
                    }
                    else{
                        dtoList.add(relatedDpMapperService.cutIntoRelatedDpDTO(dpList.get(i)));
                    }
                }
                result.data.put(area, dtoList);
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //8.2 展示相关部门详情
    @GetMapping("/GetDPDetail")
    public Result GetDPDetail(int DP_id)
    {
        try
        {
            Result result = new Result();
            RelatedDp DP=relatedDpMapper.selectById(DP_id);
            result.data.put("DP_id", DP.getDpId());//data是字典，这是向result里面加输出的信息
            result.data.put("DP_name", DP.getDpName());
            result.data.put("DP_web", DP.getWebsite());
            result.data.put("DP_contact", DP.getContactMethod());
            result.data.put("DP_photo", DP.getDpPicUrl());
            result.data.put("DP_city", DP.getCityId());
            result.data.put("DP_address", DP.getDetail());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}
