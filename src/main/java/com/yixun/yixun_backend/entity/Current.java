package com.yixun.yixun_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName yixun_current
 */
@TableName(value ="yixun_current")
@Data
public class Current implements Serializable  {
    /**
     * 现状编号
     */
    @TableId(type = IdType.AUTO)
    private Integer currentId;

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 总支出
     */
    private Double totalOutcome;

    /**
     * 总现金
     */
    private Double currentMoney;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
