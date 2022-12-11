package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolApplyInfoDTO;
import com.yixun.yixun_backend.entity.VolApply;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.VolApplyService;
import com.yixun.yixun_backend.mapper.VolApplyMapper;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_apply】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolApplyServiceImpl extends ServiceImpl<VolApplyMapper, VolApply>
    implements VolApplyService{
    @Resource
    private WebUserMapper webUserMapper;
    public VolApplyInfoDTO cutIntoVolApplyInfoList(VolApply apply) {
        VolApplyInfoDTO volApplyInfoDTO=new VolApplyInfoDTO();
        volApplyInfoDTO.setVol_apply_id(apply.getVolApplyId());
        WebUser user=webUserMapper.selectOne(new QueryWrapper<WebUser>().eq("USER_ID",apply.getUserId()));
        volApplyInfoDTO.setUser_state(user.getUserState());

        volApplyInfoDTO.setUser_id(apply.getUserId());

        volApplyInfoDTO.setUser_name(user.getUserName());
        volApplyInfoDTO.setCareer(apply.getCareer());
        volApplyInfoDTO.setSpecialty(apply.getSpecialty());
        volApplyInfoDTO.setBackground(apply.getBackground());
        volApplyInfoDTO.setApplication_time(TimeTrans.myToString(apply.getApplicationTime()));
        volApplyInfoDTO.setIspass(apply.getIspass());
        return volApplyInfoDTO;
    }
    public List<VolApplyInfoDTO> cutIntoVolApplyInfoDTOList(List<VolApply> list){
        List<VolApplyInfoDTO> dtoList=new ArrayList<>();
        for(VolApply apply : list){
            VolApplyInfoDTO dto=cutIntoVolApplyInfoList(apply);
            dtoList.add(dto);
        }
        return dtoList;
    }
}




