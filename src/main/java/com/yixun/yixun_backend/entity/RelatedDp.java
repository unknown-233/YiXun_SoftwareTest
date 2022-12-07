package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName yixun_related_dp
 */
@TableName(value ="yixun_related_dp")
@Data
public class RelatedDp implements Serializable {
    /**
     * 部门编号
     */
    @TableId(type = IdType.AUTO)
    private Integer dpId;

    /**
     * 部门网站
     */
    private String website;

    /**
     * 管理员id
     */
    private Integer administratorId;

    /**
     * 部门图片URL
     */
    private String dpPicUrl;

    /**
     * 联系方式
     */
    private String contactMethod;


    /**
     * 部门名字
     */
    private String dpName;

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