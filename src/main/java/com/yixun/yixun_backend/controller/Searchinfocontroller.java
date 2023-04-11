package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/api/SearchInfo")
@RestController
public class Searchinfocontroller {
    @Resource
    private SearchinfoService searchinfoService;
    
    //获取寻人详情页
    @GetMapping("/GetSearchInfo")
    public Result GetSearchInfo(int search_id)
    {
        Result result=new Result();
        result=searchinfoService.GetSearchInfoDetail(search_id);
        return result;
    }

    public Result GetSearchInfoToChange(int searchinfoId)
    {
        Result result=new Result();
        result=searchinfoService.GetSearchInfoToChange(searchinfoId);
        return result;

    }

}
