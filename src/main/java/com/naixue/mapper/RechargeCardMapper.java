package com.naixue.mapper;

import com.naixue.entity.RechargeCard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 储值卡Mapper接口
 *
 * 对应数据库表: recharge_card
 * 主要功能:
 * - 查询所有可用储值卡
 * - 根据ID查询储值卡
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface RechargeCardMapper {

    /**
     * 查询所有可用的储值卡
     *
     * 按售价排序
     *
     * @return 储值卡列表
     */
    @Select("SELECT * FROM recharge_card WHERE status = 1 ORDER BY sell_price")
    List<RechargeCard> selectAll();

    /**
     * 根据ID查询储值卡
     *
     * @param id 储值卡ID
     * @return 储值卡信息
     */
    @Select("SELECT * FROM recharge_card WHERE id = #{id} AND status = 1")
    RechargeCard selectById(Long id);
}
