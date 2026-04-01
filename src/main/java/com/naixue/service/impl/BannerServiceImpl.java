package com.naixue.service.impl;

import com.naixue.entity.Banner;
import com.naixue.mapper.BannerMapper;
import com.naixue.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图服务实现类
 *
 * 实现BannerService接口,提供轮播图相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    /** 轮播图Mapper */
    private final BannerMapper bannerMapper;

    /**
     * 获取轮播图列表
     *
     * @param position 位置 (默认为"home")
     * @return 轮播图列表
     */
    @Override
    public List<Banner> getBannerList(String position) {
        if (position == null || position.isEmpty()) {
            position = "home";
        }
        return bannerMapper.selectByPosition(position);
    }
}
