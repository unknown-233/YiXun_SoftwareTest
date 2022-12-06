package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_news】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface NewsService extends IService<News> {
    NewsDTO cutIntoNewsDTO(News news);
    List<NewsDTO> cutIntoNewsDTOList(List<News> newsList);
}
