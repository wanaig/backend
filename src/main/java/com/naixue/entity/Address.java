package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收货地址实体类
 *
 * 对应数据库表: address
 *
 * 收货地址信息包含:
 * - 收货人信息: acceptName, mobile, sex
 * - 区域信息: province, city, area (行政区划代码及名称)
 * - 详细地址: street, doorNumber
 * - 其他信息: isDefault (是否默认), poiname (POI名称)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 地址ID (主键,自增) */
    private Long id;

    /** 会员ID (所属用户) */
    private Long memberId;

    /** 收货人姓名 */
    private String acceptName;

    /** 收货人手机号 */
    private String mobile;

    /** 收货人性别: 0-女 1-男 */
    private Integer sex;

    /** 省份编码 (如: 440000表示广东省) */
    private Integer province;

    /** 城市编码 (如: 440300表示深圳市) */
    private Integer city;

    /** 区县编码 (如: 440306表示宝安区) */
    private Integer area;

    /** 省份名称 */
    private String provinceName;

    /** 城市名称 */
    private String cityName;

    /** 区县名称 */
    private String areaName;

    /** 街道/详细地址 */
    private String street;

    /** 门牌号 */
    private String doorNumber;

    /** 是否默认地址: 0-否 1-是 */
    private Integer isDefault;

    /** POI名称 (地图兴趣点) */
    private String poiname;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 删除标记: 0-未删除 1-已删除 */
    private Integer deleted;

    // ========== 关联数据 (非数据库字段) ==========

    /** 区域信息 (省市区组合) */
    private District district;
}
