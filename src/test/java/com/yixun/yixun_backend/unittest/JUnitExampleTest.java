package com.yixun.yixun_backend.unittest;
import com.yixun.yixun_backend.entity.Administrators;
import com.yixun.yixun_backend.entity.Volunteer;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AdministratorsMapper;
import com.yixun.yixun_backend.mapper.VolunteerMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.impl.WebUserServiceImpl;
import com.yixun.yixun_backend.utils.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class JUnitExampleTest {
    @InjectMocks
    WebUserServiceImpl webUserService;
    @Mock
    private WebUserMapper webUserMapper;
    @Mock
    private AdministratorsMapper administratorsMapper;
    @Mock
    private VolunteerMapper volunteerMapper;
    @Mock
    private WebUser mockUser = new WebUser();
    @Mock
    private Volunteer volunteer=new Volunteer();
    @Mock
    private Administrators administrator = new Administrators();

    @Before
    public void testBeforeClass(){
        MockitoAnnotations.openMocks(this);
    }

//测试IfCorrectToLogIn方法
//UT_TC_002_001_001 输入参数任意为空的情况
// 返回结果的status字段为false
    @Test
    public void testIfCorrectToLogIn_1(){
        Long phone=105356215302L;;
        String password="";
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        inputData1.put("user_phone", "");
        inputData1.put("user_password", password);
        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);
        // 验证结果
        Assert.assertEquals(result1.status, false);

        // 构造测试数据
        phone=105356215302L;
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);
        // 调用被测试的函数
        result1 = webUserService.IfCorrectToLogIn(inputData1);
        // 验证结果
        Assert.assertEquals(result1.status, false);

        // 构造测试数据
        password="cdhbsk25";
        inputData1.put("user_phone", "");
        inputData1.put("user_password", password);
        // 调用被测试的函数
        result1 = webUserService.IfCorrectToLogIn(inputData1);
        // 验证结果
        Assert.assertEquals(result1.status, false);


    }

    //UT_TC_002_001_002 输入手机号在数据库中不存在
//返回结果的status字段为false，data字段中有信息提示“用户不正确”
    @Test
    public void testIfCorrectToLogIn_2(){
        Long phone=105356215302L;;
        String password="";
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        phone=4582034L;
        password="vfdvfdb";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);
        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("message"), "用户不正确或密码错误！");
    }


    //UT_TC_002_002_003 输入手机号与密码不匹配
//返回结果的status字段为false，data字段中有信息提示“密码错误”
    @Test
    public void testIfCorrectToLogIn_3(){
        Long phone=105356215302L;;
        String password="";
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        phone=19987655678L;
        password="654321";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);
        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("message"), "用户不正确或密码错误！");
    }

    //UT_TC_002_001_004 输入手机号属于用户，但对应用户在数据库中为封禁状态
//返回结果的status字段为false，data字段中有信息“账号已被注销或封禁”
    @Test
    public void testIfCorrectToLogIn_4(){
        Long phone=105356215302L;;
        String password="";
        Map<String, Object> inputData1 = new HashMap<>();

        // 构造测试数据
        phone=13507525520L;
        password="10000";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);

        // 使用Mockito模拟依赖项的行为
        when(webUserMapper.selectOne(any())).thenReturn(mockUser);
        when(mockUser.getIsactive()).thenReturn("N");

        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("message"), "账号已被注销或封禁");
    }

    //UT_TC_002_001_005 输入手机号属于用户，且与密码匹配，且用户user_id表明该用户同时是志愿者
//返回结果的status字段为true，data字段中保存志愿者的登录信息。
    @Test
    public void testIfCorrectToLogIn_5(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long phone=13507525520L;
        String password="10000";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);

        // 使用Mockito模拟依赖项的行为
        when(webUserMapper.selectOne(any())).thenReturn(mockUser);
        when(webUserMapper.update(any(),any())).thenReturn(1);
        when(volunteerMapper.selectOne(any())).thenReturn(volunteer);
        when(volunteer.getVolId()).thenReturn(1);
        when(volunteer.getVolUserId()).thenReturn(1);

        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("identity"), "volunteer");
        Assert.assertEquals(result1.data.get("vol_id"), 1);
        Assert.assertEquals(result1.data.get("user_id"), 1);
    }

    //UT_TC_002_001_006 输入手机号属于用户，且与密码匹配且用户user_id表明该用户并不是志愿者
//返回结果的status字段为true，data字段中保存用户登录信息。
    @Test
    public void testIfCorrectToLogIn_6(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long phone=19125589656L;
        String password="ljj123";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);

        // 使用Mockito模拟依赖项的行为
        when(webUserMapper.selectOne(any())).thenReturn(mockUser);
        when(webUserMapper.update(any(),any())).thenReturn(2);
        when(volunteerMapper.selectOne(any())).thenReturn(null);
        when(mockUser.getUserId()).thenReturn(2);

        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("identity"), "user");
        Assert.assertEquals(result1.data.get("id"), 2);
    }

    //UT_TC_002_001_007 输入手机号属于管理员，且手机号和密码匹配
    // 返回结果的status字段为true，data字段中保存管理员登录信息。
    @Test
    public void testIfCorrectToLogIn_7(){
        Long phone=105356215302L;;
        String password="";
        Map<String, Object> inputData1 = new HashMap<>();

        // 构造测试数据
        phone=13507525520L;
        password="10000";
        inputData1.put("user_phone", phone);
        inputData1.put("user_password", password);

        // 使用Mockito模拟依赖项的行为
        when(webUserMapper.selectOne(any())).thenReturn(null);
        when(administratorsMapper.selectOne(any())).thenReturn(administrator);
        when(administrator.getAdministratorId()).thenReturn(1);
        when(administrator.getAdministratorCode()).thenReturn("abc");

        // 调用被测试的函数
        Result result1 = webUserService.IfCorrectToLogIn(inputData1);

        // 验证结果
        Assert.assertEquals(result1.data.get("identity"), "administrator");
        Assert.assertEquals(result1.data.get("id"), 1);
    }




}

