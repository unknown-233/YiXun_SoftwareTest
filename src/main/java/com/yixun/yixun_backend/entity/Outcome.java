package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
@TableName(value ="yixun_outcome")
@Data
public class Outcome implements Serializable {
    /**
     * 支出编号
     */
    @TableId(type = IdType.AUTO)
    private Integer outcomeId;

    /**
     * 支出管理员
     */
    private Integer administratorId;

    /**
     * 支出金额
     */
    private Integer ammount;

    /**
     * 资金使用
     */
    private String usage;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
