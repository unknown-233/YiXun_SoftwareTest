package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.ClueDTO;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Clue;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/UserOperation")
@RestController
public class UserOperationController {
    @Resource
    private ClueMapper ClueMapper;

    @Resource
    private SearchinfoMapper searchinfoMapper;

    @Resource
    private SearchinfoService searchinfoService;

    @Resource
    private ClueService clueService;
    //2.1 展示用户发布的所有寻人信息
    @GetMapping("/GetAllSearchInfoPublished")
    public Result GetAllSearchInfoPublished(int user_id,int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("USER_ID",user_id);
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("searchInfo_list", dtoList);
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

    //2.2 展示用户发布的所有寻人线索
    @GetMapping("/GetAllCLuesPublished")
    public Result GetAllCLuesPublished(int user_id, int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Clue> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            wrapper.eq("USER_ID",user_id);
            IPage iPage = ClueMapper.selectPage(page, wrapper);
            List<ClueDTO> dtoList=clueService.cutIntoClueDTOList((List<Clue>)iPage.getRecords());
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.data.put("clue_list", dtoList);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //2.3 展示用户关注的所有寻人信息（LXK）
    @GetMapping("/GetFocusInfo")
    public Result GetFocusInfo(int userid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pagenum, pagesize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_focus where USER_ID ="+userid);
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("follow_info", dtoList);
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

    //5.1 发布寻人信息（LXK)
    @GetMapping("/AddSearchPeopleInfo")
    public Result AddSearchPeopleInfo(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result=new Result();
            int user_id = (int)inputMap.get("user_id");
            String search_type =(String)inputMap.get("search_type");
            String sought_people_name = (String)inputMap.get("sought_people_name");
            String sought_people_gender =(String)inputMap.get("sought_people_gender");
            String sought_people_height =(String)inputMap.get("sought_people_height");
            String sought_people_detail = (String)inputMap.get("sought_people_detail");
            String sought_people_birthday =(String)inputMap.get("sought_people_birthday");
            String sought_people_state = (String)inputMap.get("sought_people_state");
            String isreport = (String)inputMap.get("isreport");
            String searchinfo_lostdate = (String)inputMap.get("searchinfo_lostdate");
            String contact_method = (String)inputMap.get("contact_method");

            Searchinfo newSearchInfo = new Searchinfo();
            newSearchInfo.setUserId(user_id);
            newSearchInfo.setSearchType(search_type);
            newSearchInfo.setSoughtPeopleName(sought_people_name);
            newSearchInfo.setSoughtPeopleGender(sought_people_gender);
            newSearchInfo.setSoughtPeopleHeight(sought_people_height);
            newSearchInfo.setSoughtPeopleDetail(sought_people_detail);
            newSearchInfo.setSoughtPeopleBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sought_people_birthday));
            newSearchInfo.setSoughtPeopleState(sought_people_state);
            newSearchInfo.setIsreport(isreport);
            newSearchInfo.setSearchinfoLostdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(searchinfo_lostdate));
            newSearchInfo.setContactMethod(contact_method);

            //result.data.put("searchInfo_id", SearchinfoId);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

}

