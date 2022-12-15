package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.VolActivityDTO;
import com.yixun.yixun_backend.entity.Recruited;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.mapper.VolActivityMapper;
import com.yixun.yixun_backend.service.RecruitedService;
import com.yixun.yixun_backend.mapper.RecruitedMapper;
import com.yixun.yixun_backend.service.VolActivityService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_recruited】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class RecruitedServiceImpl extends ServiceImpl<RecruitedMapper, Recruited>
    implements RecruitedService{
    @Resource
    private VolActivityMapper volActivityMapper;
    @Resource
    private VolActivityService volActivityService;
    @Resource
    private RecruitedMapper recruitedMapper;
    public Result GetVolActRecruited(int volid, int pagenum, int pagesize)
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
    public Result GetIfAppliedActivity(int vol_id,int volAct_id)
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
    public Result UpdateRecruitedState(int VolId, int VolActId)
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
}




