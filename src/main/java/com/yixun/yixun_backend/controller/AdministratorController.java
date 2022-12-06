package com.yixun.yixun_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.mapper.NewsMapper;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/Administrator")
@RestController
public class AdministratorController {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private OssUploadService ossUploadService;

    @PostMapping("/ReleaseNews")
    public Result ReleaseNews(@RequestBody Map<String,Object> inputData){
        try
        {
            Result result=new Result();

            String adminIdKey="admin_id";
            int adminId=0;
            String newsContentKey="news_content";
            String newsContent="";
            String newsTitleKey="news_title";
            String newsTitle="";
            String newsTypeKey="news_type";
            String newsType="";
            if(inputData.containsKey(adminIdKey)){
                adminId=(int)inputData.get(adminIdKey);
            }
            if(inputData.containsKey(newsContentKey)){
                newsContent=(String)inputData.get(newsContentKey);
            }
            if(inputData.containsKey(newsTitleKey)){
                newsTitle=(String)inputData.get(newsTitleKey);
            }
            if(inputData.containsKey(newsTypeKey)){
                newsType=(String)inputData.get(newsTypeKey);
            }
            News news = new News();
            news.setAdministratorId(adminId);
            news.setNewsContent(newsContent);
            news.setNewsHeadlines(newsTitle);
            news.setNewsType(newsType);
            newsMapper.insert(news);
            List<News> tmpList=newsMapper.selectList(new QueryWrapper<News>().orderByDesc("NEWS_ID"));
            News newAddedNews = tmpList.get(0);
            result.data.put("news_id", newAddedNews.getNewsId());
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    @PutMapping("/AddNewsCover")
    public Result AddNewsCover(@RequestBody Map<String,Object> inputData){
        System.out.println(1);
        try{
            Result result=new Result();
            String idKey="news_id";
            int news_id=0;
            String img_base64Key="news_cover";
            String img_base64="";
            if(inputData.containsKey(idKey)){
                news_id=(int)inputData.get(idKey);
            }
            if(inputData.containsKey(img_base64Key)){
                img_base64=(String)inputData.get(img_base64Key);
            }
            News news = newsMapper.selectOne(new QueryWrapper<News>().eq("NEWS_ID", news_id));
            String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
            img_base64 = img_base64.split("base64,")[1];

            byte [] img_bytes = Base64.getDecoder().decode(img_base64);
            ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

            String path = "news_cover/" + Integer.toString(news_id) + type;
            ossUploadService.uploadfile(stream,path);
            String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
            news.setNewsTitlepagesUrl(imgurl);
            newsMapper.updateById(news);

            result.data.put("news_cover", imgurl);
            result.errorCode = 200;
            return result;
        }
        catch (Exception e)
        {
            return Result.error();
        }
    }
}
