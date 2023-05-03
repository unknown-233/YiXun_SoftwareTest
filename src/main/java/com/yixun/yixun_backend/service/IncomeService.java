package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.IncomeDTO;
import com.yixun.yixun_backend.entity.Income;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;

import java.util.List;

/**
* @author dell
* @description 针对表【yixun_income】的数据库操作Service
* @createDate 2023-04-25 19:41:46
*/
public interface IncomeService extends IService<Income> {
    public Result GetAllIncome(int pageNum, int pageSize);
    public List<IncomeDTO> cutIntoIncomeDTOList(List<Income> list);
    public IncomeDTO cutIntoIncomeDTO(Income income);

}
