package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import lombok.Data;

/**
 * 
 * @TableName yixun_web_user
 */
@TableName(value ="yixun_web_user")
@Data
public class WebUser implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 密码
     */
    private String userPasswords;

    /**
     * 用户注册时间
     */
    private Date fundationTime;

    /**
     * 用户电话号码
     */
    private String phoneNum;

    /**
     * 电子邮箱号
     */
    private String mailboxNum;

    /**
     * 性别
     */
    private String userGender;

    /**
     * 用户状态
     */
    private String userState;

    /**
     * 地址编号
     */
    private Integer addressId;

    /**
     * 用户是否存在
     */
    private String isactive;

    /**
     * 用户头像URL
     */
    private String userHeadUrl;

    /**
     * 用户出生日期
     */
    private Date birthday;

    /**
     * 令牌
     */
    private String token;

    /**
     * 最近登录时间
     */
    private Date lastloginTime;

    /**
     * 最近登录ip地址
     */
    private String lastloginIp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}