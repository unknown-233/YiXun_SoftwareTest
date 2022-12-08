package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolInfoDTO;
import com.yixun.yixun_backend.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_volunteer】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolunteerService extends IService<Volunteer> {
    VolInfoDTO cutIntoVolInfoDTO(Volunteer vol);
    List<VolInfoDTO> cutIntoVolInfoList(List<Volunteer> volList);

}
