package com.naixue.vo;

import com.naixue.entity.Goods;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分类VO
 *
 * 包含分类信息及该分类下的商品列表
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long id;

    /** 分类名称 */
    private String name;

    /** 排序序号 */
    private Integer sort;

    /** 分类图标 */
    private String icon;

    /** 商品列表 */
    private List<Goods> goodsList;
}