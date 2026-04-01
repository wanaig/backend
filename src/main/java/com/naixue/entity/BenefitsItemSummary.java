package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 权益项实体类
 *
 * 非数据库表,用于返回具体权益项
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class BenefitsItemSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 权益项类型 */
    private Integer benefitsType;

    /** 数量 */
    private Integer num;

    /**
     * 单位类型
     * 0-次数
     * 1-金额
     * 2-其他
     */
    private Integer unitType;
}
