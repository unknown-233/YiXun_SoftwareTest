package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@TableName(value ="yixun_income")
@Data
public class Income implements Serializable {
    /**
     * 捐赠编号
     */
    @TableId(type = IdType.AUTO)
    private Integer incomeId;

    /**
     * 捐赠用户
     */
    private Integer userId;

    /**
     * 捐赠金额
     */
    private Double ammount;

    /**
     * 捐赠时间
     */
    private Date incomeTime;
    /**
     * 是否捐赠成功
     */
    private String ifSucceed;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
