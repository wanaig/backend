package com.naixue.service;

import com.naixue.entity.Goods;
import com.naixue.vo.CategoryVO;
import java.util.List;

/**
 * 商品服务接口
 *
 * 定义商品相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface GoodsService {

    /**
     * 获取商品列表(按分类)
     *
     * @param categoryId 分类ID (可选,为空则返回所有分类及其商品)
     * @return 分类商品列表
     */
    List<CategoryVO> getGoodsListByCategory(Long categoryId);

    /**
     * 获取商品详情
     *
     * @param goodsId 商品ID
     * @return 商品信息
     */
    Goods getGoodsById(Long goodsId);
}
