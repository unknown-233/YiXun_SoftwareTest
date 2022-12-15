package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.utils.Result;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

@RequestMapping("/api/News")
@RestController
public class NewsController {

    @Resource
    private NewsService newsService;

    //7.1 展示寻人资讯卡片
    @GetMapping("/GetAllNews")
    public Result GetAllNews(String news_type,int pageNum, int pageSize)
    {
        Result result=new Result();
        result=newsService.GetAllNews(news_type,pageNum,pageSize);
        return result;
    }

    //7.2 展示寻人资讯详情
    @GetMapping("/GetNewsDetail")
    public Result GetNewsDetail(int news_id)
    {
        Result result=new Result();
        result=newsService.GetOneNewsDetail(news_id);
        return result;
    }

    //7.3 搜索寻人资讯
    @PostMapping("/SearchNews")
    public Result SearchNews(@RequestBody Map<String, Object> inputMap)
    {
        Result result=new Result();
        result=newsService.SelectNewsByWord(inputMap);
        return result;
    }


}
