package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品实体类
 *
 * 对应数据库表: goods
 *
 * 商品信息包含:
 * - 基本信息: name, price, content, images
 * - 分类信息: categoryId (关联goods_category表)
 * - 规格属性: useProperty (是否有规格), properties (规格列表)
 * - 库存销售: stock (库存), sales (销量)
 * - 状态信息: status (上下架状态)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 商品ID (主键,自增) */
    private Long id;

    /** 分类ID (关联goods_category表的id) */
    private Long categoryId;

    /** 商品名称 */
    private String name;

    /** 商品价格 */
    private BigDecimal price;

    /** 商品描述/详情 */
    private String content;

    /** 商品图片URL (多张用逗号分隔) */
    private String images;

    /**
     * 是否有规格属性: true-有规格 false-无规格
     * 有规格的商品需要用户选择规格后购买
     */
    private Boolean useProperty;

    /** 库存数量 */
    private BigDecimal stock;

    /** 销量 */
    private Integer sales;

    /**
     * 商品状态: true-上架(可售) false-下架(不可售)
     */
    private Boolean status;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;

    /**
     * 商品规格属性列表 (非数据库字段,用于存储商品规格信息)
     * 规格示例: [{"name":"甜度","value":["正常糖","少糖","无糖"]},{"name":"温度","value":["去冰","少冰","正常冰"]}]
     */
    private List<GoodsProperty> properties;
}
