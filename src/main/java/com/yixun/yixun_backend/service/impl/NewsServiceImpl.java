package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.NewsManageDTO;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.mapper.NewsMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_news】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{
    @Resource
    private NewsMapper newsMapper;
    public NewsDTO cutIntoNewsDTO(News news){
        NewsDTO dto=new NewsDTO();
        dto.setNewsContent(news.getNewsContent());
        dto.setNewsId(news.getNewsId());
        dto.setCover(news.getNewsTitlepagesUrl());
        dto.setTitle(news.getNewsHeadlines());
        return dto;
    }
    public NewsManageDTO cutIntoNewsManageDTO(News news){
        NewsManageDTO dto=new NewsManageDTO();
        dto.setNews_time(TimeTrans.myToString(news.getNewsTime()));
        dto.setNews_title(news.getNewsHeadlines());
        dto.setNews_type(news.getNewsType());
        dto.setNews_id(news.getNewsId());
        dto.setAdministrator_id(news.getAdministratorId());
        dto.setIsactive(news.getIsactive());
        return dto;
    }
    public List<NewsManageDTO> cutIntoNewsManageDTOList(List<News> newsList){
        List<NewsManageDTO> dtoList=new ArrayList<>();
        for(News news : newsList){
            NewsManageDTO dto=cutIntoNewsManageDTO(news);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<NewsDTO> cutIntoNewsDTOList(List<News> newsList){
        List<NewsDTO> dtoList=new ArrayList<>();
        for(News news : newsList){
            NewsDTO dto=cutIntoNewsDTO(news);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result GetAllNews(String news_type, int pageNum, int pageSize)
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
                dtoList=cutIntoNewsDTOList((List<News>)iPage.getRecords());

                result.data.put("news_list", dtoList);
                result.data.put("total", iPage.getTotal());
                result.data.put("getcount", iPage.getRecords().size());
            }
            else{
                wrapper.eq("NEWS_TYPE",news_type);
                IPage iPage = newsMapper.selectPage(page,wrapper);
                dtoList=cutIntoNewsDTOList((List<News>)iPage.getRecords());

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
    public Result GetOneNewsDetail(int news_id)
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
    public Result SelectNewsByWord(@RequestBody Map<String, Object> inputMap)
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
            List<NewsDTO> dtoList=cutIntoNewsDTOList((List<News>)iPage.getRecords());
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




