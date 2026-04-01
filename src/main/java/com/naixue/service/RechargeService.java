package com.naixue.service;

import com.naixue.dto.RechargeDTO;
import com.naixue.entity.RechargeCard;

import java.util.List;

/**
 * 储值服务接口
 *
 * 定义储值相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface RechargeService {

    /**
     * 获取储值卡列表
     *
     * @return 储值卡列表
     */
    List<RechargeCard> getRechargeCardList();

    /**
     * 会员储值
     *
     * 流程:
     * 1. 校验储值卡信息
     * 2. 增加会员余额
     * 3. 记录积分/储值流水
     * 4. 更新成长值
     *
     * @param memberId 会员ID
     * @param dto 储值参数
     */
    void recharge(Long memberId, RechargeDTO dto);
}
