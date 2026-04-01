package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.dto.RechargeDTO;
import com.naixue.entity.RechargeCard;
import com.naixue.service.RechargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 储值控制器
 *
 * 提供储值相关的API接口
 *
 * 接口列表:
 * - GET /recharge/card - 获取储值卡列表
 * - POST /recharge - 会员储值
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/recharge")
@RequiredArgsConstructor
public class RechargeController {

    /** 储值服务 */
    private final RechargeService rechargeService;

    /**
     * 获取储值卡列表
     *
     * GET /recharge/card
     * 需要登录 (JWT Token)
     *
     * @return 储值卡列表
     */
    @GetMapping("/card")
    public Result<List<RechargeCard>> getRechargeCardList() {
        List<RechargeCard> cards = rechargeService.getRechargeCardList();
        return Result.success(cards);
    }

    /**
     * 会员储值
     *
     * POST /recharge
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param dto 储值参数
     * @return 成功标识
     */
    @PostMapping
    public Result<Void> recharge(
            @RequestAttribute Long memberId,
            @RequestBody RechargeDTO dto) {
        rechargeService.recharge(memberId, dto);
        return Result.success();
    }
}
