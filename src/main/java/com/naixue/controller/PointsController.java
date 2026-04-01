package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.entity.PointsFlow;
import com.naixue.service.PointsService;
import com.naixue.vo.PointsOverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分控制器
 *
 * 提供积分相关的API接口
 *
 * 接口列表:
 * - GET /points - 获取积分概况
 * - GET /points/flow - 获取积分流水
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointsController {

    /** 积分服务 */
    private final PointsService pointsService;

    /**
     * 获取积分概况
     *
     * GET /points
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 积分概况
     */
    @GetMapping
    public Result<PointsOverviewVO> getPointsOverview(@RequestAttribute Long memberId) {
        PointsOverviewVO overview = pointsService.getPointsOverview(memberId);
        return Result.success(overview);
    }

    /**
     * 获取积分流水
     *
     * GET /points/flow
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param page 页码
     * @param page_size 每页数量
     * @return 流水列表
     */
    @GetMapping("/flow")
    public Result<List<PointsFlow>> getPointsFlow(
            @RequestAttribute Long memberId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer page_size) {
        List<PointsFlow> flows = pointsService.getPointsFlow(memberId, page, page_size);
        return Result.success(flows);
    }
}
