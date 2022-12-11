package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.mapper.AddressMapper;
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
    private AddressMapper addressMapper;

    public SearchinfoDTO cutIntoSearchinfoDTO(Searchinfo searchinfo){
        SearchinfoDTO dto=new SearchinfoDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setSearch_info_id(searchinfo.getSearchinfoId());
        dto.setSought_people_name(searchinfo.getSoughtPeopleName());
        Address address=addressMapper.selectById(searchinfo.getAddressId());
        dto.setArea_id(address.getAreaId());
        dto.setCity_id(address.getCityId());
        dto.setProvince_id(address.getProvinceId());
        dto.setDetail(address.getDetail());
        dto.setSearch_info_lostdate(TimeTrans.myToString(searchinfo.getSearchinfoLostdate()));
        dto.setSearch_info_photourl(searchinfo.getSearchinfoPhotoUrl());
        dto.setSought_people_birthday(TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
        dto.setSearch_type(searchinfo.getSearchType());
        dto.setSought_people_gender(searchinfo.getSoughtPeopleGender());
        dto.setContact_method(searchinfo.getContactMethod());
        //new added
        dto.setIsreport(searchinfo.getIsreport());
        dto.setSearch_info_date(TimeTrans.myToString(searchinfo.getSearchinfoDate()));
        dto.setSought_people_detail(searchinfo.getSoughtPeopleDetail());
        dto.setSought_people_height(searchinfo.getSoughtPeopleHeight());
        dto.setSought_people_state(searchinfo.getSoughtPeopleState());

        return dto;
    }

    public List<SearchinfoDTO> cutIntoSearchinfoDTOList(List<Searchinfo> searchinfoList){
        List<SearchinfoDTO> dtoList=new ArrayList<>();
        for(Searchinfo searchinfo : searchinfoList){
            SearchinfoDTO dto= cutIntoSearchinfoDTO(searchinfo);
            dtoList.add(dto);
        }
        return dtoList;
    }
}




