package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.GoodsCategory;
import com.naixue.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品分类控制器
 *
 * 提供商品分类相关的API接口
 *
 * 接口列表:
 * - GET /goodsCategory - 获取所有商品分类
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/goodsCategory")
@RequiredArgsConstructor
public class GoodsCategoryController {

    /** 商品Mapper */
    private final GoodsMapper goodsMapper;

    /**
     * 获取所有商品分类
     *
     * GET /goodsCategory
     * 无需登录
     *
     * @return 分类列表,按sort字段排序
     */
    @GetMapping
    public Result<List<GoodsCategory>> getCategoryList() {
        List<GoodsCategory> categories = goodsMapper.selectAllCategories();
        return Result.success(categories);
    }
}
