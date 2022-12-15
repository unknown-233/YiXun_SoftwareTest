package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.service.RelatedDpService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/api/RelatedDp")
@RestController
public class RelatedDpController {
    @Resource
    private RelatedDpService relatedDpMapperService;
    //8.1 展示相关部门信息卡片
    @GetMapping("/GetRelatedDps")
    public Result GetRelatedDps(String province,String city)
    {
        Result result=new Result();
        result=relatedDpMapperService.GetRelatedDpsCard(province,city);
        return result;
    }

    //8.2 展示相关部门详情
    @GetMapping("/GetDPDetail")
    public Result GetDPDetail(int DP_id)
    {
        Result result=new Result();
        result=relatedDpMapperService.GetRelatedDpDetail(DP_id);
        return result;
    }

}
