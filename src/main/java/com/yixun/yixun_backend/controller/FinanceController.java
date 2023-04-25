package com.yixun.yixun_backend.controller;
import com.yixun.yixun_backend.service.*;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Finance")
public class FinanceController {

    @Resource
    private FundOutService fundOutService;
    @Resource
    private CurrentService currentService;

    @GetMapping("/GetAllFundOut")
    public Result GetAllFundOut(int pageNum, int pageSize){
        Result result=new Result();
        result=fundOutService.GetAllFundOut(pageNum,pageSize);
        return result;
    }
    @PostMapping("/AddFundOut")
    public Result AddFundOut(@RequestBody Map<String, Object> inputData)
    {
        Result result=new Result();
        result=fundOutService.AddFundOut(inputData);
        return result;
    }

    @GetMapping("/GetTotalFinance")
    public Result GetTotalFinance(){
        Result result=new Result();
        result=currentService.GetTotalFinance();
        return result;
    }
    @DeleteMapping("/DeleteFundOut")
    public Result DeleteFundOut(int fundOutId)
    {
        Result result=new Result();
        result=fundOutService.DeleteFundOut(fundOutId);
        return result;
    }

}
