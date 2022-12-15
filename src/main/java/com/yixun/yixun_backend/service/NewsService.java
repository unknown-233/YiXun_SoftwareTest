package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.NewsManageDTO;
import com.yixun.yixun_backend.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_news】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface NewsService extends IService<News> {
    NewsDTO cutIntoNewsDTO(News news);
    List<NewsDTO> cutIntoNewsDTOList(List<News> newsList);
    NewsManageDTO cutIntoNewsManageDTO(News news);
    List<NewsManageDTO> cutIntoNewsManageDTOList(List<News> newsList);
    public Result GetAllNews(String news_type, int pageNum, int pageSize);
    public Result GetOneNewsDetail(int news_id);
    public Result SelectNewsByWord(@RequestBody Map<String, Object> inputMap);
}
