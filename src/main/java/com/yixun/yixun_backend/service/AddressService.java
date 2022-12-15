package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_address】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface AddressService extends IService<Address> {
    String getProvinceID(int addressID);
    String getAreaID(int addressID);
    String getCityID(int addressID);
    String getDetail(int addressID);
}
