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
    public Result GetVolAct(int volid, int pagenum, int pagesize)
    {
        Result result=new Result();
        result=recruitedService.GetVolActRecruited(volid,pagenum,pagesize);
        return result;
    }

    //3.5 判断志愿者是否参与某个活动
    @GetMapping("/IfApplyActivity")
    public Result IfApplyActivity(int vol_id,int volAct_id)
    {
        Result result=new Result();
        result=recruitedService.GetIfAppliedActivity(vol_id,volAct_id);
        return result;
    }

    //4.1.4 报名&取消报名参加志愿活动
    @GetMapping("/SignupOrCancelVolActivity")
    public Result SignupOrCancelVolActivity(int VolId, int VolActId)
    {
        Result result=new Result();
        result=recruitedService.UpdateRecruitedState(VolId,VolActId);
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
}
