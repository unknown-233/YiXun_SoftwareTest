package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.entity.Searchinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yixun.yixun_backend.entity.VolActivity;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.Searchinfo
*/
public interface SearchinfoMapper extends BaseMapper<Searchinfo> {
    List<Searchinfo> selectFocusSearchInfoByUserID(int id);
}




