package com.yixun.yixun_backend.service;

import com.yixun.yixun_backend.dto.FundOutDTO;
import com.yixun.yixun_backend.entity.FundOut;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yixun.yixun_backend.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
* @author dell
* @description 针对表【yixun_outcome】的数据库操作Service
* @createDate 2023-04-25 19:51:46
*/
public interface FundOutService extends IService<FundOut> {
    public FundOutDTO cutIntoFundOutDTO(FundOut fundOut);
    public List<FundOutDTO> cutIntoFundOutList(List<FundOut> list);
    public Result GetAllFundOut(int pageNum, int pageSize);
    public Result AddFundOut(@RequestBody Map<String, Object> inputData);
    public Result DeleteFundOut(int fundOutId);


}
