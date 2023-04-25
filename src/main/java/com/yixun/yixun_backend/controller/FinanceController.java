package com.yixun.yixun_backend.controller;
import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Finance")
public class FinanceController {

    @Resource
    private FundOutService fundOutService;

    @GetMapping("GetAllFundOut")
    public Result GetAllFundOut(int pageNum, int pageSize){
        Result result=new Result();
        result=fundOutService.GetAllFundOut(pageNum,pageSize);
        return result;
    }

}
