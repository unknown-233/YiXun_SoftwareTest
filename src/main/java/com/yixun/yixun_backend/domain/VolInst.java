package com.yixun.yixun_backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName yixun_vol_inst
 */
@TableName(value ="yixun_vol_inst")
@Data
public class VolInst implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer volInstId;

    /**
     * 志愿机构名
     */
    private String instName;

    /**
     * 密码
     */
    private String passwords;

    /**
     * 机构成立时间
     */
    private Date fundationTime;

    /**
     * 地址编号
     */
    private Integer addressId;

    /**
     * 机构标语
     */
    private String instSlogan;

    /**
     * 现有人数
     */
    private Integer peopleCount;

    /**
     * 联系方式
     */
    private String contactMethod;

    /**
     * 机构图片URL
     */
    private String instPicUrl;

    /**
     * 志愿机构证书URL
     */
    private String volInstCredUrl;

    /**
     * 志愿机构简介
     */
    private String volInstIntroduce;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}