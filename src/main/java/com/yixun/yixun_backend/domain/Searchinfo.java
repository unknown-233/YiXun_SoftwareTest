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
 * @TableName yixun_searchinfo
 */
@TableName(value ="yixun_searchinfo")
@Data
public class Searchinfo implements Serializable {
    /**
     * 寻人信息编号
     */
    @TableId(type = IdType.AUTO)
    private Integer searchinfoId;

    /**
     * 寻人信息上传时间
     */
    private Date searchinfoDate;

    /**
     * 寻人信息状态
     */
    private String soughtPeopleState;

    /**
     * 被寻找人姓名
     */
    private String soughtPeopleName;

    /**
     * 被寻找人年龄
     */
    private Date soughtPeopleBirthday;

    /**
     * 被寻找人性别
     */
    private String soughtPeopleGender;

    /**
     * 被寻找人详情
     */
    private String soughtPeopleDetail;

    /**
     * 发布用户编号
     */
    private Integer userId;

    /**
     * 寻人类型
     */
    private String searchType;

    /**
     * 是否报案
     */
    private String isreport;

    /**
     * 地址编号
     */
    private Integer addressId;

    /**
     * 走失日期
     */
    private Date searchinfoLostdate;

    /**
     * 被寻找人身高
     */
    private String soughtPeopleHeight;

    /**
     * 被寻找人照片
     */
    private String searchinfoPhotoUrl;

    /**
     * 联系方式
     */
    private String contactMethod;

    /**
     * 记录是否存在
     */
    private String isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}