package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.entity.VolActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.VolActivity
*/
public interface VolActivityMapper extends BaseMapper<VolActivity> {
    List<VolActivity> selectVolActByVolID(int id);
}




