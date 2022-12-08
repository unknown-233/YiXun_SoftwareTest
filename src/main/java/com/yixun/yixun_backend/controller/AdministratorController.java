package com.yixun.yixun_backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.mapper.NewsMapper;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.service.NewsService;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/Administrator")
@RestController
public class AdministratorController {
    @Resource
    private NewsMapper newsMapper;
    @Resource
    private OssUploadService ossUploadService;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private VolActivityMapper volActivityMapper;

    @PostMapping("/ReleaseNews")
    public Result ReleaseNews(@RequestBody Map<String, Object> inputData) {
        try {
            Result result = new Result();

            String adminIdKey = "admin_id";
            int adminId = 0;
            String newsContentKey = "news_content";
            String newsContent = "";
            String newsTitleKey = "news_title";
            String newsTitle = "";
            String newsTypeKey = "news_type";
            String newsType = "";
            if (inputData.containsKey(adminIdKey)) {
                adminId = (int) inputData.get(adminIdKey);
            }
            if (inputData.containsKey(newsContentKey)) {
                newsContent = (String) inputData.get(newsContentKey);
            }
            if (inputData.containsKey(newsTitleKey)) {
                newsTitle = (String) inputData.get(newsTitleKey);
            }
            if (inputData.containsKey(newsTypeKey)) {
                newsType = (String) inputData.get(newsTypeKey);
            }
            News news = new News();
            news.setAdministratorId(adminId);
            news.setNewsContent(newsContent);
            news.setNewsHeadlines(newsTitle);
            news.setNewsType(newsType);
            newsMapper.insert(news);
            List<News> tmpList = newsMapper.selectList(new QueryWrapper<News>().orderByDesc("NEWS_ID"));
            News newAddedNews = tmpList.get(0);
            result.data.put("news_id", newAddedNews.getNewsId());
            result.errorCode = 200;
            result.status = true;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }

    @PutMapping("/AddNewsCover")
    public Result AddNewsCover(@RequestBody Map<String, Object> inputData) {
        try {
            Result result = new Result();
            String idKey = "news_id";
            int news_id = 0;
            String img_base64Key = "news_cover";
            String img_base64 = "";
            if (inputData.containsKey(idKey)) {
                news_id = (int) inputData.get(idKey);
            }
            if (inputData.containsKey(img_base64Key)) {
                img_base64 = (String) inputData.get(img_base64Key);
            }
            News news = newsMapper.selectOne(new QueryWrapper<News>().eq("NEWS_ID", news_id));
            String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
            img_base64 = img_base64.split("base64,")[1];

            byte[] img_bytes = Base64.getDecoder().decode(img_base64);
            ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

            String path = "news_cover/" + Integer.toString(news_id) + type;
            ossUploadService.uploadfile(stream, path);
            String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
            news.setNewsTitlepagesUrl(imgurl);
            newsMapper.updateById(news);

            result.data.put("news_cover", imgurl);
            result.errorCode = 200;
            result.status = true;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    @PostMapping("/ReleaseVolActivity")
    public Result ReleaseVolActivity(@RequestBody Map<String,Object> inputData){
        try{
            Result result=new Result();
            String actNameKey="act_name";
            String actName="";
            String actContentKey="act_content";
            String actContent="";
            String actTimeKey="act_time";
            String actTime="";
            String actProvinceKey="act_province";
            String actProvince="";
            String actCityKey="act_city";
            String actCity="";
            String actAreaKey="act_area";
            String actArea="";
            String actAddressKey="act_address";
            String actAddress="";
            String contactMethodKey="contact_method";
            String contactMethod="";
            String needPeopleKey="need_people";
            int needPeople=0;
            if(inputData.containsKey(actNameKey)){
                actName=(String)inputData.get(actNameKey);
            }
            if(inputData.containsKey(actContentKey)){
                actContent=(String)inputData.get(actContentKey);
            }
            if(inputData.containsKey(actTimeKey)){
                actTime=(String)inputData.get(actTimeKey);
            }
            if(inputData.containsKey(actProvinceKey)){
                actProvince=(String)inputData.get(actProvinceKey);
            }
            if(inputData.containsKey(actCityKey)){
                actCity=(String)inputData.get(actCityKey);
            }
            if(inputData.containsKey(actAreaKey)){
                actArea=(String)inputData.get(actAreaKey);
            }
            if(inputData.containsKey(actAddressKey)){
                actAddress=(String)inputData.get(actAddressKey);
            }
            if(inputData.containsKey(contactMethodKey)){
                contactMethod=(String)inputData.get(contactMethodKey);
            }
            if(inputData.containsKey(needPeopleKey)){
                needPeople=(int)inputData.get(needPeopleKey);
            }
            VolActivity volActivity = new VolActivity();
            volActivity.setVolActName(actName);
            volActivity.setActContent(actContent);
            volActivity.setExpTime(TimeTrans.myToDate_1(actTime));
            volActivity.setContactMethod(contactMethod);
            volActivity.setNeedpeople(needPeople);
            if(actProvince!=""){
                Address address=new Address();
                address.setDetail(actAddress);
                address.setAreaId(actArea);
                address.setCityId(actCity);
                address.setProvinceId(actProvince);
                addressMapper.insert(address);
//                List<Address> tmpList=addressMapper.selectList(new QueryWrapper<Address>().orderByDesc("ADDRESS_ID"));
//                Address newAddress = tmpList.get(0);
                volActivity.setAddressId(address.getAddressId());
            }
            volActivityMapper.insert(volActivity);
            List<VolActivity> tmpList=volActivityMapper.selectList(new QueryWrapper<VolActivity>().orderByDesc("VOL_ACT_ID"));
            VolActivity newVolActivity = tmpList.get(0);
            result.data.put("volAct_id", newVolActivity.getVolActId());

            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e)
        {
            return Result.error();
        }
    }
    @PutMapping("/AddVolActivityPic")
    public Result AddVolActivityPic(@RequestBody Map<String,Object> inputData){
        try {
            Result result = new Result();
            String idKey = "volAct_id";
            int volActId = 0;
            String img_base64Key = "volAct_pic";
            String img_base64 = "";
            if (inputData.containsKey(idKey)) {
                volActId = (int) inputData.get(idKey);
            }
            if (inputData.containsKey(img_base64Key)) {
                img_base64 = (String) inputData.get(img_base64Key);
            }
            VolActivity volActivity = volActivityMapper.selectOne(new QueryWrapper<VolActivity>().eq("VOL_ACT_ID", volActId));
            String type = "." + img_base64.split(",")[0].split(";")[0].split("/")[1];
            img_base64 = img_base64.split("base64,")[1];

            byte[] img_bytes = Base64.getDecoder().decode(img_base64);
            ByteArrayInputStream stream = new ByteArrayInputStream(img_bytes, 0, img_bytes.length);

            String path = "volAct_pic/" + Integer.toString(volActId) + type;
            ossUploadService.uploadfile(stream, path);
            String imgurl = "https://yixun-picture.oss-cn-shanghai.aliyuncs.com/" + path;
            volActivity.setActPicUrl(imgurl);
            volActivityMapper.updateById(volActivity);
            result.errorCode = 200;
            result.status = true;
            return result;
        } catch (Exception e) {
            return Result.error();
        }

    }
}
