package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 商品响应VO
 *
 * 用于返回商品列表信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class GoodsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 排序序号 */
    private Integer sort;

    /** 分类图标 */
    private String icon;

    /** 商品ID */
    private Long id;

    /** 商品名称 */
    private String name;

    /** 商品价格 */
    private Object price;

    /** 商品描述 */
    private String content;

    /** 商品图片 */
    private String images;

    /** 是否有规格属性 */
    private Integer useProperty;

    /** 规格属性列表 */
    private List<Object> property;

    /** 库存 */
    private String stock;

    /** 销量 */
    private Integer sales;
}
