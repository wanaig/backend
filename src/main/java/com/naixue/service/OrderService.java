package com.naixue.service;

import com.naixue.dto.CreateOrderDTO;
import com.naixue.entity.Order;
import com.naixue.vo.OrderVO;
import java.util.List;

/**
 * 订单服务接口
 *
 * 定义订单相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * 流程:
     * 1. 校验门店、商品信息
     * 2. 计算订单金额
     * 3. 生成订单号
     * 4. 保存订单及订单商品
     *
     * @param memberId 会员ID
     * @param dto 创建订单参数
     * @return 订单ID
     */
    Long createOrder(Long memberId, CreateOrderDTO dto);

    /**
     * 获取订单详情
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    OrderVO getOrderById(Long memberId, Long orderId);

    /**
     * 获取订单列表
     *
     * @param memberId 会员ID
     * @param status 订单状态筛选 (可选)
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    List<OrderVO> getOrderList(Long memberId, Integer status, Integer page, Integer pageSize);

    /**
     * 支付订单
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     * @param payMode 支付方式
     */
    void payOrder(Long memberId, Long orderId, String payMode);

    /**
     * 取消订单
     *
     * 仅可取消待支付状态的订单
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     */
    void cancelOrder(Long memberId, Long orderId);
}
