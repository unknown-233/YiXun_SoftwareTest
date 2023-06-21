package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchinfoServiceImplTest {

    @InjectMocks
    SearchinfoServiceImpl searchinfoService;
    @Mock
    private ClueService clueService;
    @Mock
    private ClueMapper clueMapper;
    @Mock
    private SearchinfoMapper searchinfoMapper;
    @Mock
    private Searchinfo searchinfo;
    @Mock
    private IPage iPage;
    @Mock
    private List<SearchinfoDTO> dtoList=new ArrayList<>();
    @Mock
    private WebUserMapper webUserMapper;
    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_1() {
        int search_id=0;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);
    }
    //    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_2() {
        int search_id=-3;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    //    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_3() {
        int search_id=3000;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    //    UT_TC_001_001_002 输入寻人信息ID为空的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_2() {
        int search_id=0;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    //    UT_TC_001_001_003 输入寻人信息ID合法的情况,地址为空
//    返回结果的各个字段与预期相符,status字段为true
    @Test
    public void getSearchInfoDetail_3() {
        int search_id=1;

        when(searchinfoMapper.selectById(search_id)).thenReturn(searchinfo);
        when(searchinfo.getSoughtPeopleName()).thenReturn("Mary");
        when(searchinfo.getSoughtPeopleBirthday()).thenReturn(new Date());
        when(searchinfo.getSoughtPeopleGender()).thenReturn("女");
        when(searchinfo.getSoughtPeopleDetail()).thenReturn("");
        when(searchinfo.getSearchinfoPhotoUrl()).thenReturn("");
        when(clueMapper.selectList(any())).thenReturn(new ArrayList<>());
        when(clueService.cutIntoClueDTOList(any())).thenReturn(new ArrayList<>());
        when(webUserMapper.selectVolDTOByInfoID(1)).thenReturn(new ArrayList<>());
        when(searchinfo.getWhetherFound()).thenReturn("已找到");
        when(searchinfo.getAddressId()).thenReturn(null);


        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,true);
        Assert.assertEquals(result.data.get("search_name"),"Mary");

    }
    //    UT_TC_001_001_004 输入寻人信息ID合法，但线索返回空列表
//    返回结果的各个字段与预期相符,status字段为true
    @Test
    public void getSearchInfoDetail_4() {
        int search_id=1;
        when(searchinfoMapper.selectById(search_id)).thenReturn(searchinfo);
        when(searchinfo.getSoughtPeopleName()).thenReturn("Mary");
        when(searchinfo.getSoughtPeopleBirthday()).thenReturn(new Date());
        when(searchinfo.getSoughtPeopleGender()).thenReturn("女");
        when(searchinfo.getSoughtPeopleDetail()).thenReturn("");
        when(searchinfo.getSearchinfoPhotoUrl()).thenReturn("");
        when(clueMapper.selectList(any())).thenReturn(null);
        when(clueService.cutIntoClueDTOList(any())).thenReturn(null);
        when(webUserMapper.selectVolDTOByInfoID(1)).thenReturn(new ArrayList<>());
        when(searchinfo.getWhetherFound()).thenReturn("已找到");
        when(searchinfo.getAddressId()).thenReturn(null);


        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,true);
        Assert.assertEquals(result.data.get("search_name"),"Mary");

    }
//    UT_TD_001_002_001用户ID在数据库中不存在
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_1() {
        int user_id=999;
        int pageNum=1;
        int pageSize=3;

        when(searchinfoMapper.selectPage(any(),any())).thenReturn(null);

        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);
        Assert.assertEquals(result.status,false);
    }
    //    UT_TD_001_002_002用户ID合法，且发布过寻人信息
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_2() {
        int user_id=13;
        int pageNum=1;
        int pageSize=3;
//      (List<Searchinfo>)
        when(searchinfoMapper.selectPage(any(),any())).thenReturn(iPage);
        when(iPage.getRecords()).thenReturn(dtoList);
        when(searchinfoService.cutIntoSearchinfoDTOList(any())).thenReturn(dtoList);
        when(iPage.getTotal()).thenReturn(1L);
        when(iPage.getRecords().size()).thenReturn(2);

        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);

        Assert.assertEquals(result.status,true);
        Assert.assertEquals(result.data.get("total"),1);
    }

    @Test
    public void addSearchInfo() {
    }

    @Test
    public void deleteInfoByUser() {
    }
}