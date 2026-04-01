package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.Store;
import com.naixue.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 门店控制器
 *
 * 提供门店相关的API接口
 *
 * 接口列表:
 * - GET /store - 获取门店信息
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    /** 门店服务 */
    private final StoreService storeService;

    /**
     * 获取门店信息
     *
     * GET /store
     * 无需登录
     *
     * @param longitude 经度 (可选)
     * @param latitude 纬度 (可选)
     * @return 门店信息
     */
    @GetMapping
    public Result<Store> getStore(
            @RequestParam(required = false) String longitude,
            @RequestParam(required = false) String latitude) {
        Store store = storeService.getStore(longitude, latitude);
        return Result.success(store);
    }
}
