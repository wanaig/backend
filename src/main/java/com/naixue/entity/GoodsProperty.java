package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 商品规格属性实体类
 *
 * 非数据库表,用于表示商品的规格选项
 *
 * 示例JSON:
 * {
 *   "name": "甜度",
 *   "value": ["正常糖", "少糖", "无糖"]
 * }
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class GoodsProperty implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 规格名称
     * 例如: "甜度", "温度", "杯型", "配料"
     */
    private String name;

    /**
     * 规格可选值列表
     * 例如: ["正常糖", "少糖", "无糖"]
     */
    private String value;
}
