package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolActivityServiceImpl extends ServiceImpl<VolActivityMapper, VolActivity>
    implements VolActivityService{
    @Resource
    private AddressService addressService;

    public VolActivityDTO cutIntoVolActivityDTO(VolActivity volActivity){
        VolActivityDTO dto=new VolActivityDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setVol_act_id(volActivity.getVolActId());
        dto.setVol_act_name(volActivity.getVolActName());
        dto.setVol_act_content(volActivity.getActContent());
        dto.setVol_act_time(TimeTrans.myToString(volActivity.getExpTime()));
        dto.setArea_id(addressService.getAreaID(volActivity.getAddressId()));
        dto.setCity_id(addressService.getCityID(volActivity.getAddressId()));
        dto.setProvince_id(addressService.getProvinceID(volActivity.getAddressId()));
        dto.setDetail(addressService.getDetail(volActivity.getAddressId()));
        dto.setNeed_people(volActivity.getNeedpeople());
        dto.setActpicurl(volActivity.getActPicUrl());
        dto.setContact_method(volActivity.getContactMethod());
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
}




