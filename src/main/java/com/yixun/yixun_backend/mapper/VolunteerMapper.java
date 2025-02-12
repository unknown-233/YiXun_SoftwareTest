package com.yixun.yixun_backend.mapper;

import com.yixun.yixun_backend.dto.VolunteerDTO;
import com.yixun.yixun_backend.entity.Administrators;
import com.yixun.yixun_backend.entity.Volunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_volunteer】的数据库操作Mapper
* @createDate 2022-12-03 12:43:39
* @Entity com.yixun.yixun_backend.domain.Volunteer
*/
@Mapper
public interface VolunteerMapper extends BaseMapper<Volunteer> {
    List<VolunteerDTO> selectTopTenVolDTO();
    public Volunteer selectRandomOne();
    List<Volunteer> selectAreaNearVolID(String area);
    List<Volunteer> selectProvinceNearVolID(String province);
    List<Volunteer> selectCityNearVolID(String city);
}





