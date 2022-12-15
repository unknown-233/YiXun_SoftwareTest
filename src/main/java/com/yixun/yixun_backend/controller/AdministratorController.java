package com.yixun.yixun_backend.controller;


import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/api/Administrator")
@RestController
public class AdministratorController {
    @Resource
    private AdministratorsService administratorsService;

    @PostMapping("/ReleaseNews")
    public Result ReleaseNews(@RequestBody Map<String, Object> inputData) {
        Result result=new Result();
        result=administratorsService.Addnews(inputData);
        return result;

    }

    @PutMapping("/AddNewsCover")
    public Result AddNewsCover(@RequestBody Map<String, Object> inputData) {
        Result result=new Result();
        result=administratorsService.AddNewsCover(inputData);
        return result;
    }

    @PostMapping("/ReleaseVolActivity")
    public Result ReleaseVolActivity(@RequestBody Map<String, Object> inputData) {
        Result result=new Result();
        result=administratorsService.AddVolActivity(inputData);
        return result;
    }

    @PutMapping("/AddVolActivityPic")
    public Result AddVolActivityPic(@RequestBody Map<String, Object> inputData) {
        Result result=new Result();
        result=administratorsService.AddVolActivityPic(inputData);
        return result;
    }

    //1.4 用户管理
    @GetMapping("/GetAllNorUser")
    public Result GetAllNorUser(int pagenum, int pagesize) {
        Result result=new Result();
        result=administratorsService.GetAllNormalUsers(pagenum,pagesize);
        return result;
    }

    //1.4.1 封禁用户
    @PutMapping("/BanUser")
    public Result BanUser(int userid) {
        Result result=new Result();
        result= administratorsService.UpdateUserToBan(userid);
        return result;
    }

    //1.4.2 删除用户
    @PutMapping("/DeleteUser")
    public Result DeleteUser(int userid) {
        Result result=new Result();
        result= administratorsService.DeleteUser(userid);
        return result;
    }

    //1.4.3 搜索用户名 *
    @GetMapping("/GetUserByName")
    public Result GetUserByName(String word, int pagenum, int pagesize) {
        Result result=new Result();
        result= administratorsService.GetUserByName(word,pagenum,pagesize);
        return result;
    }

    //1.5 志愿者管理
    @GetMapping("/GetAllVol")
    public Result GetAllVol(int pagenum, int pagesize) {
        Result result=new Result();
        result= administratorsService.GetAllVolunteers(pagenum,pagesize);
        return result;
    }

    //1.7.1 获得申请审核数量 *
    @GetMapping("/GetVolApplyCount")
    public Result GetVolApplyCount(int adminId) {
        Result result=new Result();
        result= administratorsService.GetVolApplyNumber(adminId);
        return result;
    }

    @GetMapping("/GetInfoRepoCount")
    public Result GetInfoRepoCount(int adminId) {
        Result result=new Result();
        result= administratorsService.GetInfoRepoNumber(adminId);
        return result;
    }

    @GetMapping("/GetClueRepoCount")
    public Result GetClueRepoCount(int adminId) {
        Result result=new Result();
        result= administratorsService.GetClueRepoNumber(adminId);
        return result;
    }

    @GetMapping("/GetVolApplyReviewed")
    public Result GetVolApplyReviewed(int adminId, int pagenum, int pagesize, String review) {
        Result result=new Result();
        result= administratorsService.GetVolApplyReviewed(adminId,pagenum,pagesize,review);
        return result;
    }

    @PutMapping("/PassVolApply")
    public Result PassVolApply(int volapplyid) {
        Result result=new Result();
        result= administratorsService.UpdateVolApplyToPass(volapplyid);
        return result;
    }

    @PutMapping("/DenyVolApply")
    public Result DenyVolApply(int volapplyid) {
        Result result=new Result();
        result= administratorsService.UpdateVolApplyToDeny(volapplyid);
        return result;
    }

    @DeleteMapping("DeleteNews")
    public Result DeleteNews(int newsid) {
        Result result=new Result();
        result= administratorsService.DeleteNews(newsid);
        return result;
    }

    @GetMapping("/GetInfoRepoReviewed")
    public Result GetInfoRepoReviewed(int adminId, int pagenum, int pagesize, String review) {
        Result result=new Result();
        result= administratorsService.GetReviewedInfoRepo(adminId,pagenum,pagesize,review);
        return result;
    }

    @GetMapping("/GetClueRepoReviewed")
    public Result GetClueRepoReviewed(int adminId, int pagenum, int pagesize, String review) {
        Result result=new Result();
        result= administratorsService.GetReviewedClueRepo(adminId,pagenum,pagesize,review);
        return result;
    }

    @DeleteMapping("/PassInfoRepo")
    public Result PassInfoRepo(int infoid) {
        Result result=new Result();
        result= administratorsService.UpdateInfoRepoToPass(infoid);
        return result;
    }

    @PostMapping("/DenyInfoRepo")
    public Result DenyInfoRepo(int inforepoid) {
        Result result=new Result();
        result= administratorsService.UpdateInfoRepoToDeny(inforepoid);
        return result;
    }

    //1.8.7 通过举报
    @PutMapping("/PassClueRepo")
    public Result PassClueRepo(int clueId) {
        Result result=new Result();
        result= administratorsService.UpdateClueRepToPass(clueId);
        return result;
    }

    //1.8.8 拒绝举报
    @PutMapping("/DenyClueRepo")
    public Result DenyClueRepo(int cluerepoid) {
        Result result=new Result();
        result= administratorsService.UpdateClueRepoToDeny(cluerepoid);
        return result;
    }

    @GetMapping("/GetAllNews")
    public Result GetAllNews(int pagenum, int pagesize) {
        Result result=new Result();
        result= administratorsService.GetAllNews(pagenum,pagesize);
        return result;
    }

}
