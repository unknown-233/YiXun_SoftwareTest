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
 * @TableName yixun_vol_apply
 */
@TableName(value ="yixun_vol_apply")
@Data
public class VolApply implements Serializable {
    /**
     * 申请信息编号
     */
    @TableId(type = IdType.AUTO)
    private Integer volApplyId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 是否被审核
     */
    private String isreviewed;

    /**
     * 申请者特长
     */
    private String specialty;

    /**
     * 申请背景
     */
    private String background;

    /**
     * 申请时间
     */
    private Date applicationTime;

    /**
     * 审核管理员id
     */
    private Integer administratorId;

    /**
     * 申请者职业
     */
    private String career;

    /**
     * 审核是否通过
     */
    private String ispass;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}