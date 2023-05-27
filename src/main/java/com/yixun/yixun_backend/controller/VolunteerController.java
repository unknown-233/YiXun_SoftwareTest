package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/api/Vol")
@RestController
public class VolunteerController {
    @Resource
    private SearchinfoFollowupService searchinfoFollowupService;
    @Resource
    private RecruitedService recruitedService;
    @Resource
    private VolApplyService volApplyService;
    @Resource
    private VolunteerService volunteerService;

    //3.1 展示志愿者跟进的所有寻人信息（LXK）
    @GetMapping("/GetFollowUpInfo")
    public Result GetFollowUpInfo(int volid, int pagenum, int pagesize)
    {
        Result result=new Result();
        result=searchinfoFollowupService.GetFollowUpInfo(volid,pagenum,pagesize);
        return result;
    }

    //3.2 展示志愿者报名的所有志愿活动
    @GetMapping("/GetVolAct")
    public Result GetVolAct(int userId, int pagenum, int pagesize)
    {
        Result result=new Result();
        result=recruitedService.GetVolActRecruited(userId,pagenum,pagesize);
        return result;
    }

    //3.5 判断志愿者是否参与某个活动
    @GetMapping("/IfApplyActivity")
    public Result IfApplyActivity(int userId,int volAct_id)
    {
        Result result=new Result();
        result=recruitedService.GetIfAppliedActivity(userId,volAct_id);
        return result;
    }

    //4.1.4 报名&取消报名参加志愿活动
    @GetMapping("/SignupOrCancelVolActivity")
    public Result SignupOrCancelVolActivity(int userId, int VolActId)
    {
        Result result=new Result();
        result=recruitedService.UpdateRecruitedState(userId,VolActId);
        return result;
    }

    //2.4 用户申请成为志愿者
    @PostMapping("/UserApplyVolunteer")
    public Result UserApplyVolunteer(@RequestBody Map<String, Object> inputMap)
    {
        Result result=new Result();
        result=volApplyService.AddVolApply(inputMap);
        return result;
    }

    //2.5 判断用户是否有正在审核中
    @GetMapping("/IsReviewApply")
    public Result IsReviewApply(int user_id)
    {
        Result result=new Result();
        result=volApplyService.GetIsReviewApply(user_id);
        return result;
    }

    //4.3.1 获取志愿时长排在前10的志愿者信息
    @GetMapping("/ShowTenVolunteer")
    public Result ShowTenVolunteer()
    {
        Result result=new Result();
        result=volunteerService.GetTenVolunteer();
        return result;
    }
    @PutMapping("/ChangeSearchinfoToFound")
    public Result ChangeSearchinfoToFound(int searchinfoId)
    {
        Result result=new Result();
        result=volunteerService.UpdateSearchinfoToFound(searchinfoId);
        return result;
    }
    @PutMapping("/ChangeClueConfirmed")
    public Result ChangeClueConfirmed(@RequestBody Map<String, Object> inputMap)
    {
        Result result=new Result();
        result=volunteerService.UpdateClueConfirmed(inputMap);
        return result;
    }
    @GetMapping("/SearchVolByDistinct")
    public Result SearchVolByDistinct(String city,int pagenum, int pagesize)
    {
        Result result=new Result();
        result=volunteerService.GetVolByDistinct(city,pagenum,pagesize);
        return result;
    }
}
