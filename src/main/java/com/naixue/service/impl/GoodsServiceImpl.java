package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.entity.Goods;
import com.naixue.entity.GoodsCategory;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.GoodsMapper;
import com.naixue.service.GoodsService;
import com.naixue.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 *
 * 实现GoodsService接口,提供商品相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    /** 商品Mapper */
    private final GoodsMapper goodsMapper;

    /**
     * 获取商品列表(按分类)
     *
     * @param categoryId 分类ID (可选,为空则返回所有分类及其商品)
     * @return 分类商品列表
     */
    @Override
    public List<CategoryVO> getGoodsListByCategory(Long categoryId) {
        // 获取分类列表(如果指定了categoryId,则只获取该分类)
        List<GoodsCategory> categories;
        if (categoryId != null) {
            categories = goodsMapper.selectCategoryById(categoryId);
            if (categories.isEmpty()) {
                return new ArrayList<>();
            }
        } else {
            categories = goodsMapper.selectAllCategories();
        }

        // 获取所有上架商品(如果指定了categoryId,则只获取该分类的商品)
        List<Goods> goodsList;
        if (categoryId != null) {
            goodsList = goodsMapper.selectByCategoryId(categoryId);
        } else {
            goodsList = goodsMapper.selectAll();
        }

        // 按分类ID分组商品
        Map<Long, List<Goods>> goodsByCategory = goodsList.stream()
                .collect(Collectors.groupingBy(Goods::getCategoryId));

        // 组装分类VO列表
        List<CategoryVO> result = new ArrayList<>();
        for (GoodsCategory category : categories) {
            CategoryVO vo = new CategoryVO();
            vo.setId(category.getId());
            vo.setName(category.getName());
            vo.setSort(category.getSort());
            vo.setGoodsList(goodsByCategory.getOrDefault(category.getId(), new ArrayList<>()));
            result.add(vo);
        }

        return result;
    }

    /**
     * 获取商品详情
     *
     * @param goodsId 商品ID
     * @return 商品信息
     */
    @Override
    public Goods getGoodsById(Long goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new BusinessException(ResultCode.GOODS_NOT_FOUND);
        }
        return goods;
    }
}
