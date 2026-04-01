package com.naixue.mapper;

import com.naixue.entity.Order;
import com.naixue.entity.OrderGoods;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 订单Mapper接口
 *
 * 对应数据库表: orders, order_goods
 * 主要功能:
 * - 订单CRUD操作
 * - 订单状态管理
 * - 订单商品管理
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface OrderMapper {

    /**
     * 新增订单
     *
     * 使用自增主键,会自动将生成的ID回填到实体对象中
     *
     * @param order 订单信息
     * @return 影响行数
     */
    @Insert("INSERT INTO orders (order_no, member_id, store_id, type_cate, status, " +
            "total_amount, amount, goods_num, pay_mode, postscript, created_at) " +
            "VALUES (#{orderNo}, #{memberId}, #{storeId}, #{typeCate}, #{status}, " +
            "#{totalAmount}, #{amount}, #{goodsNum}, #{payMode}, #{postscript}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    /**
     * 根据订单ID查询订单
     *
     * @param id 订单ID
     * @return 订单信息,未找到返回null
     */
    @Select("SELECT * FROM orders WHERE id = #{id} AND deleted = 0")
    Order selectById(@Param("id") Long id);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    @Select("SELECT * FROM orders WHERE order_no = #{orderNo} AND deleted = 0")
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询会员的所有订单
     *
     * 按创建时间降序排列
     *
     * @param memberId 会员ID
     * @return 订单列表
     */
    @Select("SELECT * FROM orders WHERE member_id = #{memberId} AND deleted = 0 ORDER BY created_at DESC")
    List<Order> selectByMemberId(@Param("memberId") Long memberId);

    /**
     * 根据会员ID和订单状态查询订单
     *
     * @param memberId 会员ID
     * @param status 订单状态
     * @return 订单列表
     */
    @Select("SELECT * FROM orders WHERE member_id = #{memberId} AND status = #{status} AND deleted = 0 ORDER BY created_at DESC")
    List<Order> selectByMemberIdAndStatus(@Param("memberId") Long memberId, @Param("status") Integer status);

    /**
     * 更新订单状态
     *
     * @param id 订单ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE orders SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新订单支付状态
     *
     * @param id 订单ID
     * @param payMode 支付方式
     * @return 影响行数
     */
    @Update("UPDATE orders SET payed_at = NOW(), status = 1, pay_mode = #{payMode}, updated_at = NOW() WHERE id = #{id}")
    int updatePayStatus(@Param("id") Long id, @Param("payMode") String payMode);

    /**
     * 统计会员今日订单数量
     *
     * 用于判断用户是否今日首单
     *
     * @param memberId 会员ID
     * @return 今日订单数
     */
    @Select("SELECT COUNT(*) FROM orders WHERE member_id = #{memberId} AND DATE(created_at) = CURDATE() AND deleted = 0")
    int countTodayOrders(@Param("memberId") Long memberId);

    /**
     * 新增订单商品
     *
     * @param orderGoods 订单商品信息
     * @return 影响行数
     */
    @Insert("INSERT INTO order_goods (order_id, goods_id, goods_name, price, number, amount, property) " +
            "VALUES (#{orderId}, #{goodsId}, #{goodsName}, #{price}, #{number}, #{amount}, #{property})")
    int insertOrderGoods(OrderGoods orderGoods);

    /**
     * 查询订单的所有商品
     *
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    @Select("SELECT * FROM order_goods WHERE order_id = #{orderId}")
    List<OrderGoods> selectGoodsByOrderId(@Param("orderId") Long orderId);
}
