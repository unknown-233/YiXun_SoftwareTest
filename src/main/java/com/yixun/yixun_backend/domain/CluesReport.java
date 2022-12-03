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
 * @TableName yixun_clues_report
 */
@TableName(value ="yixun_clues_report")
@Data
public class CluesReport implements Serializable {
    /**
     * 线索举报编号
     */
    @TableId(type = IdType.AUTO)
    private Integer clueReportId;

    /**
     * 举报时间
     */
    private Date reportTime;

    /**
     * 举报内容
     */
    private String reportContent;

    /**
     * 线索编号
     */
    private Integer clueId;

    /**
     * 举报人编号
     */
    private Integer userId;

    /**
     * 是否被审核
     */
    private String isreviewed;

    /**
     * 审核管理员id
     */
    private Integer administratorId;

    /**
     * 审核是否通过
     */
    private String ispass;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}