package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品分类实体类
 *
 * 对应数据库表: goods_category
 *
 * 用于商品的分类管理,一个分类包含多个商品
 * 示例分类: "奈雪早餐", "霸气鲜果茶", "宝藏鲜奶茶"等
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class GoodsCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 分类ID (主键,自增) */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 排序序号 (数字越小排序越靠前) */
    private Integer sort;

    /** 创建时间 */
    private LocalDateTime createdAt;
}
