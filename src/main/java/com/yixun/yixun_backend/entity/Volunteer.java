package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import lombok.Data;

/**
 * 
 * @TableName yixun_volunteer
 */
@TableName(value ="yixun_volunteer")
@Data
public class Volunteer implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer volId;

    /**
     * 志愿时长
     */
    private Integer volTime;

    /**
     * 对应用户编号
     */
    private Integer volUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}