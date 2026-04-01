package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.Goods;
import com.naixue.service.GoodsService;
import com.naixue.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品控制器
 *
 * 提供商品相关的API接口
 *
 * 接口列表:
 * - GET /goods - 获取商品列表(按分类)
 * - GET /goods/{id} - 获取商品详情
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodsController {

    /** 商品服务 */
    private final GoodsService goodsService;

    /**
     * 获取商品列表
     *
     * GET /goods
     * GET /goods?category_id=xxx
     * 无需登录
     *
     * @param categoryId 分类ID (可选,为空则返回所有分类及商品)
     * @return 分类商品列表
     */
    @GetMapping
    public Result<List<CategoryVO>> getGoodsList(
            @RequestParam(name = "category_id", required = false) Long categoryId) {
        List<CategoryVO> result = goodsService.getGoodsListByCategory(categoryId);
        return Result.success(result);
    }

    /**
     * 获取商品详情
     *
     * GET /goods/{id}
     * 无需登录
     *
     * @param id 商品ID
     * @return 商品详情
     */
    @GetMapping("/{id}")
    public Result<Goods> getGoodsById(@PathVariable Long id) {
        Goods goods = goodsService.getGoodsById(id);
        return Result.success(goods);
    }
}
