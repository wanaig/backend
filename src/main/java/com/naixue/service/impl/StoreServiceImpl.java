package com.naixue.service.impl;

import com.naixue.entity.Store;
import com.naixue.mapper.StoreMapper;
import com.naixue.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店服务实现类
 *
 * 实现StoreService接口,提供门店相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    /** 门店Mapper */
    private final StoreMapper storeMapper;

    /**
     * 获取门店信息
     *
     * TODO: 目前实现较为简单,直接返回第一个门店
     * 完整实现应根据经纬度计算距离并返回最近的门店
     *
     * @param longitude 经度 (预留参数)
     * @param latitude 纬度 (预留参数)
     * @return 门店信息
     */
    @Override
    public Store getStore(String longitude, String latitude) {
        List<Store> stores = storeMapper.selectAll();
        if (stores != null && !stores.isEmpty()) {
            return stores.get(0);
        }
        return null;
    }
}
