package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.SerachinfoDTO;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class SearchinfoServiceImpl extends ServiceImpl<SearchinfoMapper, Searchinfo>
    implements SearchinfoService{
    @Resource
    private AddressService addressService;

    public SerachinfoDTO cutIntoSerachinfoDTO(Searchinfo searchinfo){
        SerachinfoDTO dto=new SerachinfoDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setSoughtPeopleName(searchinfo.getSoughtPeopleName());
        dto.setArea(addressService.getAreaID(searchinfo.getAddressId()));
        dto.setCity(addressService.getCityID(searchinfo.getAddressId()));
        dto.setProvince(addressService.getProvinceID(searchinfo.getAddressId()));
        dto.setDetail(addressService.getDetail(searchinfo.getAddressId()));
        dto.setSearchinfoLostdate(TimeTrans.myToString(searchinfo.getSearchinfoLostdate()));
        dto.setSearchinfoPhotoURL(searchinfo.getSearchinfoPhotoUrl());
        dto.setSoughtPeopleBirthday(TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
        dto.setSoughtPeopleGender(searchinfo.getSoughtPeopleGender());
        dto.setSearchType(searchinfo.getSearchType());
        return dto;
    }

    public List<SerachinfoDTO> cutIntoSerachinfoDTOList(List<Searchinfo> searchinfoList){
        List<SerachinfoDTO> dtoList=new ArrayList<>();
        for(Searchinfo searchinfo : searchinfoList){
            SerachinfoDTO dto=cutIntoSerachinfoDTO(searchinfo);
            dtoList.add(dto);
        }
        return dtoList;
    }
}




