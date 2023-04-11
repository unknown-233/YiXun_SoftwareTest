package com.yixun.yixun_backend.controller;


import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Map;

@RequestMapping("/api/UserOperation")
@RestController
public class UserOperationController {
    @Resource
    private CluesReportService cluesReportService;
    @Resource
    private InfoReportService infoReportService;
    @Resource
    private SearchinfoFocusService searchinfoFocusService;
    @Resource
    private SearchinfoService searchinfoService;

    @Resource
    private ClueService clueService;
    //2.1 展示用户发布的所有寻人信息
    @GetMapping("/GetAllSearchInfoPublished")
    public Result GetAllSearchInfoPublished(int user_id,int pageNum, int pageSize)
    {
        Result result=new Result();
        result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);
        return result;
    }

    //2.2 展示用户发布的所有寻人线索
    @GetMapping("/GetAllCLuesPublished")
    public Result GetAllCLuesPublished(int user_id, int pageNum, int pageSize)
    {
        Result result=new Result();
        result=clueService.GetAllCLuesPublished(user_id,pageNum,pageSize);
        return result;
    }

    //2.3 展示用户关注的所有寻人信息（LXK）
    @GetMapping("/GetFocusInfo")
    public Result GetFocusInfo(int userid, int pagenum, int pagesize)
    {
        Result result=new Result();
        result=searchinfoFocusService.GetFocusInfo(userid,pagenum,pagesize);
        return result;
    }

    //5.1 发布寻人信息（LXK)
    @PostMapping("/AddSearchPeopleInfo")
    public Result AddSearchPeopleInfo(@RequestBody Map<String, Object> inputMap)
    {
        Result result=new Result();
        result=searchinfoService.AddSearchInfo(inputMap);
        return result;
    }
    //5.1-2上传寻人信息图片
    @PutMapping("/AddSearchInfoPic")
    public Result AddSearchInfoPic(@RequestBody Map<String, Object> inputData){
        Result result=new Result();
        result=searchinfoService.AddSearchInfoPic(inputData);
        return result;
    }

    //发布线索
    @PostMapping("/AddSearchPeopleClue")
    public Result AddSearchPeopleClue(@RequestBody Map<String, Object> inputMap){
        Result result=new Result();
        result=clueService.AddOneClue(inputMap);
        return result;
    }

    //发布线索举报
    @PostMapping("/AddSearchClueReport")
    public Result AddSearchClueReport(@RequestBody Map<String, Object> inputMap){
        Result result=new Result();
        result=cluesReportService.AddOneClueReport(inputMap);
        return result;
    }

    //寻人信息举报
    @PostMapping("/AddSearchInfoReport")
    public Result AddSearchInfoReport(@RequestBody Map<String, Object> inputMap){
        Result result=new Result();
        result=infoReportService.AddOneInfoReport(inputMap);
        return result;
    }

    //关注寻人信息
    @GetMapping("/UserFocus")
    public Result UserFocus(int userid, int infoid)
    {
        Result result=new Result();
        result=searchinfoFocusService.GetUserFocus(userid,infoid);
        return result;
    }

    //用户删除自己发布的寻人信息
    @DeleteMapping("/UserDeleteInfo")
    public Result UserDeleteInfo(int userid, int infoid){
        Result result=new Result();
        result=searchinfoService.DeleteInfoByUser(userid,infoid);
        return result;
    }

    //用户删除自己发布的寻人线索 *
    @DeleteMapping("/UserDeleteClue")
    public Result UserDeleteClue(int userid, int clueid)
    {
        Result result=new Result();
        result=clueService.DeleteClueByUser(userid,clueid);
        return result;
    }

    @PutMapping("/UserChangeInfo")
    public Result UserChangeInfo(@RequestBody Map<String, Object> inputMap){
        Result result=new Result();
        result=searchinfoService.UpdateInfoByUser(inputMap);
        return result;
    }

}

