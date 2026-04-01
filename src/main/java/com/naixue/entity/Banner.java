package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 轮播图实体类
 *
 * 对应数据库表: banner
 *
 * 用于小程序首页、活动页等位置的轮播图展示
 *
 * position (位置):
 * - "home": 首页
 * - "product": 商品页
 * - "cart": 购物车页
 * - 等其他自定义位置
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Banner implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 轮播图ID (主键,自增) */
    private Long id;

    /** 标题 */
    private String title;

    /** 图片URL */
    private String image;

    /** 跳转链接 */
    private String link;

    /** 排序序号 */
    private Integer sort;

    /** 状态: 0-禁用 1-启用 */
    private Integer status;

    /**
     * 展示位置
     * 默认为"home",可自定义如"activity", "product"等
     */
    private String position;

    /** 创建时间 */
    private LocalDateTime createdAt;
}
