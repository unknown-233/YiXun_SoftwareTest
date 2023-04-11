package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.entity.Administrators;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_administrators】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface AdministratorsService extends IService<Administrators> {
    public Result Addnews(@RequestBody Map<String, Object> inputData);
    public Result AddNewsCover(@RequestBody Map<String, Object> inputData);
    public Result AddVolActivity(@RequestBody Map<String, Object> inputData);
    public Result AddVolActivityPic(@RequestBody Map<String, Object> inputData);
    public Result GetAllNormalUsers(int pagenum, int pagesize);
    public Result UpdateUserToBan(int userid);
    public Result DeleteUser(int userid);
    public Result GetUserByName(String word, int pagenum, int pagesize);
    public Result GetAllVolunteers(int pagenum, int pagesize);
    public Result GetVolApplyNumber(int adminId);
    public Result GetInfoRepoNumber(int adminId);
    public Result GetClueRepoNumber(int adminId);
    public Result GetVolApplyReviewed(int adminId, int pagenum, int pagesize, String review);
    public Result UpdateVolApplyToPass(int volapplyid);
    public Result UpdateVolApplyToDeny(int volapplyid);
    public Result DeleteNews(int newsid);
    public Result GetReviewedInfoRepo(int adminId, int pagenum, int pagesize, String review);
    public Result GetReviewedClueRepo(int adminId, int pagenum, int pagesize, String review);
    public Result UpdateInfoRepoToPass(int infoid);
    public Result UpdateInfoRepoToDeny(int inforepoid);
    public Result UpdateClueRepToPass(int clueId);
    public Result UpdateClueRepoToDeny(int cluerepoid);
    public Result GetAllNews(int pagenum, int pagesize);
    public Result UpdateVolActivity(@RequestBody Map<String, Object> inputMap);
    public Result DeleteVolActivity(int actId);
}
