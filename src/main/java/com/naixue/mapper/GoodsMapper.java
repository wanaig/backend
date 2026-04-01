package com.naixue.mapper;

import com.naixue.entity.Goods;
import com.naixue.entity.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 商品Mapper接口
 *
 * 对应数据库表: goods, goods_category
 * 主要功能:
 * - 查询所有/指定分类的商品
 * - 根据ID查询商品
 * - 查询所有/指定商品分类
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface GoodsMapper {

    /**
     * 查询所有上架商品
     *
     * 按销量降序排列
     *
     * @return 商品列表
     */
    @Select("SELECT * FROM goods WHERE status = 1 ORDER BY sales DESC")
    List<Goods> selectAll();

    /**
     * 根据分类ID查询商品
     *
     * @param categoryId 分类ID
     * @return 商品列表
     */
    @Select("SELECT * FROM goods WHERE category_id = #{categoryId} AND status = 1 ORDER BY sales DESC")
    List<Goods> selectByCategoryId(Long categoryId);

    /**
     * 根据ID查询商品
     *
     * @param id 商品ID
     * @return 商品信息,未找到返回null
     */
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods selectById(Long id);

    /**
     * 查询所有商品分类
     *
     * 按sort字段排序
     *
     * @return 分类列表
     */
    @Select("SELECT * FROM goods_category ORDER BY sort")
    List<GoodsCategory> selectAllCategories();

    /**
     * 根据ID查询商品分类
     *
     * @param id 分类ID
     * @return 分类列表
     */
    @Select("SELECT * FROM goods_category WHERE id = #{id} ORDER BY sort")
    List<GoodsCategory> selectCategoryById(Long id);
}
