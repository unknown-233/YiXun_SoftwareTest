package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.dto.FollowVolDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/SearchInfo")
@RestController
public class Searchinfocontroller {
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private ClueService clueService;

    @Resource
    private AddressMapper addressMapper;
    
    //获取寻人详情页
    @GetMapping("/GetSearchInfo")
    public Result GetSearchInfo(int search_id)
    {
        try
        {
            Result result = new Result();
            Searchinfo searchinfo=searchinfoMapper.selectById(search_id);
            result.data.put("search_id", search_id);//data是字典，这是向result里面加输出的信息
            result.data.put("search_name", searchinfo.getSoughtPeopleName());
            result.data.put("search_birthday", TimeTrans.myToString(searchinfo.getSoughtPeopleBirthday()));
            result.data.put("search_gender", searchinfo.getSoughtPeopleGender());
            result.data.put("search_detail", searchinfo.getSoughtPeopleDetail());
            result.data.put("search_photo", searchinfo.getSearchinfoPhotoUrl());
            QueryWrapper<Clue> clueWrapper = new QueryWrapper<Clue>();
            clueWrapper.eq("SEARCHINFO_ID",search_id);
            List<Clue> clueDTOList=clueMapper.selectList(clueWrapper);
            result.data.put("search_clue",clueService.cutIntoClueDTOList(clueDTOList));
            FollowVolDTO followVolDTO=webUserMapper.selectVolDTOByInfoID(search_id);
            result.data.put("search_vols",followVolDTO);
            if (searchinfo.getAddressId() != null)
            {
                Address address=addressMapper.selectById(searchinfo.getAddressId());
                result.data.put("search_province", address.getProvinceId());
                result.data.put("search_city", address.getCityId());
                result.data.put("search_area", address.getAreaId());
                result.data.put("search_address", address.getDetail());
            }
            else
            {
                result.data.put("search_province", null);
                result.data.put("search_city", null);
                result.data.put("search_area", null);
                result.data.put("search_address", null);
            }
            
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}
