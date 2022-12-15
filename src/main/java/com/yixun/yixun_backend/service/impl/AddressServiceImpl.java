package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_address】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{
    @Resource
    private AddressMapper addressMapper;

    public String getProvinceID(int addressID)
    {
        Address address=addressMapper.selectById(addressID);
        return address.getProvinceId();
    }
    public String getAreaID(int addressID)
    {
        Address address=addressMapper.selectById(addressID);
        return address.getAreaId();
    }
    public String getCityID(int addressID)
    {
        Address address=addressMapper.selectById(addressID);
        return address.getCityId();
    }
    public String getDetail(int addressID)
    {
        Address address=addressMapper.selectById(addressID);
        return address.getDetail();
    }

}




