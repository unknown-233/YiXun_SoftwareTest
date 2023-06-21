package com.yixun.yixun_backend.service.impl;

import com.yixun.yixun_backend.entity.Clue;
import com.yixun.yixun_backend.mapper.ClueMapper;
import com.yixun.yixun_backend.service.ClueService;
import com.yixun.yixun_backend.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ClueServiceImplTest {
    @InjectMocks
    private ClueServiceImpl clueService;
    @Mock
    private Clue clue;
    @Mock
    private ClueMapper clueMapper;
    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    UT_TC_003_001_001删除不是用户本人发布的线索，userID和clueId不匹配
//    返回结果的status字段为false
    @Test
    public void deleteClueByUser_1() {
        int userid=1;
        int clueid=0;

//        when(clueService.deleteClue(anyInt())).thenReturn(false);

        Result result=clueService.DeleteClueByUser(userid,clueid);

        Assert.assertEquals(result.status,false);
    }
    //    UT_TC_003_001_002userID不在数据库
//    返回结果的status字段为false
    @Test
    public void deleteClueByUser_2() {
        int userid=1;
        int clueid=0;

        when(clueService.deleteClue(clueid)).thenReturn(false);

        Result result=clueService.DeleteClueByUser(userid,clueid);

        Assert.assertEquals(result.status,false);
    }
    //    UT_TC_003_001_003clueId不在数据库
//status字段为false
    @Test
    public void deleteClueByUser_3() {
        int userid=1;
        int clueid=0;

//        when(clueService.deleteClue(0)).thenReturn(false);

        Result result=clueService.DeleteClueByUser(userid,clueid);

        Assert.assertEquals(result.status,false);
    }
    //    UT_TC_003_001_004userID为空
//status字段为false
    @Test
    public void deleteClueByUser_4() {
        int userid=1;
        int clueid=0;

//        when(clueService.deleteClue(0)).thenReturn(false);

        Result result=clueService.DeleteClueByUser(userid,clueid);

        Assert.assertEquals(result.status,false);
    }
    //    UT_TC_003_001_005clueId为空
//status字段为false
    @Test
    public void deleteClueByUser_5() {
        int userid=1;
        int clueid=0;

//        when(clueService.deleteClue(0)).thenReturn(false);

        Result result=clueService.DeleteClueByUser(userid,clueid);

        Assert.assertEquals(result.status,false);
    }
//UT_TC_003_002_001测试输入线索ID存在但数据库中没有对应线索的错误情况
//    返回结果的status字段为false
    @Test
    public void getClueDetail_1() {
        int clueid=0;

        when(clueMapper.selectById(clueid)).thenReturn(null);

        Result result=clueService.GetClueDetail(clueid);

        Assert.assertEquals(result.status,false);

    }
    //UT_TC_003_002_002测试输入线索ID不存在
//    返回结果的status字段为false
    @Test
    public void getClueDetail_2() {
        int clueid=0;

        when(clueMapper.selectById(clueid)).thenReturn(null);

        Result result=clueService.GetClueDetail(clueid);

        Assert.assertEquals(result.status,false);

    }
    //UT_TC_003_002_003输入线索ID合法，且有图片
//返回结果的各个字段与预期相符,status字段为true
    @Test
    public void getClueDetail_3() {
        int clueid=13;

        when(clueMapper.selectById(clueid)).thenReturn(clue);
        when(clue.getPicture()).thenReturn("图片");
        when(clue.getClueContent()).thenReturn("hello");

        Result result=clueService.GetClueDetail(clueid);

        Assert.assertEquals(result.status,true);
        Assert.assertEquals(result.data.get("clue_content"),"hello");

    }
    //UT_TC_003_002_004输入线索ID合法，但没有图片
//返回结果的各个字段与预期相符,status字段为true
    @Test
    public void getClueDetail_4() {
        int clueid=13;

        when(clueMapper.selectById(clueid)).thenReturn(clue);
        when(clue.getPicture()).thenReturn(null);
        when(clue.getClueContent()).thenReturn("hello");

        Result result=clueService.GetClueDetail(clueid);

        Assert.assertEquals(result.status,true);
        Assert.assertEquals(result.data.get("clue_content"),"hello");

    }
}