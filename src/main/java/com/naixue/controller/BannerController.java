package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.Banner;
import com.naixue.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制器
 *
 * 提供轮播图相关的API接口
 *
 * 接口列表:
 * - GET /banner - 获取轮播图列表
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {

    /** 轮播图服务 */
    private final BannerService bannerService;

    /**
     * 获取轮播图列表
     *
     * GET /banner
     * 无需登录
     *
     * @param position 位置 (默认为"home")
     * @return 轮播图列表
     */
    @GetMapping
    public Result<List<Banner>> getBannerList(
            @RequestParam(required = false, defaultValue = "home") String position) {
        List<Banner> banners = bannerService.getBannerList(position);
        return Result.success(banners);
    }
}
