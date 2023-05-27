package com.yixun.yixun_backend.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yixun.yixun_backend.entity.AlipayBean;
import com.yixun.yixun_backend.entity.Income;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.IncomeMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/Alipay")
public class AliPayController{
    @Resource
    private IncomeMapper incomeMapper;

    //获取配置文件中的配置信息
    //应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    @Value("${appId}")
    private String appId;

    //商户私钥 您的PKCS8格式RSA2私钥
    @Value("${privateKey}")
    private String privateKey;

    //应用公钥
    @Value("${publicKey}")
    private String publicKey;

    //支付宝公钥
    @Value("${alipayPublicKey}")
    private String alipayPublicKey;

    //服务器异步通知页面路径
    @Value("${notifyUrl}")
    private String notifyUrl;

    //页面跳转同步通知页面路径
    @Value("${returnUrl}")
    private String returnUrl;

    //签名方式
    @Value("${signType}")
    private String signType;

    //字符编码格式
    @Value("${charset}")
    private String charset;

    //支付宝网关
    @Value("${gatewayUrl}")
    private String gatewayUrl;

    private final String format = "json";

    @PostMapping("/createorder")
    public Result  CreateOrder(int user_id, Double total_amount){
        Income income=new Income();
        income.setIncomeTime(new Date());
        income.setAmmount(total_amount);
        income.setUserId(user_id);
        income.setIfSucceed("N");
        incomeMapper.insert(income);
        List<Income> tmpList = incomeMapper.selectList(new QueryWrapper<Income>().orderByDesc("INCOME_ID"));
        Income newAddedIncome= tmpList.get(0);
        Result result=new Result();
        result.data.put("outTradeNo",newAddedIncome.getIncomeId());
        result.errorCode = 200;
        result.status = true;
        return result;
    }

    //PC网页段支付，返回的是支付宝账号的登录页面
    @GetMapping("/pay")
    public String pay(AlipayBean alipayBean) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        //PC网页支付使用AlipayTradePagePayRequest传参，下面调用的是pageExecute方法
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));

        // 调用SDK生成表单
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println("请求支付宝付款返回参数为: " + result);
        return result;
    }

    /**
     * 交易状态,新版支付接口回调不返回trade_status
     * @param out_trade_no
     * @return
     */
    @GetMapping("/paycheck")
    public Result selectTradeStatus(String out_trade_no) throws AlipayApiException {
        Result result = new Result();
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, alipayPublicKey, signType);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            Income income=incomeMapper.selectById(out_trade_no);
            income.setIfSucceed("Y");
            incomeMapper.updateById(income);
            result.data.put("response", response);
            result.status = true;
            result.errorCode = 200;
            return result;
        } else {
            System.out.println("调用失败");
            System.out.println(response);
            result.data.put("response", response);
            result.status = true;
            result.errorCode = 200;
            return result;
        }
    }

    /**
     * 手机扫码支付
     * @param alipayBean
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay2")
    @ResponseBody
    public Result pay2(AlipayBean alipayBean) throws Exception {
        //接口模拟数据
//        alipayBean.setOut_trade_no("20210817010101003");
//        alipayBean.setSubject("订单名称");
//        alipayBean.setTotal_amount(String.valueOf(new Random().nextInt(100)));
//        alipayBean.setBody("商品描述");

        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, format, charset, publicKey, signType);
        //扫码支付使用AlipayTradePrecreateRequest传参，下面调用的是execute方法
        AlipayTradePrecreateRequest precreateRequest = new AlipayTradePrecreateRequest();
        precreateRequest.setReturnUrl(returnUrl);
        precreateRequest.setNotifyUrl(notifyUrl);
        precreateRequest.setBizContent(JSON.toJSONString(alipayBean));

        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(precreateRequest);
        } catch (AlipayApiException e) {
            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", e.getErrCode(), e.getErrMsg()));
        }

        /*
        {
        "code": "10000",
        "msg": "Success",
        "out_trade_no": "815259610498863104",
        "qr_code": "https://qr.alipay.com/bax09455sq1umiufbxf4503e"
        }
        */
        if (!response.isSuccess()) {
            throw new Exception(String.format("下单失败 错误代码:[%s], 错误信息:[%s]", response.getCode(), response.getMsg()));
        }
        // TODO 下单记录保存入库
        // 返回结果，主要是返回 qr_code，前端根据 qr_code 进行重定向或者生成二维码引导用户支付
        JSONObject jsonObject = new JSONObject();
        //支付宝响应的订单号
        String outTradeNo = response.getOutTradeNo();
        jsonObject.put("outTradeNo",outTradeNo);
        //二维码地址，页面使用二维码工具显示出来就可以了
        jsonObject.put("qrCode",response.getQrCode());
        return Result.error();
    }

    @GetMapping("/success")
    public String success(){
        return "交易成功！";
    }

    @GetMapping(value = "/index")
    public String payCoin(){
        return "index.html";
    }
}


