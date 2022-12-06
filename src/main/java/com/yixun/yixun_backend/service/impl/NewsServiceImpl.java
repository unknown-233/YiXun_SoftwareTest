package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_news】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News>
    implements NewsService{

    public NewsDTO cutIntoNewsDTO(News news){
        NewsDTO dto=new NewsDTO();
        dto.setNewsContent(news.getNewsContent());
        dto.setNewsId(news.getNewsId());
        dto.setCover(news.getNewsTitlepagesUrl());
        dto.setTitle(news.getNewsHeadlines());
        return dto;
    }

    public List<NewsDTO> cutIntoNewsDTOList(List<News> newsList){
        List<NewsDTO> dtoList=new ArrayList<>();
        for(News news : newsList){
            NewsDTO dto=cutIntoNewsDTO(news);
            dtoList.add(dto);
        }
        return dtoList;
    }

}




