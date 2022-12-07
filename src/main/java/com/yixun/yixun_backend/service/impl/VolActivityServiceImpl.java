package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolActivityServiceImpl extends ServiceImpl<VolActivityMapper, VolActivity>
    implements VolActivityService{
//    @Resource
//    private AddressService addressService;
@Resource
private AddressMapper addressMapper;


    public VolActivityDTO cutIntoVolActivityDTO(VolActivity volActivity){
        VolActivityDTO dto=new VolActivityDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setVolActId(volActivity.getVolActId());
        dto.setVolActName(volActivity.getVolActName());
        dto.setExpTime(TimeTrans.myToString(volActivity.getExpTime()));
        Address address=addressMapper.selectById(volActivity.getAddressId());
        dto.setArea(address.getAreaId());
        dto.setCity(address.getCityId());
        dto.setProvince(address.getProvinceId());
        dto.setDetail(address.getDetail());
        dto.setNeedpeople(volActivity.getNeedpeople());
        dto.setActPicUrl(volActivity.getActPicUrl());
        dto.setContactMethod(volActivity.getContactMethod());
        dto.setSignupPeople(volActivity.getSignupPeople());
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




