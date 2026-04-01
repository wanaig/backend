package com.naixue.service;

import com.naixue.entity.Store;

/**
 * 门店服务接口
 *
 * 定义门店相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface StoreService {

    /**
     * 获取门店信息
     *
     * 根据经纬度返回附近门店或默认门店
     *
     * @param longitude 经度 (可选)
     * @param latitude 纬度 (可选)
     * @return 门店信息
     */
    Store getStore(String longitude, String latitude);
}
