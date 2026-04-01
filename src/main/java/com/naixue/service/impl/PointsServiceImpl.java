package com.naixue.service.impl;

import com.naixue.entity.Member;
import com.naixue.entity.PointsFlow;
import com.naixue.mapper.MemberMapper;
import com.naixue.mapper.PointsMapper;
import com.naixue.service.PointsService;
import com.naixue.vo.PointsOverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分服务实现类
 *
 * 实现PointsService接口,提供积分相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class PointsServiceImpl implements PointsService {

    /** 会员Mapper */
    private final MemberMapper memberMapper;

    /** 积分流水Mapper */
    private final PointsMapper pointsMapper;

    /**
     * 获取积分概况
     *
     * @param memberId 会员ID
     * @return 积分概况
     */
    @Override
    public PointsOverviewVO getPointsOverview(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        PointsOverviewVO vo = new PointsOverviewVO();
        vo.setTotalPoints(member != null ? member.getPointNum() : 0);
        vo.setForeverPoints(0);
        vo.setSoonExpiredPoints(0);
        vo.setExpiredTime(null);
        return vo;
    }

    /**
     * 获取积分流水
     *
     * @param memberId 会员ID
     * @param page 页码
     * @param pageSize 每页数量
     * @return 流水列表
     */
    @Override
    public List<PointsFlow> getPointsFlow(Long memberId, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        int offset = (page - 1) * pageSize;
        return pointsMapper.selectByMemberIdWithPage(memberId, offset, pageSize);
    }
}
