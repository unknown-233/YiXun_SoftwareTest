package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.FundOutDTO;
import com.yixun.yixun_backend.dto.SearchinfoDTO;
import com.yixun.yixun_backend.entity.FundOut;
import com.yixun.yixun_backend.entity.Recruited;
import com.yixun.yixun_backend.entity.Searchinfo;
import com.yixun.yixun_backend.entity.VolActivity;
import com.yixun.yixun_backend.service.FundOutService;
import com.yixun.yixun_backend.mapper.FundOutMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @author dell
* @description 针对表【yixun_outcome】的数据库操作Service实现
* @createDate 2023-04-25 19:51:46
*/
@Service
public class FundOutServiceImpl extends ServiceImpl<FundOutMapper, FundOut>
    implements FundOutService {
    @Resource
    private FundOutMapper fundOutMapper;


    public FundOutDTO cutIntoFundOutDTO(FundOut fundOut){
        FundOutDTO dto=new FundOutDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setFund_out_id(fundOut.getFundOutId());
        dto.setFund_out_amount(fundOut.getAmmount());
        dto.setFund_out_usage(fundOut.getUsage());
        dto.setFund_out_time(TimeTrans.myToString(fundOut.getFundOutTime()));
        return dto;
    }
    public List<FundOutDTO> cutIntoFundOutList(List<FundOut> list){
        List<FundOutDTO> dtoList=new ArrayList<>();
        for(FundOut fundOut : list){
            FundOutDTO dto= cutIntoFundOutDTO(fundOut);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Result GetAllFundOut(int pageNum, int pageSize) {
        try {
            Result result = new Result();
            Page<FundOut> page = new Page<FundOut>(pageNum, pageSize);
            QueryWrapper<FundOut> wrapper = new QueryWrapper<FundOut>();
            IPage iPage = fundOutMapper.selectPage(page, wrapper);
            List<FundOutDTO> dtoList = cutIntoFundOutList((List<FundOut>) iPage.getRecords());
            result.data.put("fund_out", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    
    public Result AddFundOut(@RequestBody Map<String, Object> inputData){
        try {
            Result result = new Result();
            int ammount = (int)inputData.get("fund_out_amount");
            int administrator_id = (int)inputData.get("administrator_id");
            String usage = (String)inputData.get("fund_out_usage");
            String time = (String) inputData.get("fund_out_time");
            FundOut fundOut = new FundOut();
            fundOut.setFundOutTime(TimeTrans.myToDate_1(time));
            fundOut.setUsage(usage);
            fundOut.setAdministratorId(administrator_id);
            fundOut.setAmmount(ammount);
            fundOutMapper.insert(fundOut);

            result.errorCode = 200;
            result.status = true;
            return result;
        } catch (Exception e) {
            return Result.error();
        }
    }
    public Result DeleteFundOut(int fundOutId)
    {
        try{
            Result result = new Result();

            LambdaQueryWrapper<FundOut> wrapper2 = new QueryWrapper<FundOut>().lambda().eq(FundOut::getFundOutId, fundOutId);
            fundOutMapper.delete(wrapper2);

            result.status = true;
            result.errorCode = 200;
            return result;
        }
        catch (Exception e) {
            return Result.error();
        }
    }



}




