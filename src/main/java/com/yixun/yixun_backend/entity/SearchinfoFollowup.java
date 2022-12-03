package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName yixun_searchinfo_followup
 */
@TableName(value ="yixun_searchinfo_followup")
@Data
public class SearchinfoFollowup implements Serializable {
    /**
     * 被跟进寻人信息编号
     */
    @TableId
    private Integer searchinfoId;

    /**
     * 用户编号
     */
    @TableId
    private Integer volId;

    /**
     * 跟进开始时间
     */
    private Date followtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}