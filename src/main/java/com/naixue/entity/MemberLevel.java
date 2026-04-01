package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 会员等级实体类
 *
 * 非数据库表,用于返回会员等级权益信息
 *
 * 会员等级权益包含:
 * - 等级信息: level, cardName, picture
 * - 权益列表: benefitsSummaries
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class MemberLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 等级 (1-6) */
    private Integer level;

    /** 等级名称 (V1-V6) */
    private String cardName;

    /** 等级图片URL */
    private String picture;

    /** 权益摘要列表 */
    private List<BenefitsSummary> benefitsSummaries;
}
