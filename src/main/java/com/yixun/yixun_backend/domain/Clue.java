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
 * @TableName yixun_clue
 */
@TableName(value ="yixun_clue")
@Data
public class Clue implements Serializable {
    /**
     * 线索编号
     */
    @TableId(type = IdType.AUTO)
    private Integer clueId;

    /**
     * 线索内容
     */
    private String clueContent;

    /**
     * 线索上传时间
     */
    private Date clueDate;

    /**
     * 对应寻人信息
     */
    private Integer searchinfoId;

    /**
     * 发布用户编号
     */
    private Integer userId;

    /**
     * 线索是否被删除
     */
    private String isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}