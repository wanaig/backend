package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.dto.CreateOrderDTO;
import com.naixue.entity.*;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.*;
import com.naixue.service.OrderService;
import com.naixue.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 *
 * 实现OrderService接口,提供订单相关的业务逻辑
 *
 * 订单状态:
 * - 0: 待支付
 * - 1: 已支付
 * - 2: 制作中
 * - 3: 已完成
 * - 4: 已取消
 * - 5: 退款中
 * - 6: 已退款
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    /** 订单Mapper */
    private final OrderMapper orderMapper;

    /** 商品Mapper */
    private final GoodsMapper goodsMapper;

    /** 门店Mapper */
    private final StoreMapper storeMapper;

    /** 会员Mapper */
    private final MemberMapper memberMapper;

    /** 优惠券Mapper */
    private final CouponMapper couponMapper;

    /**
     * 订单状态文本数组
     * STATUS_TEXT[0] = "待支付", STATUS_TEXT[1] = "已支付", 以此类推
     */
    private static final String[] STATUS_TEXT = {"待支付", "已支付", "制作中", "已完成", "已取消", "退款中", "已退款"};

    /**
     * 创建订单
     *
     * 流程:
     * 1. 校验门店是否存在
     * 2. 校验所有商品是否有效
     * 3. 计算订单总金额
     * 4. 生成唯一订单号
     * 5. 保存订单信息
     * 6. 保存订单商品列表
     *
     * @param memberId 会员ID
     * @param dto 创建订单参数
     * @return 订单ID
     */
    @Override
    @Transactional
    public Long createOrder(Long memberId, CreateOrderDTO dto) {
        // 校验门店
        Store store = storeMapper.selectById(dto.getStoreId());
        if (store == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // 计算订单金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalNum = 0;

        // 校验商品并计算金额
        for (CreateOrderDTO.GoodsItem item : dto.getGoodsList()) {
            Goods goods = goodsMapper.selectById(item.getGoodsId());
            if (goods == null) {
                throw new BusinessException(ResultCode.GOODS_NOT_FOUND);
            }
            if (!goods.getStatus()) {
                throw new BusinessException(ResultCode.GOODS_OFFLINE);
            }
            totalAmount = totalAmount.add(goods.getPrice().multiply(new BigDecimal(item.getNumber())));
            totalNum += item.getNumber();
        }

        // 生成订单号
        String orderNo = generateOrderNo();

        // 构建订单信息
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setMemberId(memberId);
        order.setStoreId(dto.getStoreId());
        order.setTypeCate(dto.getTypeCate());
        order.setStatus(0); // 待支付
        order.setTotalAmount(totalAmount);
        order.setAmount(totalAmount);
        order.setGoodsNum(totalNum);
        order.setPostscript(dto.getRemark());
        order.setPayMode("wechat");
        order.setSortNum("520");
        order.setCreatedAt(LocalDateTime.now());

        // 保存订单
        orderMapper.insert(order);

        // 保存订单商品
        for (CreateOrderDTO.GoodsItem item : dto.getGoodsList()) {
            Goods goods = goodsMapper.selectById(item.getGoodsId());
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(goods.getId());
            orderGoods.setGoodsName(goods.getName());
            orderGoods.setPrice(goods.getPrice());
            orderGoods.setNumber(item.getNumber());
            orderGoods.setAmount(goods.getPrice().multiply(new BigDecimal(item.getNumber())));
            orderGoods.setProperty(item.getProperty());
            orderGoods.setImage(goods.getImages());
            orderMapper.insertOrderGoods(orderGoods);
        }

        log.info("创建订单: memberId={}, orderId={}, orderNo={}", memberId, order.getId(), orderNo);
        return order.getId();
    }

    /**
     * 获取订单详情
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     * @return 订单详情
     */
    @Override
    public OrderVO getOrderById(Long memberId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getMemberId().equals(memberId)) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        return buildOrderVO(order);
    }

    /**
     * 获取订单列表
     *
     * @param memberId 会员ID
     * @param status 状态筛选 (可选)
     * @param page 页码
     * @param pageSize 每页数量
     * @return 订单列表
     */
    @Override
    public List<OrderVO> getOrderList(Long memberId, Integer status, Integer page, Integer pageSize) {
        // 设置默认分页参数
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;

        List<Order> orders;
        if (status != null) {
            orders = orderMapper.selectByMemberIdAndStatus(memberId, status);
        } else {
            orders = orderMapper.selectByMemberId(memberId);
        }

        return orders.stream().map(this::buildOrderVO).collect(Collectors.toList());
    }

    /**
     * 支付订单
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     * @param payMode 支付方式
     */
    @Override
    @Transactional
    public void payOrder(Long memberId, Long orderId, String payMode) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getMemberId().equals(memberId)) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        if (order.getStatus() != 0) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR);
        }

        // 余额支付
        if ("余额支付".equals(payMode) || "balance".equals(payMode)) {
            Member member = memberMapper.selectById(memberId);
            if (member == null) {
                throw new BusinessException(ResultCode.MEMBER_NOT_FOUND);
            }
            if (member.getBalance().compareTo(order.getAmount()) < 0) {
                throw new BusinessException(ResultCode.BALANCE_NOT_ENOUGH);
            }
            int rows = memberMapper.decrementBalance(memberId, order.getAmount());
            if (rows == 0) {
                throw new BusinessException(ResultCode.BALANCE_NOT_ENOUGH);
            }
            log.info("【余额支付】memberId={}, orderId={}, amount={}, remainingBalance={}",
                    memberId, orderId, order.getAmount(), member.getBalance().subtract(order.getAmount()));
        }

        orderMapper.updatePayStatus(orderId, payMode);
        log.info("支付订单: memberId={}, orderId={}, payMode={}", memberId, orderId, payMode);
    }

    /**
     * 取消订单
     *
     * 仅能取消待支付状态的订单
     *
     * @param memberId 会员ID
     * @param orderId 订单ID
     */
    @Override
    @Transactional
    public void cancelOrder(Long memberId, Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null || !order.getMemberId().equals(memberId)) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        if (order.getStatus() != 0) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR);
        }
        orderMapper.updateStatus(orderId, 4); // 已取消
        log.info("取消订单: memberId={}, orderId={}", memberId, orderId);
    }

    /**
     * 构建订单VO
     *
     * 将Order实体转换为OrderVO,包含门店信息和商品列表
     *
     * @param order 订单实体
     * @return 订单VO
     */
    private OrderVO buildOrderVO(Order order) {
        OrderVO vo = new OrderVO();
        vo.setId(order.getId());
        vo.setOrderNo(order.getOrderNo());
        vo.setStatus(order.getStatus());
        vo.setStatusText(STATUS_TEXT[order.getStatus()]);
        vo.setTotalAmount(order.getTotalAmount());
        vo.setAmount(order.getAmount());
        vo.setGoodsNum(order.getGoodsNum());
        vo.setPayMode(order.getPayMode());
        vo.setCreatedAt(order.getCreatedAt() != null ? order.getCreatedAt().toEpochSecond(java.time.ZoneOffset.UTC) : 0);
        vo.setPayedAt(order.getPayedAt() != null ? order.getPayedAt().toLocalDate().toEpochDay() * 86400 : null);
        vo.setCompletedTime(order.getUpdatedAt() != null ? order.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null);
        vo.setPostscript(order.getPostscript());
        vo.setSortNum(order.getSortNum());
        vo.setTypeCate(order.getTypeCate());

        // 设置门店信息
        Store store = storeMapper.selectById(order.getStoreId());
        if (store != null) {
            OrderVO.StoreVO storeVO = new OrderVO.StoreVO();
            storeVO.setName(store.getName());
            storeVO.setAddress(store.getAddress());
            storeVO.setMobile(store.getMobile());
            vo.setStore(storeVO);
        }

        // 设置商品列表
        List<OrderGoods> goodsList = orderMapper.selectGoodsByOrderId(order.getId());
        vo.setGoods(goodsList.stream().map(g -> {
            OrderVO.OrderGoodsVO goodsVO = new OrderVO.OrderGoodsVO();
            goodsVO.setName(g.getGoodsName());
            goodsVO.setNumber(g.getNumber());
            goodsVO.setPrice(g.getPrice().toString());
            goodsVO.setAmount(g.getAmount().toString());
            goodsVO.setProperty(g.getProperty());
            goodsVO.setImage(g.getImage());
            return goodsVO;
        }).collect(Collectors.toList()));

        return vo;
    }

    /**
     * 生成唯一订单号
     *
     * 格式: NX + 时间戳 + 4位随机数
     *
     * @return 订单号
     */
    private String generateOrderNo() {
        return "NX" + System.currentTimeMillis() + String.format("%04d", (int)(Math.random() * 10000));
    }
}
