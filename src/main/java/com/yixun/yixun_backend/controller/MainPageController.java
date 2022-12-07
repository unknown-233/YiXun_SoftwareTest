package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.NewsDTO;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Clue;
import com.yixun.yixun_backend.entity.News;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.entity.Volunteer;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.VolunteerMapper;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/MainPage")
@RestController
public class MainPageController {
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private ClueMapper clueMapper;
    @Resource
    private VolunteerMapper volunteerMapper;
    //首页展示所有寻人信息
    @GetMapping("/GetAllSearchInfo")
    public Result GetAllSearchInfo(int pageNum, int pageSize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pageNum, pageSize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            List<SearchinfoDTO> dtoList=new ArrayList<>();
            wrapper.eq("ISACTIVE","Y").orderByDesc("SEARCHINFO_DATE");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
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

    //已发布项目，寻人信息总数
    @GetMapping("/GetSearchInfoNum")
    public String GetSearchInfoNum()
    {
        try
        {
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            String count=String.valueOf(searchinfoMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
    //已获得线索，线索总数
    @GetMapping("/GetCluesNum")
    public String GetCluesNum()
    {
        try
        {
            QueryWrapper<Clue> wrapper = new QueryWrapper<Clue>();
            String count=String.valueOf(clueMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }

    //累计已帮助，就是已经找到的信息
    @GetMapping("/GetFoundInfoNum")
    public String GetFoundInfoNum()
    {
        try
        {
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.eq("SOUGHT_PEOPLE_STATE","已找到");
            String count=String.valueOf(searchinfoMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }

    //注册志愿者数
    @GetMapping("/GetVolunteerNum")
    public String GetVolunteerNum()
    {
        try
        {
            QueryWrapper<Volunteer> wrapper = new QueryWrapper<Volunteer>();
            String count=String.valueOf(volunteerMapper.selectCount(wrapper));
            return count;
        }
        catch (Exception e) {
            return null;
        }
    }
}
