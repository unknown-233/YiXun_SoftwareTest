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
 * @TableName yixun_recruited
 */
@TableName(value ="yixun_recruited")
@Data
public class Recruited implements Serializable {
    /**
     * 志愿活动编号
     */
    @TableId
    private Integer volActId;

    /**
     * 志愿者编号
     */
    @TableId
    private Integer volId;

    /**
     * 报名志愿活动时间
     */
    private Date recruittime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}