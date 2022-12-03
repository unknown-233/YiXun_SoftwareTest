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
 * @TableName yixun_news
 */
@TableName(value ="yixun_news")
@Data
public class News implements Serializable {
    /**
     * 资讯编号
     */
    @TableId(type = IdType.AUTO)
    private Integer newsId;

    /**
     * 资讯内容
     */
    private String newsContent;

    /**
     * 发布时间
     */
    private Date newsTime;

    /**
     * 管理员编号
     */
    private Integer administratorId;

    /**
     * 资讯类型
     */
    private String newsType;

    /**
     * 资讯封面URL
     */
    private String newsTitlepagesUrl;

    /**
     * 资讯标题
     */
    private String newsHeadlines;

    /**
     * 资讯是否存在
     */
    private String isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}