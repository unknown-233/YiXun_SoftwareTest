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
    @Resource
    private IncomeService incomeService;

    @GetMapping("/GetAllFundOut")
    public Result GetAllFundOut(int pageNum, int pageSize){
        Result result=new Result();
        result=fundOutService.GetAllFundOut(pageNum,pageSize);
        return result;
    }
    @GetMapping("/GetAllIncome")
    public Result GetAllIncome(int pageNum, int pageSize){
        Result result=new Result();
        result=incomeService.GetAllIncome(pageNum,pageSize);
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
    @PostMapping("/GetFundOutByYear")
    public Result GetFundOutByYear(@RequestBody Map<String, Object> inputData){
        Result result=new Result();
        result=fundOutService.GetFundOutByYear(inputData);
        return result;
    }
    @GetMapping("/GetDonateCount")
    public double GetDonateCount(){
        double result=currentService.GetTotalIncome();
        return result;
    }
    @GetMapping("/GetDonateHead")
    public int GetDonateHead(){
        int result=incomeService.GetDonateHead();
        return result;
    }
}
