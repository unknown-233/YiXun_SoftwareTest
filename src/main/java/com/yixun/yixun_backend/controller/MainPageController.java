package com.yixun.yixun_backend.controller;
import com.yixun.yixun_backend.mapper.AddressMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.service.VolunteerService;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/api/MainPage")
@RestController
public class MainPageController {
    @Resource
    private SearchinfoService searchinfoService;

    @Resource
    private ClueService clueService;

    @Resource
    private VolunteerService volunteerService;
    @Resource
    private AddressMapper addressMapper;
    //首页展示所有寻人信息
    @GetMapping("/GetAllSearchInfo")
    public Result GetAllSearchInfo(int pageNum, int pageSize)
    {
        Result result=new Result();
        result=searchinfoService.GetAllSearchInfo(pageNum,pageSize);
        return result;
    }

    //已发布项目，寻人信息总数
    @GetMapping("/GetSearchInfoNum")
    public String GetSearchInfoNum()
    {
        String str="";
        str=searchinfoService.GetSearchInfoNumber();
        return str;
    }
    //已获得线索，线索总数
    @GetMapping("/GetCluesNum")
    public String GetCluesNum()
    {
        String str="";
        str=clueService.GetCluesNumber();
        return str;
    }

    //累计已帮助，就是已经找到的信息
    @GetMapping("/GetFoundInfoNum")
    public String GetFoundInfoNum()
    {
        String str="";
        str=searchinfoService.GetFoundNumber();
        return str;
    }

    //注册志愿者数
    @GetMapping("/GetVolunteerNum")
    public String GetVolunteerNum()
    {
        String str="";
        str=volunteerService.GetVolunteerNumber();
        return str;
    }
    @PostMapping("/ScreenSearchInfo")
    public Result ScreenSearchInfo(@RequestBody Map<String,Object> inputData){
        Result result=new Result();
        result=searchinfoService.SelectSearchInfoByConditions(inputData);
        return result;
    }
    //该地址码处的寻人信息
    @GetMapping("/GetSearchInfoPos")
    public Result GetSearchInfoPos(String infoAd){
        Result result=new Result();
        result=searchinfoService.GetSearchInfoInPosition(infoAd);
        return result;
    }

}
