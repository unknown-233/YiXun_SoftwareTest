package com.yixun.yixun_backend.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.NewsMapper;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/News")
@RestController
public class NewsController {
    @Resource
    private NewsMapper newsMapper;

    @Resource
    private NewsService newsService;
    @Resource
    private OssUploadService ossUploadService;

    //7.1 展示寻人资讯卡片
    @GetMapping("/GetAllNews")
    public Result GetAllNews(String news_type,int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<News> page = new Page<>(pageNum, pageSize);
            QueryWrapper<News> wrapper = new QueryWrapper<News>();
            List<NewsDTO> dtoList=new ArrayList<>();
            if(news_type.equals("全部"))
            {
                IPage iPage = newsMapper.selectPage(page,wrapper);
                dtoList=newsService.cutIntoNewsDTOList((List<News>)iPage.getRecords());

                result.data.put("news_list", dtoList);
                result.data.put("total", iPage.getTotal());
                result.data.put("getcount", iPage.getRecords().size());
            }
            else{
                wrapper.eq("NEWS_TYPE",news_type);
                IPage iPage = newsMapper.selectPage(page,wrapper);
                dtoList=newsService.cutIntoNewsDTOList((List<News>)iPage.getRecords());

                result.data.put("news_list", dtoList);
                result.data.put("total", iPage.getTotal());
                result.data.put("getcount", iPage.getRecords().size());
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //7.2 展示寻人资讯详情
    @GetMapping("/GetNewsDetail")
    public Result GetNewsDetail(int news_id)
    {
        try
        {
            Result result = new Result();
            News  news=newsMapper.selectById(news_id);
            result.data.put("news_id", news.getNewsId());
            result.data.put("news_content", news.getNewsContent());
            result.data.put("news_time", TimeTrans.myToString(news.getNewsTime()));
            result.data.put("news_title", news.getNewsHeadlines());
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //7.3 搜索寻人资讯
    @PostMapping("/SearchNews")
    public Result SearchNews(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result = new Result();
            int pageNum=(int)inputMap.get("pageNum");
            int pageSize=(int)inputMap.get("pageSize");
            String searchContent=(String)inputMap.get("search");
            Page<News> page = new Page<>(pageNum, pageSize);
            QueryWrapper<News> wrapper = new QueryWrapper<News>();
            wrapper.like("NEWS_HEADLINES",searchContent);
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
