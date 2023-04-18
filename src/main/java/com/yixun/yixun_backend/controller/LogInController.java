package com.yixun.yixun_backend.controller;

import com.yixun.yixun_backend.service.WebUserService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/LogIn")
public class LogInController {
    @Resource
    private WebUserService webUserService;

    @PostMapping("/LogInCheck")
    public Result LogInCheck(@RequestBody Map<String,Object> inputData){
        Result result=new Result();
        //result=webUserService.IfCorrectToLogIn(inputData);
        result=webUserService.IfCorrectToLogIn(inputData);
        return result;
    }

    @PutMapping("/UpLoadInfo")
    public Result UpLoadInfo(@RequestBody Map<String,Object> inputData){
        Result result=new Result();
        result=webUserService.AddUserInfomation(inputData);
        return result;
    }
    @PostMapping("/Regist")
    public Result Regist(@RequestBody Map<String,Object> inputData){
        Result result=new Result();
        result=webUserService.AddWebUser(inputData);
        //result=webUserService.N_AddWebUser(inputData);
        return result;
    }

//    my code
    @PostMapping("/GetVerification")
    public String GetVerification(String email){
        String code=webUserService.SendEmailVerification(email);
        return code;
    }


}
