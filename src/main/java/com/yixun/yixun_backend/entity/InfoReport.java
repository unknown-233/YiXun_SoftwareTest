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
 * @TableName yixun_info_report
 */
@TableName(value ="yixun_info_report")
@Data
public class InfoReport implements Serializable {
    /**
     * 寻人信息举报编号
     */
    @TableId(type = IdType.AUTO)
    private Integer infoReportId;

    /**
     * 举报时间
     */
    private Date reportTime;

    /**
     * 举报内容
     */
    private String reportContent;

    /**
     * 寻人信息编号
     */
    private Integer searchinfoId;

    /**
     * 举报人编号
     */
    private Integer userId;

    /**
     * 审核管理员id
     */
    private Integer administratorId;

    /**
     * 是否被审核过
     */
    private String isreviewed;

    /**
     * 审核是否通过
     */
    private String ispass;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}