package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.*;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.OssUploadService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
* @author hunyingzhong
* @description 针对表【yixun_administrators】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class AdministratorsServiceImpl extends ServiceImpl<AdministratorsMapper, Administrators>
    implements AdministratorsService{

    @Resource
    private NewsMapper newsMapper;
    @Resource
    private OssUploadService ossUploadService;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private WebUserMapper webUserMapper;
    @Resource
    private CluesReportMapper cluesReportMapper;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private WebUserService webUserService;
    @Resource
    private VolApplyService volApplyService;
    @Resource
    private InfoReportService infoReportService;
    @Resource
    private VolunteerMapper volunteerMapper;
    @Resource
    private VolunteerService volunteerService;
    @Resource
    private CluesReportService cluesReportService;
    @Resource
    private NewsService newsService;
    @Resource
    private VolApplyMapper volApplyMapper;
    @Resource
    private InfoReportMapper infoReportMapper;
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoFocusMapper searchinfoFocusMapper;
    @Resource
    private SearchinfoFollowupMapper searchinfoFollowupMapper;
    @Resource
    private RecruitedMapper recruitedMapper;
    public Result Addnews(@RequestBody Map<String, Object> inputData) {
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
    public Result AddVolActivity(@RequestBody Map<String, Object> inputData) {
        try {
            Result result = new Result();
            String actNameKey = "act_name";
            String actName = "";
            String actContentKey = "act_content";
            String actContent = "";
            String actTimeKey = "act_time";
            String actTime = "";
            String actProvinceKey = "act_province";
            String actProvince = "";
            String actCityKey = "act_city";
            String actCity = "";
            String actAreaKey = "act_area";
            String actArea = "";
            String actAddressKey = "act_address";
            String actAddress = "";
            String contactMethodKey = "contact_method";
            String contactMethod = "";
            String needPeopleKey = "need_people";
            String endTimeKey = "end_time";
            String endTime = "";
            int needPeople = 0;
            if (inputData.containsKey(endTimeKey)) {
                endTime = (String) inputData.get(endTimeKey);
            }
            if (inputData.containsKey(actNameKey)) {
                actName = (String) inputData.get(actNameKey);
            }
            if (inputData.containsKey(actContentKey)) {
                actContent = (String) inputData.get(actContentKey);
            }
            if (inputData.containsKey(actTimeKey)) {
                actTime = (String) inputData.get(actTimeKey);
            }
            if (inputData.containsKey(actProvinceKey)) {
                actProvince = (String) inputData.get(actProvinceKey);
            }
            if (inputData.containsKey(actCityKey)) {
                actCity = (String) inputData.get(actCityKey);
            }
            if (inputData.containsKey(actAreaKey)) {
                actArea = (String) inputData.get(actAreaKey);
            }
            if (inputData.containsKey(actAddressKey)) {
                actAddress = (String) inputData.get(actAddressKey);
            }
            if (inputData.containsKey(contactMethodKey)) {
                contactMethod = (String) inputData.get(contactMethodKey);
            }
            if (inputData.containsKey(needPeopleKey)) {
                needPeople = (int) inputData.get(needPeopleKey);
            }
            VolActivity volActivity = new VolActivity();
            volActivity.setVolActName(actName);
            volActivity.setActContent(actContent);
            volActivity.setExpTime(TimeTrans.myToDate_1(actTime));
            volActivity.setEndTime(TimeTrans.myToDate_1(endTime));
            volActivity.setContactMethod(contactMethod);
            volActivity.setNeedpeople(needPeople);
            if (actProvince != "") {
                Address address = new Address();
                address.setDetail(actAddress);
                address.setAreaId(actArea);
                address.setCityId(actCity);
                address.setProvinceId(actProvince);
                addressMapper.insert(address);
                List<Address> tmpList = addressMapper.selectList(new QueryWrapper<Address>().orderByDesc("ADDRESS_ID"));
                Address newAddress = tmpList.get(0);
                volActivity.setAddressId(newAddress.getAddressId());
            }
            volActivityMapper.insert(volActivity);
            List<VolActivity> tmpList = volActivityMapper.selectList(new QueryWrapper<VolActivity>().orderByDesc("VOL_ACT_ID"));
            VolActivity newVolActivity = tmpList.get(0);
            result.data.put("volAct_id", newVolActivity.getVolActId());

            result.errorCode = 200;
            result.status = true;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result AddVolActivityPic(@RequestBody Map<String, Object> inputData) {
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
    public Result GetAllNormalUsers(int pagenum, int pagesize) {
        try {
            Result result = new Result();
            Page<WebUser> page = new Page<WebUser>(pagenum, pagesize);
            QueryWrapper<WebUser> wrapper = new QueryWrapper<WebUser>();
            IPage iPage = webUserMapper.selectPage(page, wrapper);
            List<UserInfoDTO> dtoList = webUserService.cutIntoUserInfoList((List<WebUser>) iPage.getRecords());
            result.data.put("user_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateUserToBan(int userid) {
        try {
            Result result = new Result();
            WebUser user = webUserMapper.selectById(userid);
            if (user.getUserState().equals("N")) {
                user.setUserState("Y");
            } else {
                user.setUserState("N");
            }
            webUserMapper.updateById(user);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result DeleteUser(int userid) {
        try {
            Result result = new Result();
            WebUser user = webUserMapper.selectById(userid);
            user.setUserName("账号已注销");
            user.setUserState("N");
            user.setIsactive("N");
            webUserMapper.updateById(user);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetUserByName(String word, int pagenum, int pagesize) {
        try {
            Result result = new Result();
            Page<WebUser> page = new Page<WebUser>(pagenum, pagesize);
            QueryWrapper<WebUser> wrapper = new QueryWrapper<WebUser>();
            wrapper.like("USER_NAME", word);
            IPage iPage = webUserMapper.selectPage(page, wrapper);
            List<UserInfoDTO> dtoList = webUserService.cutIntoUserInfoList((List<WebUser>) iPage.getRecords());
            result.data.put("user_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetAllVolunteers(int pagenum, int pagesize) {
        try {
            Result result = new Result();
            Page<Volunteer> page = new Page<Volunteer>(pagenum, pagesize);
            QueryWrapper<Volunteer> wrapper = new QueryWrapper<Volunteer>();
            IPage iPage = volunteerMapper.selectPage(page, wrapper);
            List<VolInfoDTO> dtoList = volunteerService.cutIntoVolInfoList((List<Volunteer>) iPage.getRecords());
            result.data.put("vol_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetVolApplyNumber(int adminId) {
        try {
            Result result = new Result();
            QueryWrapper<VolApply> wrapperY = new QueryWrapper<VolApply>();
            wrapperY.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "Y");
            Map<String, Object> dto = new HashMap<>();
            dto.put("vol_apply_reviewed", volApplyMapper.selectCount(wrapperY));
            QueryWrapper<VolApply> wrapperN = new QueryWrapper<VolApply>();
            wrapperN.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "N");
            dto.put("vol_apply_notreviewed", volApplyMapper.selectCount(wrapperN));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(dto);
            result.data.put("vol_apply_review", list);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetInfoRepoNumber(int adminId) {
        try {
            Result result = new Result();
            QueryWrapper<InfoReport> wrapperY = new QueryWrapper<InfoReport>();
            wrapperY.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "Y");
            Map<String, Object> dto = new HashMap<>();
            dto.put("info_repo_reviewed", infoReportMapper.selectCount(wrapperY));
            QueryWrapper<InfoReport> wrapperN = new QueryWrapper<InfoReport>();
            wrapperN.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "N");
            dto.put("info_repo_notreviewed", infoReportMapper.selectCount(wrapperN));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(dto);
            result.data.put("info_repo_review", list);

            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetClueRepoNumber(int adminId) {
        try {
            Result result = new Result();
            QueryWrapper<CluesReport> wrapperY = new QueryWrapper<CluesReport>();
            wrapperY.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "Y");
            Map<String, Object> dto = new HashMap<>();
            dto.put("clue_repo_reviewed", cluesReportMapper.selectCount(wrapperY));
            QueryWrapper<CluesReport> wrapperN = new QueryWrapper<CluesReport>();
            wrapperN.eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", "N");
            dto.put("clue_repo_notreviewed", cluesReportMapper.selectCount(wrapperN));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(dto);
            result.data.put("clue_repo_review", list);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetVolApplyReviewed(int adminId, int pagenum, int pagesize, String review) {
        try {
            Result result = new Result();
            int count = volApplyMapper.selectCount(new QueryWrapper<VolApply>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review));
            result.data.put("total", count);
            Page<VolApply> page = new Page<VolApply>(pagenum, pagesize);
            QueryWrapper<VolApply> wrapper = new QueryWrapper<VolApply>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review);

            IPage iPage = volApplyMapper.selectPage(page, wrapper);
            List<VolApplyInfoDTO> dtoList = volApplyService.cutIntoVolApplyInfoDTOList((List<VolApply>) iPage.getRecords());
            result.data.put("vol_apply", dtoList);
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateVolApplyToPass(int volapplyid) {
        try {
            Result result = new Result();
            VolApply volApply = volApplyMapper.selectById(volapplyid);
            List<VolApply> applyList = volApplyMapper.selectList(new QueryWrapper<VolApply>().eq("USER_ID", volApply.getUserId()));
            for (int i = 0; i < applyList.size(); i++) {
                VolApply apply = applyList.get(i);
                apply.setIsreviewed("Y");
                apply.setIspass("Y");
                volApplyMapper.updateById(apply);
            }
            Volunteer volunteer = new Volunteer();
            volunteer.setVolUserId(volApply.getUserId());
            volunteerMapper.insert(volunteer);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateVolApplyToDeny(int volapplyid) {
        try {
            Result result = new Result();
            VolApply volApply = volApplyMapper.selectById(volapplyid);
            volApply.setIspass("N");
            volApply.setIsreviewed("Y");
            volApplyMapper.updateById(volApply);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result DeleteNews(int newsid) {
        try {
            Result result = new Result();
            News news = newsMapper.selectById(newsid);
            news.setIsactive("N");
            newsMapper.updateById(news);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetReviewedInfoRepo(int adminId, int pagenum, int pagesize, String review) {
        try {
            Result result = new Result();
            int count = infoReportMapper.selectCount(new QueryWrapper<InfoReport>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review));
            result.data.put("total", count);

            Page<InfoReport> page = new Page<InfoReport>(pagenum, pagesize);
            QueryWrapper<InfoReport> wrapper = new QueryWrapper<InfoReport>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review);

            IPage iPage = infoReportMapper.selectPage(page, wrapper);
            List<InfoRepoInfoDTO> dtoList = infoReportService.cutIntoInfoRepoList((List<InfoReport>) iPage.getRecords());
            result.data.put("info_repo", dtoList);
            result.data.put("getcount", iPage.getRecords().size());

            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetReviewedClueRepo(int adminId, int pagenum, int pagesize, String review) {
        try {
            Result result = new Result();
            int count = cluesReportMapper.selectCount(new QueryWrapper<CluesReport>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review));
            result.data.put("total", count);

            Page<CluesReport> page = new Page<CluesReport>(pagenum, pagesize);
            QueryWrapper<CluesReport> wrapper = new QueryWrapper<CluesReport>().eq("ADMINISTRATOR_ID", adminId).eq("ISREVIEWED", review);
            IPage iPage = cluesReportMapper.selectPage(page, wrapper);
            List<ClueRepoInfoDTO> dtoList = cluesReportService.cutIntoCluesRepoList((List<CluesReport>) iPage.getRecords());
            result.data.put("clue_repo", dtoList);
            result.data.put("getcount", iPage.getRecords().size());

            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateInfoRepoToPass(int infoid) {
        try {
            Result result = new Result();
            Searchinfo searchinfo = searchinfoMapper.selectById(infoid);
            searchinfo.setIsactive("N");
            searchinfoMapper.updateById(searchinfo);
            List<InfoReport> tmpList = infoReportMapper.selectList(new QueryWrapper<InfoReport>().eq("SEARCHINFO_ID", infoid));
            for (int i = 0; i < tmpList.size(); i++) {
                InfoReport tmp = tmpList.get(i);
                tmp.setIsreviewed("Y");
                tmp.setIspass("Y");
                infoReportMapper.updateById(tmp);
            }
            QueryWrapper<SearchinfoFocus> wrapper = new QueryWrapper<SearchinfoFocus>();
            wrapper.eq("SEARCHINFO_ID", infoid);
            searchinfoFocusMapper.delete(wrapper);//从searchinfoFocus表中删去数据
            QueryWrapper<SearchinfoFollowup> wrapper2 = new QueryWrapper<SearchinfoFollowup>();
            wrapper2.eq("SEARCHINFO_ID", infoid);
            searchinfoFollowupMapper.delete(wrapper2);//从searchinfoFocus表中删去这条数据
            List<Clue> clueList = clueMapper.selectList(new QueryWrapper<Clue>().eq("SEARCHINFO_ID", infoid));
            for (int i = 0; i < clueList.size(); i++) {
                Clue clue = clueList.get(i);
                clue.setIsactive("N");
                List<CluesReport> cluesReportList = cluesReportMapper.selectList(new QueryWrapper<CluesReport>().eq("CLUE_ID", clue.getClueId()));
                for (int j = 0; j < cluesReportList.size(); j++) {
                    CluesReport repo = cluesReportList.get(j);
                    repo.setIspass("Y");
                    repo.setIsreviewed("Y");
                    cluesReportMapper.updateById(repo);
                }
                clueMapper.updateById(clue);
            }


            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateInfoRepoToDeny(int inforepoid) {
        try {
            Result result = new Result();
            InfoReport infoReport = infoReportMapper.selectById(inforepoid);
            infoReport.setIspass("N");
            infoReport.setIsreviewed("Y");
            infoReportMapper.updateById(infoReport);
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateClueRepToPass(int clueId) {
        try {
            Result result = new Result();
            Clue clue = clueMapper.selectById(clueId);
            clue.setIsactive("N");
            clueMapper.updateById(clue);
            List<CluesReport> tmpList = cluesReportMapper.selectList(new QueryWrapper<CluesReport>().eq("CLUE_ID", clueId));
            for (int i = 0; i < tmpList.size(); i++) {
                CluesReport tmp = tmpList.get(i);
                tmp.setIsreviewed("Y");
                tmp.setIspass("Y");
                cluesReportMapper.updateById(tmp);
            }

            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result UpdateClueRepoToDeny(int cluerepoid) {
        try {
            Result result = new Result();
            CluesReport cluesReport = cluesReportMapper.selectById(cluerepoid);
            cluesReport.setIspass("N");
            cluesReport.setIsreviewed("Y");
            cluesReportMapper.updateById(cluesReport);

            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result GetAllNews(int pagenum, int pagesize) {
        try {
            Result result = new Result();
            Page<News> page = new Page<>(pagenum, pagesize);
            QueryWrapper<News> wrapper = new QueryWrapper<News>();
            List<NewsManageDTO> dtoList = new ArrayList<>();
            IPage iPage = newsMapper.selectPage(page, wrapper);
            dtoList = newsService.cutIntoNewsManageDTOList((List<News>) iPage.getRecords());

            result.data.put("news_info", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }

    public Result UpdateVolActivity(@RequestBody Map<String, Object> inputData)
    {
        try{
            Result result = new Result();
            String actIdKey="act_id";
            String actNameKey = "act_name";
            String actName = "";
            String actContentKey = "act_content";
            String actContent = "";
            String actTimeKey = "act_time";
            String actTime = "";
            String endTimeKey = "end_time";
            String endTime = "";
            String actProvinceKey = "act_province";
            String actProvince = "";
            String actCityKey = "act_city";
            String actCity = "";
            String actAreaKey = "act_area";
            String actArea = "";
            String actAddressKey = "act_address";
            String actAddress = "";
            String contactMethodKey = "contact_method";
            String contactMethod = "";
            String needPeopleKey = "need_people";
            int needPeople = 0;
            int actId=-1;
            if (inputData.containsKey(needPeopleKey)) {
                actId = (int) inputData.get(actIdKey);
            }
            if (inputData.containsKey(actNameKey)) {
                actName = (String) inputData.get(actNameKey);
            }
            if (inputData.containsKey(actContentKey)) {
                actContent = (String) inputData.get(actContentKey);
            }
            if (inputData.containsKey(actTimeKey)) {
                actTime = (String) inputData.get(actTimeKey);
            }
            if (inputData.containsKey(endTimeKey)) {
                endTime = (String) inputData.get(endTimeKey);
            }
            if (inputData.containsKey(actProvinceKey)) {
                actProvince = (String) inputData.get(actProvinceKey);
            }
            if (inputData.containsKey(actCityKey)) {
                actCity = (String) inputData.get(actCityKey);
            }
            if (inputData.containsKey(actAreaKey)) {
                actArea = (String) inputData.get(actAreaKey);
            }
            if (inputData.containsKey(actAddressKey)) {
                actAddress = (String) inputData.get(actAddressKey);
            }
            if (inputData.containsKey(contactMethodKey)) {
                contactMethod = (String) inputData.get(contactMethodKey);
            }
            if (inputData.containsKey(needPeopleKey)) {
                needPeople = (int) inputData.get(needPeopleKey);
            }
            VolActivity volActivity=volActivityMapper.selectById(actId);
            volActivity.setVolActName(actName);
            volActivity.setActContent(actContent);
            volActivity.setExpTime(TimeTrans.myToDate_1(actTime));
            volActivity.setEndTime(TimeTrans.myToDate_1(endTime));
            volActivity.setContactMethod(contactMethod);
            volActivity.setNeedpeople(needPeople);
            if(actProvince!=""){
                Address address=addressMapper.selectById(volActivity.getAddressId());
                if (address==null){
                    Address address1=new Address();
                    address1.setProvinceId(actProvince);
                    address1.setCityId(actCity);
                    address1.setAreaId(actArea);
                    address1.setDetail(actAddress);
                    addressMapper.insert(address1);
                }
                else{
                    address.setProvinceId(actProvince);
                    address.setCityId(actCity);
                    address.setAreaId(actArea);
                    address.setDetail(actAddress);
                    addressMapper.updateById(address);
                }
            }
            volActivityMapper.updateById(volActivity);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
    public Result DeleteVolActivity(int actId)
    {
        try{
            Result result = new Result();

            LambdaQueryWrapper<Recruited> wrapper2 = new QueryWrapper<Recruited>().lambda().eq(Recruited::getVolActId, actId);
            recruitedMapper.delete(wrapper2);
            //原来的方法只能删掉一行，这个可以删掉所有符合条件的
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("VOL_ACT_ID", actId);
            searchinfoMapper.delete(wrapper);//从searchinfoFocus表中删去这条数据

            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}




