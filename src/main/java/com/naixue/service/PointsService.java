package com.naixue.service;

import com.naixue.entity.PointsFlow;
import com.naixue.vo.PointsOverviewVO;
import java.util.List;

/**
 * 积分服务接口
 *
 * 定义积分相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface PointsService {

    /**
     * 获取积分概况
     *
     * @param memberId 会员ID
     * @return 积分概况
     */
    PointsOverviewVO getPointsOverview(Long memberId);

    /**
     * 获取积分流水
     *
     * @param memberId 会员ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 流水列表
     */
    List<PointsFlow> getPointsFlow(Long memberId, Integer page, Integer pageSize);
}
