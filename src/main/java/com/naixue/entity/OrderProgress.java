package com.naixue.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 订单进度实体类
 *
 * 非数据库表,用于返回订单制作进度信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class OrderProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 当前状态码 */
    private Integer status;

    /** 状态描述文本 */
    private String statusText;

    /** 进度步骤列表 */
    private List<ProgressStep> progress;

    /** 取餐号 */
    private String sortNum;

    /**
     * 进度步骤内部类
     *
     * @author naixue-backend
     */
    @Data
    public static class ProgressStep implements Serializable {
        private static final long serialVersionUID = 1L;

        /** 步骤名称 (如: "已下单", "制作中", "已完成") */
        private String step;

        /** 完成时间 */
        private String time;

        /** 是否已完成 */
        private Boolean done;
    }
}
