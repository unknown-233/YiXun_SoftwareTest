package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName yixun_address
 */
@TableName(value ="yixun_address")
@Data
public class Address implements Serializable {
    /**
     * 地址存储编号
     */
    @TableId(type = IdType.AUTO)
    private Integer addressId;

    /**
     * 行政区编号
     */
    private String areaId;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 行政市编号
     */
    private String cityId;

    /**
     * 行政省编号
     */
    private String provinceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}