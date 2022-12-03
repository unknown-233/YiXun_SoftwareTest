package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.entity.Address;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper{
    //查询所有用户
    @Select("select * from yixun_address")
    List<Address> getAddress();

    @Insert("insert into yixun_address values (#{addressID},#{areaID},#{detail},#{cityID},#{provinceID})")
    int insert(Address address);
}
