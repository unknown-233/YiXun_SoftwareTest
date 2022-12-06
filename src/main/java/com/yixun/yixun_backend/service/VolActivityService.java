package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.VolActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolActivityService extends IService<VolActivity> {
    VolActivityDTO cutIntoVolActivityDTO(VolActivity volActivity);
    List<VolActivityDTO>  cutIntoVolActivityDTOList(List<VolActivity> volActivityList);
}
