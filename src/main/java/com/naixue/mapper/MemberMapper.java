package com.naixue.mapper;

import com.naixue.entity.Member;
import org.apache.ibatis.annotations.*;
import java.math.BigDecimal;

/**
 * 会员Mapper接口
 *
 * 对应数据库表: member
 * 主要功能:
 * - 根据openid查询会员
 * - 根据ID查询会员
 * - 新增会员
 * - 更新会员信息
 * - 积分增减操作
 * - 余额增减操作
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface MemberMapper {

    /**
     * 根据OpenID查询会员
     *
     * @param openid 微信OpenID
     * @return 会员信息,未找到返回null
     */
    @Select("SELECT * FROM member WHERE openid = #{openid} LIMIT 1")
    Member selectByOpenid(@Param("openid") String openid);

    /**
     * 根据会员ID查询会员
     *
     * @param customerId 会员ID
     * @return 会员信息,未找到返回null
     */
    @Select("SELECT * FROM member WHERE customer_id = #{customerId}")
    Member selectById(@Param("customerId") Long customerId);

    /**
     * 新增会员信息
     *
     * 使用自增主键,会自动将生成的customerId回填到实体对象中
     *
     * @param member 会员信息
     * @return 影响行数
     */
    @Insert("INSERT INTO member (openid, unionid, nickname, avatar, member_level, " +
            "point_num, balance, gift_balance, current_value, member_origin, " +
            "last_login_time, created_at, updated_at) " +
            "VALUES (#{openid}, #{unionid}, #{nickname}, #{avatar}, #{memberLevel}, " +
            "#{pointNum}, #{balance}, #{giftBalance}, #{currentValue}, #{memberOrigin}, " +
            "#{lastLoginTime}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "customerId")
    int insert(Member member);

    /**
     * 更新会员基本信息
     *
     * @param member 会员信息(只更新非空字段)
     * @return 影响行数
     */
    int updateById(Member member);

    /**
     * 更新会员最后登录时间
     *
     * @param customerId 会员ID
     * @return 影响行数
     */
    @Update("UPDATE member SET last_login_time = NOW() WHERE customer_id = #{customerId}")
    int updateLoginTime(@Param("customerId") Long memberId);

    /**
     * 增加会员积分
     *
     * 使用SQL的原子操作确保并发安全
     *
     * @param customerId 会员ID
     * @param points 增加的积分数量
     * @return 影响行数
     */
    @Update("UPDATE member SET point_num = point_num + #{points} WHERE customer_id = #{customerId}")
    int incrementPoints(@Param("customerId") Long memberId, @Param("points") Integer points);

    /**
     * 增加会员余额
     *
     * 使用SQL的原子操作确保并发安全
     *
     * @param customerId 会员ID
     * @param amount 增加的金额
     * @return 影响行数
     */
    @Update("UPDATE member SET balance = balance + #{amount} WHERE customer_id = #{customerId}")
    int incrementBalance(@Param("customerId") Long memberId, @Param("amount") BigDecimal amount);

    /**
     * 查询会员积分数量
     *
     * @param customerId 会员ID
     * @return 积分数量
     */
    @Select("SELECT point_num FROM member WHERE customer_id = #{customerId}")
    Integer selectPointNum(@Param("customerId") Long memberId);
}
