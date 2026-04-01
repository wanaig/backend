package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.dto.CreateOrderDTO;
import com.naixue.entity.OrderProgress;
import com.naixue.service.OrderService;
import com.naixue.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 *
 * 提供订单相关的API接口
 *
 * 接口列表:
 * - POST /order - 创建订单
 * - GET /order - 获取订单列表
 * - GET /order/{id} - 获取订单详情
 * - PUT /order/{id}/cancel - 取消订单
 * - PUT /order/{id}/pay - 支付订单
 * - GET /order/{id}/progress - 获取订单制作进度
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    /** 订单服务 */
    private final OrderService orderService;

    /**
     * 创建订单
     *
     * POST /order
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param dto 创建订单参数
     * @return 订单ID
     */
    @PostMapping
    public Result<Map<String, Object>> createOrder(
            @RequestAttribute Long memberId,
            @RequestBody CreateOrderDTO dto) {
        Long orderId = orderService.createOrder(memberId, dto);
        Map<String, Object> result = new HashMap<>();
        result.put("order_id", orderId);
        return Result.success(result);
    }

    /**
     * 获取订单列表
     *
     * GET /order
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param status 订单状态筛选 (可选)
     * @param page 页码
     * @param page_size 每页数量
     * @return 订单列表
     */
    @GetMapping
    public Result<List<OrderVO>> getOrderList(
            @RequestAttribute Long memberId,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer page_size) {
        List<OrderVO> orders = orderService.getOrderList(memberId, status, page, page_size);
        return Result.success(orders);
    }

    /**
     * 获取订单详情
     *
     * GET /order/{id}
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 订单ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<OrderVO> getOrderById(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        OrderVO order = orderService.getOrderById(memberId, id);
        return Result.success(order);
    }

    /**
     * 取消订单
     *
     * PUT /order/{id}/cancel
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 订单ID
     * @return 成功标识
     */
    @PutMapping("/{id}/cancel")
    public Result<Void> cancelOrder(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        orderService.cancelOrder(memberId, id);
        return Result.success();
    }

    /**
     * 支付订单
     *
     * PUT /order/{id}/pay
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 订单ID
     * @return 成功标识
     */
    @PutMapping("/{id}/pay")
    public Result<Void> payOrder(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        orderService.payOrder(memberId, id, "微信支付");
        return Result.success();
    }

    /**
     * 获取订单制作进度
     *
     * GET /order/{id}/progress
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 订单ID
     * @return 制作进度
     */
    @GetMapping("/{id}/progress")
    public Result<OrderProgress> getOrderProgress(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        OrderVO order = orderService.getOrderById(memberId, id);
        OrderProgress progress = new OrderProgress();
        progress.setStatus(order.getStatus());
        progress.setStatusText(order.getStatusText());
        progress.setSortNum(order.getSortNum());

        List<OrderProgress.ProgressStep> steps = List.of(
            createStep("已下单", order.getCreatedAt() != null, order.getCreatedAt() + ""),
            createStep("制作中", order.getStatus() >= 2, null),
            createStep("已完成", order.getStatus() >= 3, order.getCompletedTime())
        );
        progress.setProgress(steps);

        return Result.success(progress);
    }

    /**
     * 创建进度步骤
     */
    private OrderProgress.ProgressStep createStep(String step, boolean done, String time) {
        OrderProgress.ProgressStep progressStep = new OrderProgress.ProgressStep();
        progressStep.setStep(step);
        progressStep.setDone(done);
        progressStep.setTime(time);
        return progressStep;
    }
}
