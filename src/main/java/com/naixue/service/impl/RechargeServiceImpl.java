package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.dto.RechargeDTO;
import com.naixue.entity.Member;
import com.naixue.entity.PointsFlow;
import com.naixue.entity.RechargeCard;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.MemberMapper;
import com.naixue.mapper.PointsMapper;
import com.naixue.mapper.RechargeCardMapper;
import com.naixue.service.RechargeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 储值服务实现类
 *
 * 实现RechargeService接口,提供储值相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RechargeServiceImpl implements RechargeService {

    /** 储值卡Mapper */
    private final RechargeCardMapper rechargeCardMapper;

    /** 会员Mapper */
    private final MemberMapper memberMapper;

    /** 积分流水Mapper */
    private final PointsMapper pointsMapper;

    /**
     * 获取储值卡列表
     *
     * @return 储值卡列表
     */
    @Override
    public List<RechargeCard> getRechargeCardList() {
        return rechargeCardMapper.selectAll();
    }

    /**
     * 会员储值
     *
     * 流程:
     * 1. 校验储值卡
     * 2. 增加会员余额
     * 3. 记录储值流水
     *
     * @param memberId 会员ID
     * @param dto 储值参数
     */
    @Override
    @Transactional
    public void recharge(Long memberId, RechargeDTO dto) {
        RechargeCard card = rechargeCardMapper.selectById(dto.getCardId());
        if (card == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }

        // 增加会员余额
        memberMapper.incrementBalance(memberId, card.getValue());

        // 记录储值流水
        PointsFlow flow = new PointsFlow();
        flow.setMemberId(memberId);
        flow.setChangeType(1); // 增加
        flow.setChangeNum(0);
        flow.setReason("储值 " + card.getName());
        flow.setSourceType(6); // 储值
        pointsMapper.insert(flow);

        // TODO: 更新成长值
        Member member = memberMapper.selectById(memberId);
        int newValue = (member.getCurrentValue() != null ? member.getCurrentValue() : 0) + card.getValue().intValue();
        member.setCurrentValue(newValue);

        log.info("储值: memberId={}, cardId={}, amount={}", memberId, dto.getCardId(), card.getValue());
    }
}
