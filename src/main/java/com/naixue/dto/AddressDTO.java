package com.naixue.dto;

import lombok.Data;

/**
 * 地址请求DTO
 *
 * 用于接收新增/修改收货地址的请求参数
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class AddressDTO {

    /** 收货人姓名 */
    private String acceptName;

    /** 收货人手机号 */
    private String mobile;

    /** 性别: 0-女 1-男 */
    private Integer sex;

    /** 省份编码 */
    private Integer province;

    /** 城市编码 */
    private Integer city;

    /** 区县编码 */
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

    /** 是否设为默认地址: 0-否 1-是 */
    private Integer isDefault;
}
