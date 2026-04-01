package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 权益摘要实体类
 *
 * 非数据库表,用于返回会员等级权益详情
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class BenefitsSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 权益名称 (如: "开卡特权", "生日礼包", "专属客服") */
    private String benefitsName;

    /** 权益类型 */
    private Integer benefitsType;

    /** 权益项列表 */
    private List<BenefitsItemSummary> benefitsItemSummaries;
}
