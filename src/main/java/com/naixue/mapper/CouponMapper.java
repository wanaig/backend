package com.naixue.mapper;

import com.naixue.entity.Coupon;
import com.naixue.entity.MemberCoupon;
import org.apache.ibatis.annotations.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 优惠券Mapper接口
 *
 * 对应数据库表: coupon, member_coupon
 * 主要功能:
 * - 优惠券查询
 * - 会员优惠券领取/使用
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface CouponMapper {

    /**
     * 查询所有可用优惠券
     *
     * @return 优惠券列表
     */
    @Select("SELECT * FROM coupon WHERE status = 1")
    List<Coupon> selectAll();

    /**
     * 根据ID查询优惠券
     *
     * @param id 优惠券ID
     * @return 优惠券信息
     */
    @Select("SELECT * FROM coupon WHERE id = #{id}")
    Coupon selectById(Long id);

    /**
     * 查询会员可用的优惠券
     *
     * 条件: 未使用且未过期
     *
     * @param memberId 会员ID
     * @return 可用优惠券列表
     */
    @Select("SELECT c.* FROM coupon c " +
            "INNER JOIN member_coupon mc ON c.id = mc.coupon_id " +
            "WHERE mc.member_id = #{memberId} AND mc.status = 0 AND mc.end_at > NOW()")
    List<Coupon> selectAvailableByMemberId(@Param("memberId") Long memberId);

    /**
     * 查询会员的所有优惠券(不限状态)
     *
     * @param memberId 会员ID
     * @return 优惠券列表
     */
    @Select("SELECT c.* FROM coupon c " +
            "INNER JOIN member_coupon mc ON c.id = mc.coupon_id " +
            "WHERE mc.member_id = #{memberId} AND mc.status = 0")
    List<Coupon> selectAllByMemberId(@Param("memberId") Long memberId);

    /**
     * 会员领取优惠券
     *
     * @param memberId 会员ID
     * @param couponId 优惠券ID
     * @param endAt 过期时间
     * @return 影响行数
     */
    @Insert("INSERT INTO member_coupon (member_id, coupon_id, status, end_at, created_at) " +
            "VALUES (#{memberId}, #{couponId}, 0, #{endAt}, NOW())")
    int insertMemberCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId,
                          @Param("endAt") LocalDateTime endAt);

    /**
     * 使用优惠券
     *
     * @param memberId 会员ID
     * @param couponId 优惠券ID
     * @return 影响行数
     */
    @Update("UPDATE member_coupon SET status = 1 WHERE member_id = #{memberId} AND coupon_id = #{couponId}")
    int useCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId);

    /**
     * 统计会员可用的特定优惠券数量
     *
     * @param memberId 会员ID
     * @param couponId 优惠券ID
     * @return 可用数量
     */
    @Select("SELECT COUNT(*) FROM member_coupon WHERE member_id = #{memberId} AND coupon_id = #{couponId} AND status = 0")
    int countAvailableCoupon(@Param("memberId") Long memberId, @Param("couponId") Long couponId);

    /**
     * 查询会员的优惠券列表
     *
     * @param memberId 会员ID
     * @return 会员优惠券列表
     */
    @Select("SELECT * FROM member_coupon WHERE member_id = #{memberId} AND status = 0")
    List<MemberCoupon> selectMemberCoupons(@Param("memberId") Long memberId);
}
