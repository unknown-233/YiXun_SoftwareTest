package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName yixun_vol_activity
 */
@TableName(value ="yixun_vol_activity")
@Data
public class VolActivity implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer volActId;

    /**
     * 活动预期时间
     */
    private Date expTime;

    /**
     * 地址编号
     */
    private Integer addressId;

    /**
     * 需要的人数
     */
    private Integer needpeople;

    /**
     * 活动图片URL
     */
    private String actPicUrl;

    /**
     * 联系方式
     */
    private String contactMethod;

    /**
     * 活动发布时间
     */
    private Date releaseTime;

    /**
     * 现已报名人数
     */
    private Integer signupPeople;

    /**
     * 志愿活动内容
     */
    private String actContent;

    /**
     * 志愿活动名
     */
    private String volActName;
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}