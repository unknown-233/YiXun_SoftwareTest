package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.VolActivity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_activity】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolActivityService extends IService<VolActivity> {
    VolActivityDTO cutIntoVolActivityDTO(VolActivity volActivity);
    List<VolActivityDTO>  cutIntoVolActivityDTOList(List<VolActivity> volActivityList);
    public Result GetVolActivities(int volId,int pageNum, int pageSize);
    public Result GetVolActivityByWord(@RequestBody Map<String, Object> inputMap);
    public Result GetVolActivityDetail(int VolActId);
}
