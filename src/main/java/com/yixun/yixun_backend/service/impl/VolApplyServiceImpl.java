package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolApplyInfoDTO;
import com.yixun.yixun_backend.entity.VolApply;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.VolApplyService;
import com.yixun.yixun_backend.mapper.VolApplyMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_vol_apply】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class VolApplyServiceImpl extends ServiceImpl<VolApplyMapper, VolApply>
    implements VolApplyService{
    @Resource
    private AdministratorsMapper administratorsMapper;
    @Resource
    private VolApplyMapper volApplyMapper;
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
    public Result AddVolApply(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result=new Result();
            VolApply newApply = new VolApply();
            QueryWrapper<VolApply> wrapper = new QueryWrapper<VolApply>();
            wrapper.eq("USER_ID",(int)inputMap.get("UserId"));
            if(volApplyMapper.selectCount(wrapper)>0) { //已申请
                return Result.error();
            }
            newApply.setUserId((int)inputMap.get("UserId"));
            newApply.setSpecialty((String)inputMap.get("Specialty"));
            newApply.setBackground((String)inputMap.get("Background"));
            newApply.setCareer((String)inputMap.get("Career"));
            newApply.setIspass("N");
            newApply.setIsreviewed("N");
            newApply.setApplicationTime(new Date());
            newApply.setAdministratorId(administratorsMapper.selectRandomOne().getAdministratorId());
            volApplyMapper.insert(newApply);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetIsReviewApply(int user_id)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<VolApply> wrapper = new QueryWrapper<VolApply>();
            wrapper.eq("USER_ID",user_id);
            VolApply volApply=volApplyMapper.selectOne(wrapper);
            if(volApplyMapper.selectCount(wrapper)>0){ //已申请
                if (volApply.getIsreviewed().equals("N"))
                    result.data.put("ApplyHistory", 1);
                else
                {
                    if (volApply.getIspass().equals("N"))
                        result.data.put("ApplyHistory", 2);
                    else
                        result.data.put("ApplyHistory", 3);
                }
            }
            else
                result.data.put("ApplyHistory", 0);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}




