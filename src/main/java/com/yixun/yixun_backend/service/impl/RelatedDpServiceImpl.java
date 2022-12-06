package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.RelatedDpDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.RelatedDp;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.service.AddressService;
import com.yixun.yixun_backend.service.RelatedDpService;
import com.yixun.yixun_backend.mapper.RelatedDpMapper;
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
    private AddressService addressService;
    public RelatedDpDTO cutIntoRelatedDpDTO(RelatedDp relatedDp){
        RelatedDpDTO dto=new RelatedDpDTO();
        dto.setDpId(relatedDp.getDpId());
        dto.setDpName(relatedDp.getDpName());
        dto.setContactMethod(relatedDp.getContactMethod());
        dto.setArea(addressService.getAreaID(relatedDp.getAddressId()));
        dto.setCity(addressService.getCityID(relatedDp.getAddressId()));
        dto.setProvince(addressService.getProvinceID(relatedDp.getAddressId()));
        dto.setDetail(addressService.getDetail(relatedDp.getAddressId()));
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
}




