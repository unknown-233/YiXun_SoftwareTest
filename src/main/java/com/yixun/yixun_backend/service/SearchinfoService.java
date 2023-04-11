package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo】的数据库操作Service
* @createDate 2022-12-03 12:43:39
*/
public interface SearchinfoService extends IService<Searchinfo> {
    SearchinfoDTO cutIntoSearchinfoDTO(Searchinfo searchinfo);
    List<SearchinfoDTO> cutIntoSearchinfoDTOList(List<Searchinfo> searchinfoList);
    public Result GetAllSearchInfo(int pageNum, int pageSize);
    public String GetSearchInfoNumber();
    public String GetFoundNumber();
    public Result SelectSearchInfoByConditions(@RequestBody Map<String,Object> inputData);
    public Result GetSearchInfoInPosition(String infoAd);
    public Result GetSearchInfoDetail(int search_id);
    public Result GetAllSearchInfoPublished(int user_id,int pageNum, int pageSize);
    public Result AddSearchInfo(@RequestBody Map<String, Object> inputMap);
    public Result AddSearchInfoPic(@RequestBody Map<String, Object> inputData);
    public Result DeleteInfoByUser(int userid, int infoid);
    public Result UpdateInfoByUser(@RequestBody Map<String, Object> inputMap);
}
