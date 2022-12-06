package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.mapper.NewsMapper;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/api/News")
@RestController
public class NewsController {
    @Resource
    private NewsMapper newsMapper;

    @Resource
    private NewsService newsService;

    //7.1 展示寻人资讯卡片
    @GetMapping("/GetAllNews")
    public Result GetAllNews(int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<News> page = new Page<>(pagenum, pagesize);
            QueryWrapper<News> wrapper = new QueryWrapper<News>();
            IPage iPage = newsMapper.selectPage(page,wrapper);
            List<NewsDTO> dtoList=newsService.cutIntoNewsDTOList((List<News>)iPage.getRecords());
            result.data.put("news_list", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}
