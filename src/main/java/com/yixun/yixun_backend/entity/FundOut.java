package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@TableName(value ="yixun_fund_out")
@Data
public class FundOut implements Serializable {
    /**
     * 支出编号
     */
    @TableId(type = IdType.AUTO)
    private Integer fundOutId;

    /**
     * 支出管理员
     */
    private Integer administratorId;

    /**
     * 支出金额
     */
    private Double ammount;

    /**
     * 资金使用
     */
    private String fundOutUsage;

    /**
     * 支出时间
     */
    private Date fundOutTime;

    /**
     * 详细描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
