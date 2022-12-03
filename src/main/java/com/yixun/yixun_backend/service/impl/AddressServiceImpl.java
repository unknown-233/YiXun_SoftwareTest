package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.domain.Address;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author hunyingzhong
* @description 针对表【yixun_address】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




