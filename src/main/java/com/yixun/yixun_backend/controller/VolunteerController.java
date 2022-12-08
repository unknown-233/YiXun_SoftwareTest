package com.yixun.yixun_backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.dto.VolunteerDTO;
import com.yixun.yixun_backend.entity.*;
import com.yixun.yixun_backend.mapper.*;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/Vol")
@RestController
public class VolunteerController {
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private VolActivityService volActivityService;
    @Resource
    private RecruitedMapper recruitedMapper;
    @Resource
    private AdministratorsMapper administratorsMapper;
    @Resource
    private VolApplyMapper volApplyMapper;
    @Resource
    private VolunteerMapper volunteerMapper;

    //3.1 展示志愿者跟进的所有寻人信息（LXK）
    @GetMapping("/GetFollowUpInfo")
    public Result GetFollowUpInfo(int volid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pagenum, pagesize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_followup where VOL_ID ="+volid).eq("ISACTIVE","Y");
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("followup_info", dtoList);
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

    //3.2 展示志愿者报名的所有志愿活动
    @GetMapping("/GetVolAct")
    public Result GetVolAct(int volid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<VolActivity> page = new Page<>(pagenum, pagesize);
            QueryWrapper<VolActivity> wrapper = new QueryWrapper<VolActivity>();
            wrapper.inSql("VOL_ACT_ID","select VOL_ACT_ID from yixun_recruited where VOL_ID="+volid);
            IPage iPage = volActivityMapper.selectPage(page,wrapper);
            List<VolActivityDTO> dtoList=volActivityService.cutIntoVolActivityDTOList((List<VolActivity>)iPage.getRecords());
            result.data.put("vol_act_info", dtoList);
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

    //3.5 判断志愿者是否参与某个活动
    @GetMapping("/IfApplyActivity")
    public Result IfApplyActivity(int vol_id,int volAct_id)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<Recruited> wrapper = new QueryWrapper<Recruited>();
            wrapper.eq("VOL_ID",vol_id).eq("VOL_ACT_ID",volAct_id);
            int ifApply=recruitedMapper.selectCount(wrapper);
            if(ifApply==1)
            {
                result.data.put("is_applied", "true");
            }
            else
            {
                result.data.put("is_applied", "false");
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //4.1.4 报名&取消报名参加志愿活动
    @GetMapping("/SignupOrCancelVolActivity")
    public Result SignupOrCancelVolActivity(int VolId, int VolActId)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<Recruited> wrapper = new QueryWrapper<Recruited>();
            wrapper.eq("VOL_ID",VolId).eq("VOL_ACT_ID",VolActId);
            int ifApply=recruitedMapper.selectCount(wrapper);
            VolActivity curVolAct=volActivityMapper.selectById(VolActId);
            if(ifApply==1) //已报名
            {
                curVolAct.setSignupPeople(curVolAct.getSignupPeople()-1); //取消报名，人数减少
                volActivityMapper.updateById(curVolAct);
                recruitedMapper.delete(wrapper);//从recruit表中删去这条数据
                result.data.put("ApplyState", false);
            }
            else
            {
                Recruited newRecruit = new Recruited();
                newRecruit.setVolActId(VolActId);
                newRecruit.setVolId(VolId);
                newRecruit.setRecruittime(new Date());
                recruitedMapper.insert(newRecruit);
                curVolAct.setSignupPeople(curVolAct.getSignupPeople()+1); //报名，人数增加
                volActivityMapper.updateById(curVolAct);
                result.data.put("ApplyState", true);
            }
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //2.4 用户申请成为志愿者
    @PostMapping("/UserApplyVolunteer")
    public Result UserApplyVolunteer(@RequestBody Map<String, Object> inputMap)
    {
        try
        {
            Result result=new Result();
            VolApply newApply = new VolApply();
            QueryWrapper<VolApply> wrapper = new QueryWrapper<VolApply>();
            wrapper.eq("USER_ID",(int)inputMap.get("UserId"));
            if(volApplyMapper.selectCount(wrapper)>0) { //已申请
                return Result.error();
            }
            newApply.setUserId((int)inputMap.get("UserId"));
            newApply.setSpecialty((String)inputMap.get("Specialty"));
            newApply.setBackground((String)inputMap.get("Background"));
            newApply.setCareer((String)inputMap.get("Career"));
            newApply.setIspass("N");
            newApply.setIsreviewed("N");
            newApply.setApplicationTime(new Date());
            newApply.setAdministratorId(administratorsMapper.selectRandomOne().getAdministratorId());
            volApplyMapper.insert(newApply);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //2.5 判断用户是否有正在审核中
    @GetMapping("/IsReviewApply")
    public Result IsReviewApply(int user_id)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<VolApply> wrapper = new QueryWrapper<VolApply>();
            wrapper.eq("USER_ID",user_id);
            VolApply volApply=volApplyMapper.selectOne(wrapper);
            if(volApplyMapper.selectCount(wrapper)>0){ //已申请
                if (volApply.getIsreviewed().equals("N"))
                    result.data.put("ApplyHistory", 1);
                else
                {
                    if (volApply.getIspass().equals("N"))
                        result.data.put("ApplyHistory", 2);
                    else
                        result.data.put("ApplyHistory", 3);
                }
            }
            else
                result.data.put("ApplyHistory", 0);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }

    //4.3.1 获取志愿时长排在前10的志愿者信息
    @GetMapping("/ShowTenVolunteer")
    public Result ShowTenVolunteer()
    {
        try
        {
            Result result=new Result();
            List<VolunteerDTO> dtoList=volunteerMapper.selectTopTenVolDTO();
            result.data.put("AllVolActivity_list", dtoList);
            result.errorCode = 200;
            result.status = true;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }
}
