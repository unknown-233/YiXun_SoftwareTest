package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.Clue;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.mapper.InfoReportMapper;
import com.yixun.yixun_backend.mapper.SearchinfoMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.service.SearchinfoService;
import com.yixun.yixun_backend.utils.Result;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Epic("益寻后端单元测试")
@Feature("寻人信息类")
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
    private InfoReportMapper infoReportMapper;
    @Mock
    private Searchinfo searchinfo;
    @Mock
    private Clue clue;
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

    @Story("UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
//    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_1() {
        int search_id=0;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);
    }
    @Story("UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_2() {
        int search_id=-3;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    @Story("UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TC_001_001_001 测试输入寻人信息ID不存在的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_1_3() {
        int search_id=3000;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    @Story("UT_TC_001_001_002 输入寻人信息ID为空的错误情况")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TC_001_001_002 输入寻人信息ID为空的错误情况
//    返回结果的status字段为false search_id=0、-3、3000
    @Test
    public void getSearchInfoDetail_2() {
        int search_id=0;

        when(searchinfoMapper.selectById(search_id)).thenReturn(null);

        Result result=searchinfoService.GetSearchInfoDetail(search_id);

        Assert.assertEquals(result.status,false);

    }
    @Story("UT_TC_001_001_003 输入寻人信息ID合法的情况,地址为空")
    @Description("返回结果的各个字段与预期相符,status字段为true")
    @Owner("zqr")
    @Severity(SeverityLevel.NORMAL)
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
    @Story("UT_TC_001_001_004 输入寻人信息ID合法，但线索返回空列表")
    @Description("返回结果的各个字段与预期相符,status字段为true")
    @Owner("zqr")
    @Severity(SeverityLevel.NORMAL)
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
@Story("UT_TD_001_002_001用户ID在数据库中不存在")
@Description("返回结果的status字段为false")
@Owner("zqr")
@Severity(SeverityLevel.CRITICAL)
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
    @Story("UT_TD_001_002_002用户ID合法，且发布过寻人信息")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_002_002用户ID合法，且发布过寻人信息
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_2() {
        int user_id=13;
        int pageNum=1;
        int pageSize=3;
        when(searchinfoMapper.selectPage(any(),any())).thenReturn(iPage);
        when(iPage.getRecords()).thenReturn(dtoList);
//        when(searchinfoService.cutIntoSearchinfoDTOList(anyList())).thenReturn(dtoList);
//        when(iPage.getTotal()).thenReturn(1L);
//        when(iPage.getRecords().size()).thenReturn(2);


        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);

        Assert.assertEquals(result.status,false);
//        Assert.assertEquals(result.data.get("total"),1);
    }
    @Story("UT_TD_001_002_003任意输入参数任意为空")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TD_001_002_003任意输入参数任意为空
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_3() {
        int user_id=0;
        int pageNum=1;
        int pageSize=3;
        when(searchinfoMapper.selectPage(any(),any())).thenReturn(iPage);
        when(iPage.getRecords()).thenReturn(dtoList);
//        when(searchinfoService.cutIntoSearchinfoDTOList(anyList())).thenReturn(dtoList);
//        when(iPage.getTotal()).thenReturn(1L);
//        when(iPage.getRecords().size()).thenReturn(2);


        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);

        Assert.assertEquals(result.status,false);
//        Assert.assertEquals(result.data.get("total"),1);
    }
    @Story("UT_TD_001_002_004输入页码非法")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TD_001_002_004输入页码非法
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_4() {
        int user_id=13;
        int pageNum=-1;
        int pageSize=3;
        when(searchinfoMapper.selectPage(any(),any())).thenReturn(iPage);
        when(iPage.getRecords()).thenReturn(dtoList);
//        when(searchinfoService.cutIntoSearchinfoDTOList(anyList())).thenReturn(dtoList);
//        when(iPage.getTotal()).thenReturn(1L);
//        when(iPage.getRecords().size()).thenReturn(2);


        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);

        Assert.assertEquals(result.status,false);
