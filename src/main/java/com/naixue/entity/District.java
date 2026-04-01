package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 行政区划信息实体类
 *
 * 非数据库表,用于返回地址的省市区详细信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class District implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 完整区域描述 */
    private String districts;

    /** 区/县 */
    private String area;

    /** 市 */
    private String city;

    /** 省 */
    private String province;
}
