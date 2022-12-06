package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.dto.RelatedDpDTO;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.RelatedDp;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.RelatedDpMapper;
import com.yixun.yixun_backend.service.RelatedDpService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/RelatedDp")
@RestController
public class RelatedDpController {
    @Resource
    private RelatedDpMapper relatedDpMapper;
    @Resource
    private RelatedDpService relatedDpMapperService;
    @Resource
    private AddressMapper addressMapper;
    //8.1 展示相关部门信息卡片
    @GetMapping("/GetRelatedDps")
    public Result GetRelatedDps(String province,String city)
    {
        try
        {
            Result result = new Result();
            List<String> areaList=addressMapper.selectAreaListByCityID(city);
            for(String area : areaList){
                QueryWrapper<RelatedDp> wrapper = new QueryWrapper<RelatedDp>();
                wrapper.inSql("ADDRESS_ID","SELECT ADDRESS_ID FROM yixun_address WHERE AREA_ID="+area);
                if(wrapper!=null)
                {
                    List<RelatedDpDTO> dtoList=relatedDpMapperService.cutIntoRelatedDpDTOList(relatedDpMapper.selectList(wrapper));
                    result.data.put(area, dtoList);
                }
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //8.2 展示相关部门详情
    @GetMapping("/GetDPDetail")
    public Result GetDPDetail(int DP_id)
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

            if (DP.getAddressId() != null)
            {
                Address address = addressMapper.selectById(DP.getAddressId());
                result.data.put("DP_city", address.getCityId());
                result.data.put("DP_address", address.getDetail());
            }
            else
                result.data.put("DP_address", DP.getAddressId());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}
