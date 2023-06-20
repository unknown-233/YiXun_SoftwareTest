package com.yixun.yixun_backend.unittest;
import com.yixun.yixun_backend.entity.Address;
import com.yixun.yixun_backend.entity.Administrators;
import com.yixun.yixun_backend.entity.Volunteer;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.AddressMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private AddressMapper addressMapper;
    @Mock
    private WebUser mockUser = new WebUser();
    @Mock
    List<WebUser> tmpList = new ArrayList<>();
    @Mock
    Address address=new Address();
    @Mock
    private Volunteer volunteer=new Volunteer();
    @Mock
    private Administrators administrator = new Administrators();

    @Before
    public void setUp(){
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

    //UT_TC_002_002_001 均不填内容
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_1(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=Long.valueOf(0);
        String userPassword="";
        String userName="";
        String userEmail="";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_002 不填手机号、填密码、不填姓名、填非法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_2(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=Long.valueOf(0);
        String userPassword="gbsdh";
        String userName="";
        String userEmail="1@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_003 不填手机号、不填密码、填姓名、填合法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_3(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=Long.valueOf(0);
        String userPassword="";
        String userName="szq";
        String userEmail="1051172622@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_004 填非法手机号、不填密码、填姓名、填合法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_4(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=454215L;
        String userPassword="";
        String userName="szq";
        String userEmail="1051172622@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_005 填非法手机号、填密码、填姓名、不填邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_5(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=454215L;
        String userPassword="bgd";
        String userName="szq";
        String userEmail="";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_006 填非法手机号、填密码、不填姓名、填非法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_6(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=454215L;
        String userPassword="bgd";
        String userName="";
        String userEmail="1@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_007 填合法手机号、不填密码、填姓名、填非法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_7(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=18759073815L;
        String userPassword="";
        String userName="zqd";
        String userEmail="1@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_008 填合法手机号、填密码、不填姓名、填合法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_8(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=18759073815L;
        String userPassword="hjkh";
        String userName="";
        String userEmail="1051172622@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_009 填合法手机号、填密码、填姓名、不填邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_9(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=18759073815L;
        String userPassword="hjkh";
        String userName="zqd";
        String userEmail="";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_002_010 填合法手机号、填密码、填姓名、填合法邮箱
    //返回结果的status字段为true，data字段为新生成的user_id，用户信息添加成功
    @Test
    public void testAddWebUser_10(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=18759073815L;
        String userPassword="hjkh*15";
        String userName="zqd";
        String userEmail="1051172622@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

//        mockito模拟依赖项行为
        when(webUserMapper.selectOne(any())).thenReturn(null);
        when(webUserMapper.insert(any())).thenReturn(10);
        when(webUserMapper.selectList(any())).thenReturn(tmpList);
        when(tmpList.get(0)).thenReturn(mockUser);
        when(mockUser.getUserId()).thenReturn(10);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,true);
        Assert.assertEquals(result1.data.get("user_id"),10);
    }

    //UT_TC_002_002_011 填合法但数据库中已有的手机号、填密码、填姓名、填合法邮箱
    //返回结果的status字段为false
    @Test
    public void testAddWebUser_11(){
        Map<String, Object> inputData1 = new HashMap<>();
        // 构造测试数据
        Long userPhone=19121763605L;
        String userPassword="hjkh*15";
        String userName="zqd";
        String userEmail="1051172622@qq.com";

        inputData1.put("user_phone", userPhone);
        inputData1.put("user_password", userPassword);
        inputData1.put("user_name",userName);
        inputData1.put("user_email",userEmail);

//        mockito模拟依赖项行为
        when(webUserMapper.selectOne(any())).thenReturn(mockUser);

        // 调用被测试的函数
        Result result1 = webUserService.AddWebUser(inputData1);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

//UT_TC_002_003_001 输入user_id为空
//返回结果的status字段为false
    @Test
    public void testGetUserInformation_1(){
        // 构造测试数据
//        int user_id=;

//        mockito模拟依赖项行为
        when(webUserMapper.selectById(any())).thenReturn(null);

        // 调用被测试的函数
        Result result1 = webUserService.GetUserInfomation(0);

        // 验证结果
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_003_002 输入user_id非空，但在数据库中不存在
//返回结果的status字段为false
    @Test
    public void testGetUserInformation_2(){
        // 构造测试数据
        int user_id=-1;

//        mockito模拟依赖项行为
        when(webUserMapper.selectById(user_id)).thenReturn(null);

        // 调用被测试的函数
        Result result1 = webUserService.GetUserInfomation(user_id);

        // 验证结果
        verify(webUserMapper.selectById(user_id));
        Assert.assertEquals(result1.status,false);
    }

    //UT_TC_002_003_003 输入user_id非空，且在数据库中存在，地址id为空
//返回结果的status字段为true，data字段中返回用户的信息包括id和姓名邮箱等
    @Test
    public void testGetUserInformation_3(){
        // 构造测试数据
        int user_id=1;

//        mockito模拟依赖项行为
        when(webUserMapper.selectById(user_id)).thenReturn(mockUser);
        when(mockUser.getUserId()).thenReturn(1);
        when(mockUser.getUserName()).thenReturn("alian");
        when(mockUser.getUserPasswords()).thenReturn("abc");
        when(mockUser.getUserHeadUrl()).thenReturn("1111");
        when(mockUser.getAddressId()).thenReturn(null);

        // 调用被测试的函数
        Result result1 = webUserService.GetUserInfomation(user_id);

        // 验证结果
        verify(webUserMapper.selectById(user_id));
        Assert.assertEquals(result1.status,true);
        Assert.assertEquals(result1.data.get("user_address"),null);
    }

    //UT_TC_002_003_004 输入user_id非空，且在数据库中存在，地址id非空
//返回结果的status字段为true，data字段中返回用户的信息包括id和姓名邮箱等，地址相应信息非空
    @Test
    public void testGetUserInformation_4(){
        // 构造测试数据
        int user_id=1;

//        mockito模拟依赖项行为
        when(webUserMapper.selectById(user_id)).thenReturn(mockUser);
        when(mockUser.getUserId()).thenReturn(1);
        when(mockUser.getUserName()).thenReturn("alian");
        when(mockUser.getUserPasswords()).thenReturn("abc");
        when(mockUser.getUserHeadUrl()).thenReturn("1111");
        when(mockUser.getAddressId()).thenReturn(1);
        when(addressMapper.selectById(any())).thenReturn(address);
        when(address.getProvinceId()).thenReturn("省份");
        when(address.getCityId()).thenReturn("城市");
        when(address.getAreaId()).thenReturn("地区");
        when(address.getDetail()).thenReturn("细节");

        // 调用被测试的函数
        Result result1 = webUserService.GetUserInfomation(user_id);

        // 验证结果
        verify(webUserMapper.selectById(user_id));
        Assert.assertEquals(result1.status,true);
        Assert.assertEquals(result1.data.get("user_province"),"省份");
    }

    //UT_TC_002_003_005 输入user_id为0
//返回结果的status字段为false
    @Test
    public void testGetUserInformation_5(){
        // 构造测试数据
        int user_id=0;

//        mockito模拟依赖项行为
        when(webUserMapper.selectById(user_id)).thenReturn(null);

        // 调用被测试的函数
        Result result = webUserService.GetUserInfomation(user_id);

        // 验证结果
        Assert.assertEquals(result.status,false);
    }

}

