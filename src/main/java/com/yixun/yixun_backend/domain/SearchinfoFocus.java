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
 * @TableName yixun_searchinfo_focus
 */
@TableName(value ="yixun_searchinfo_focus")
@Data
public class SearchinfoFocus implements Serializable {
    /**
     * 被关注寻人信息编号
     */
    @TableId
    private Integer searchinfoId;

    /**
     * 用户编号
     */
    @TableId
    private Integer userId;

    /**
     * 关注时间
     */
    private Date focustime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}