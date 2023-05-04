package com.yixun.yixun_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yixun.yixun_backend.dto.FundOutDTO;
import com.yixun.yixun_backend.dto.IncomeDTO;
import com.yixun.yixun_backend.entity.FundOut;
import com.yixun.yixun_backend.entity.Income;
import com.yixun.yixun_backend.entity.WebUser;
import com.yixun.yixun_backend.mapper.FundOutMapper;
import com.yixun.yixun_backend.mapper.WebUserMapper;
import com.yixun.yixun_backend.service.IncomeService;
import com.yixun.yixun_backend.mapper.IncomeMapper;
import com.yixun.yixun_backend.utils.Result;
import com.yixun.yixun_backend.utils.TimeTrans;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
* @author dell
* @description 针对表【yixun_income】的数据库操作Service实现
* @createDate 2023-04-25 19:41:46
*/
@Service
public class IncomeServiceImpl extends ServiceImpl<IncomeMapper, Income>
    implements IncomeService{
    @Resource
    private IncomeMapper incomeMapper;
    @Resource
    private WebUserMapper webUserMapper;

    public IncomeDTO cutIntoIncomeDTO(Income income){
        IncomeDTO dto=new IncomeDTO();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dto.setAmount(income.getAmmount());
        WebUser user=webUserMapper.selectById(income.getUserId());
        dto.setPhoneNum(Long.toString(user.getPhoneNum()));
        dto.setUserHeadUrl(user.getUserHeadUrl());
        dto.setTime(TimeTrans.myToDateString(income.getIncomeTime()));
        return dto;
    }
    public List<IncomeDTO> cutIntoIncomeDTOList(List<Income> list){
        List<IncomeDTO> dtoList=new ArrayList<>();
        for(Income income : list){
            IncomeDTO dto= cutIntoIncomeDTO(income);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public Result GetAllIncome(int pageNum, int pageSize) {
        try {
            Result result = new Result();
            Page<Income> page = new Page<Income>(pageNum, pageSize);
            QueryWrapper<Income> wrapper = new QueryWrapper<Income>();
            IPage iPage = incomeMapper.selectPage(page, wrapper);
            List<IncomeDTO> dtoList = cutIntoIncomeDTOList((List<Income>) iPage.getRecords());
            result.data.put("income", dtoList);
            result.data.put("total", iPage.getTotal());
            result.data.put("getcount", iPage.getRecords().size());
            result.status = true;
            result.errorCode = 200;
            return result;
        } catch (Exception e) {
            Result result = new Result();
            result.data.put("error",e.getMessage());
            return result;
        }
    }
    public int GetDonateHead(){
        QueryWrapper<Income> wrapper = new QueryWrapper<Income>();
        int num=incomeMapper.selectCount(wrapper);
        return num;
    }
}




