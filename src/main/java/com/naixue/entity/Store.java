package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 门店实体类
 *
 * 对应数据库表: store
 *
 * 门店信息包含:
 * - 基本信息: name, address, mobile, tel
 * - 位置信息: longitude (经度), latitude (纬度), areaName (区域)
 * - 营业状态: isOpen (是否营业), isTakeout (是否支持外卖), forhereIsOpen (是否支持堂食)
 * - 费用信息: minPrice (起送价), packingFee (包装费), deliveryCost (配送费)
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 门店ID (主键,自增) */
    private Long id;

    /** 门店名称 */
    private String name;

    /** 门店地址 */
    private String address;

    /** 门店电话 */
    private String mobile;

    /** 备用电话 */
    private String tel;

    /** 经度 */
    private BigDecimal longitude;

    /** 纬度 */
    private BigDecimal latitude;

    /** 区域名称 (如: 福田区) */
    private String areaName;

    /** 是否营业: true-营业中 false-休息中 */
    private Boolean isOpen;

    /** 是否支持外卖: true-支持 false-不支持 */
    private Boolean isTakeout;

    /** 外卖服务状态: true-可配送 false-暂停配送 */
    private Boolean takeoutServerStatus;

    /** 是否支持堂食: true-支持 false-不支持 */
    private Boolean forhereIsOpen;

    /** 起送价 (最低消费金额) */
    private BigDecimal minPrice;

    /** 包装费 */
    private BigDecimal packingFee;

    /** 配送费 */
    private BigDecimal deliveryCost;

    /** 平均配送时间 (分钟) */
    private Integer avgDeliveryCostTime;

    /** 是否展示: true-展示 false-隐藏 */
    private Boolean isShow;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 更新时间 */
    private LocalDateTime updatedAt;
}
