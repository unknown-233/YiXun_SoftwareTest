package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.domain.Address;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hunyingzhong
* @description 针对表【yixun_address】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.Address
*/

@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}