//        Assert.assertEquals(result.data.get("total"),1);
    }
    @Story("UT_TD_001_002_005输入页大小非法")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.MINOR)
    //    UT_TD_001_002_005输入页大小非法
//    返回结果的status字段为false
    @Test
    public void getAllSearchInfoPublished_5() {
        int user_id=13;
        int pageNum=1;
        int pageSize=-2;
        when(searchinfoMapper.selectPage(any(),any())).thenReturn(iPage);
        when(iPage.getRecords()).thenReturn(dtoList);
//        when(searchinfoService.cutIntoSearchinfoDTOList(anyList())).thenReturn(dtoList);
//        when(iPage.getTotal()).thenReturn(1L);
//        when(iPage.getRecords().size()).thenReturn(2);


        Result result=searchinfoService.GetAllSearchInfoPublished(user_id,pageNum,pageSize);

        Assert.assertEquals(result.status,false);
//        Assert.assertEquals(result.data.get("total"),1);
    }
    @Story("UT_TD_001_003_001删除不是用户本人发布的寻人信息，userID和infoID不匹配")
    @Description("返回结果的status字段为false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_003_001删除不是用户本人发布的寻人信息，userID和infoID不匹配
//返回false
    @Test
    public void deleteInfoByUser_1() {
        int userid=4;
        int infoid=6;

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        Assert.assertEquals(result.status,false);
    }
    @Story("UT_TD_001_002_002删除用户本人发布的寻人信息，userID和infoID合法且匹配")
    @Description("返回true")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
//    UT_TD_001_002_002删除用户本人发布的寻人信息，userID和infoID合法且匹配
//返回true
    @Test
    public void deleteInfoByUser_2() {
        int userid=4;
        int infoid=6;

        when(searchinfoMapper.selectById(anyInt())).thenReturn(searchinfo);
        when(searchinfoMapper.updateById(searchinfo)).thenReturn(1);
        when(infoReportMapper.selectList(any())).thenReturn(new ArrayList<>());
        when(infoReportMapper.updateById(any())).thenReturn(1);
        when(clueMapper.selectList(any())).thenReturn(new ArrayList<>());
        when(clue.getClueId()).thenReturn(1);
        when(clueService.deleteClue(anyInt())).thenReturn(true);

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        Assert.assertEquals(result.status,true);
    }
    @Story("UT_TD_001_002_003userID不在数据库")
    @Description("返回false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_002_003userID不在数据库
//返回false
    @Test
    public void deleteInfoByUser_3() {
        int userid=4;
        int infoid=6;

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        Assert.assertEquals(result.status,false);
    }
    @Story("UT_TD_001_002_004infoID不在数据库")
    @Description("返回false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_002_004infoID不在数据库
//返回false
    @Test
    public void deleteInfoByUser_4() {
        int userid=4;
        int infoid=6;

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        when(searchinfoMapper.selectById(anyInt())).thenReturn(null);

        Assert.assertEquals(result.status,false);
    }
    @Story("UT_TD_001_002_005userID为空")
    @Description("返回false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_002_005userID为空
//返回false
    @Test
    public void deleteInfoByUser_5() {
        int userid=0;
        int infoid=6;

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        when(searchinfoMapper.selectById(anyInt())).thenReturn(null);

        Assert.assertEquals(result.status,false);
    }
    @Story("UT_TD_001_002_006infoID为空")
    @Description("返回false")
    @Owner("zqr")
    @Severity(SeverityLevel.CRITICAL)
    //    UT_TD_001_002_006infoID为空
//返回false
    @Test
    public void deleteInfoByUser_6() {
        int userid=6;
        int infoid=0;

        Result result=searchinfoService.DeleteInfoByUser(userid,infoid);

        when(searchinfoMapper.selectById(anyInt())).thenReturn(null);

        Assert.assertEquals(result.status,false);
    }


}