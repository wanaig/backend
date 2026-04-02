package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员信息响应VO
 *
 * 包含会员的完整信息,用于个人中心等页面展示
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class MemberInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    private Long customerId;

    /** 昵称 */
    private String nickname;

    /** 头像URL */
    private String avatar;

    /** 手机号 */
    private String mobilePhone;

    /** 性别: 0-女 1-男 */
    private Integer gender;

    /** 会员卡号 */
    private String cardNo;

    /** 等级名称 (如: "V1", "V2") */
    private String cardName;

    /** 会员等级 (1-6) */
    private Integer memberLevel;

    /** 等级名称 (如: "VIP1", "VIP2") */
    private String memberLevelName;

    /** 积分数量 */
    private Integer pointNum;

    /** 优惠券数量 */
    private Integer couponNum;

    /** 余额 */
    private BigDecimal balance;

    /** 礼品卡余额 */
    private BigDecimal giftBalance;

    /** 当前成长值 */
    private Integer currentValue;

    /** 距离下一级所需成长值 */
    private Integer needValue;

    /** 生日 (格式: yyyy-MM-dd) */
    private String birthday;

    /** 会员来源 */
    private String memberOrigin;

    /** 累计消费金额 */
    private BigDecimal expenseAmount;

    /** 会员等级 (1-6) */
    private Integer level;

    /** 创建时间 (时间戳秒) */
    private Long createdAt;
}
