package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 礼品卡主题实体类
 *
 * 非数据库表,定义礼品卡的具体主题样式
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class GiftCardTheme implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 活动ID */
    private Long activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动代码 */
    private String activityCode;

    /** 主题图片列表 */
    private List<String> imageUrls;
}
