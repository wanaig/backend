package com.naixue.service;

import com.naixue.entity.Banner;
import java.util.List;

/**
 * 轮播图服务接口
 *
 * 定义轮播图相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface BannerService {

    /**
     * 获取轮播图列表
     *
     * @param position 位置 (默认为"home")
     * @return 轮播图列表
     */
    List<Banner> getBannerList(String position);
}
