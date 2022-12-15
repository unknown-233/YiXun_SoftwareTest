package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.entity.SearchinfoFocus;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.service.SearchinfoFocusService;
import com.yixun.yixun_backend.mapper.SearchinfoFocusMapper;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @author hunyingzhong
* @description 针对表【yixun_searchinfo_focus】的数据库操作Service实现
* @createDate 2022-12-03 12:43:39
*/
@Service
public class SearchinfoFocusServiceImpl extends ServiceImpl<SearchinfoFocusMapper, SearchinfoFocus>
    implements SearchinfoFocusService{
    @Resource
    private SearchinfoMapper searchinfoMapper;
    @Resource
    private SearchinfoService searchinfoService;
    @Resource
    private SearchinfoFocusMapper searchinfoFocusMapper;
    public Result GetFocusInfo(int userid, int pagenum, int pagesize)
    {
        try
        {
            Result result = new Result();
            Page<Searchinfo> page = new Page<>(pagenum, pagesize);
            QueryWrapper<Searchinfo> wrapper = new QueryWrapper<Searchinfo>();
            wrapper.inSql("SEARCHINFO_ID","select SEARCHINFO_ID from yixun_searchinfo_focus where USER_ID ="+userid);
            IPage iPage = searchinfoMapper.selectPage(page,wrapper);
            List<SearchinfoDTO> dtoList=searchinfoService.cutIntoSearchinfoDTOList((List<Searchinfo>)iPage.getRecords());
            result.data.put("follow_info", dtoList);
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
    public Result GetUserFocus(int userid, int infoid)
    {
        try
        {
            Result result = new Result();
            QueryWrapper<SearchinfoFocus> wrapper = new QueryWrapper<SearchinfoFocus>();
            wrapper.eq("USER_ID",userid).eq("SEARCHINFO_ID",infoid);
            int ifApply=searchinfoFocusMapper.selectCount(wrapper);
            if(ifApply==1) //已关注
            {
                searchinfoFocusMapper.delete(wrapper);//从searchinfoFocus表中删去这条数据
                result.data.put("state", false);
            }
            else
            {
                SearchinfoFocus focus = new SearchinfoFocus();
                focus.setSearchinfoId(infoid);
                focus.setUserId(userid);
                focus.setFocustime(new Date());
                searchinfoFocusMapper.insert(focus);
                result.data.put("state", true);
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




