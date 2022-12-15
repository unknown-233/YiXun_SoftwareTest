package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.RelatedDpDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.RelatedDp;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.RelatedDpService;
import com.yixun.yixun_backend.mapper.RelatedDpMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_related_dp】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class RelatedDpServiceImpl extends ServiceImpl<RelatedDpMapper, RelatedDp>
    implements RelatedDpService{
    @Resource
    private RelatedDpMapper relatedDpMapper;
    public RelatedDpDTO cutIntoRelatedDpDTO(RelatedDp relatedDp){
        RelatedDpDTO dto=new RelatedDpDTO();
        dto.setDpId(relatedDp.getDpId());
        dto.setDpName(relatedDp.getDpName());
        dto.setContactMethod(relatedDp.getContactMethod());
        dto.setArea(relatedDp.getAreaId());
        dto.setCity(relatedDp.getCityId());
        dto.setProvince(relatedDp.getProvinceId());
        dto.setDetail(relatedDp.getDetail());
        return dto;
    }
    public List<RelatedDpDTO> cutIntoRelatedDpDTOList(List<RelatedDp> relatedDpList){
        List<RelatedDpDTO> dtoList=new ArrayList<>();
        for(RelatedDp relatedDp : relatedDpList){
            RelatedDpDTO dto=cutIntoRelatedDpDTO(relatedDp);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result GetRelatedDpsCard(String province, String city)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<RelatedDp> wrapper = new QueryWrapper<RelatedDp>();
            wrapper.eq("CITY_ID",city).orderByAsc("AREA_ID");
            List<RelatedDp> dpList=relatedDpMapper.selectList(wrapper);
            List<RelatedDpDTO> dtoList=new ArrayList<>();
            if(dpList.isEmpty())
                return Result.error();
            else{
                String area=dpList.get(0).getAreaId();
                dtoList.add(cutIntoRelatedDpDTO(dpList.get(0)));
                for(int i=1;i<dpList.size();i++){
                    if(!dpList.get(i).getAreaId().equals(area))
                    {
                        result.data.put(area,new ArrayList<>(dtoList));
                        dtoList.clear();
                        area=dpList.get(i).getAreaId();
                        dtoList.add(cutIntoRelatedDpDTO(dpList.get(i)));
                    }
                    else{
                        dtoList.add(cutIntoRelatedDpDTO(dpList.get(i)));
                    }
                }
                result.data.put(area, dtoList);
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetRelatedDpDetail(int DP_id)
    {
        try
        {
            Result result = new Result();
            RelatedDp DP=relatedDpMapper.selectById(DP_id);
            result.data.put("DP_id", DP.getDpId());//data是字典，这是向result里面加输出的信息
            result.data.put("DP_name", DP.getDpName());
            result.data.put("DP_web", DP.getWebsite());
            result.data.put("DP_contact", DP.getContactMethod());
            result.data.put("DP_photo", DP.getDpPicUrl());
            result.data.put("DP_city", DP.getCityId());
            result.data.put("DP_address", DP.getDetail());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}




