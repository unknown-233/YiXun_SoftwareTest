package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.VolInfoDTO;
import com.yixun.yixun_backend.dto.VolunteerDTO;
import com.yixun.yixun_backend.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_volunteer】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface VolunteerService extends IService<Volunteer> {
    public VolInfoDTO cutIntoVolInfoDTO(Volunteer vol);
    public List<VolInfoDTO> cutIntoVolInfoList(List<Volunteer> volList);
    public String GetVolunteerNumber();
    public Result GetTenVolunteer();
    public Result UpdateSearchinfoToFound(int searchinfoId);
    public Result UpdateClueConfirmed(@RequestBody Map<String, Object> inputMap);
    public VolunteerDTO cutIntoVolunteerDTO(Volunteer vol);
    public List<VolunteerDTO> cutIntoVolunteerDTOList(List<Volunteer> list);
    public Result GetVolByDistinct(String city,int pagenum, int pagesize);
}
