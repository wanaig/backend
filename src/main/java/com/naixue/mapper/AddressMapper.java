package com.naixue.mapper;

import com.naixue.entity.Address;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 收货地址Mapper接口
 *
 * 对应数据库表: address
 * 主要功能:
 * - 地址CRUD操作
 * - 默认地址管理
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Mapper
public interface AddressMapper {

    /**
     * 查询会员的所有收货地址
     *
     * 按是否默认、创建时间排序
     *
     * @param memberId 会员ID
     * @return 地址列表
     */
    @Select("SELECT * FROM address WHERE member_id = #{memberId} AND deleted = 0 ORDER BY is_default DESC, created_at DESC")
    List<Address> selectByMemberId(@Param("memberId") Long memberId);

    /**
     * 根据ID和会员ID查询地址
     *
     * @param id 地址ID
     * @param memberId 会员ID
     * @return 地址信息
     */
    @Select("SELECT * FROM address WHERE id = #{id} AND member_id = #{memberId} AND deleted = 0")
    Address selectByIdAndMemberId(@Param("id") Long id, @Param("memberId") Long memberId);

    /**
     * 新增收货地址
     *
     * @param address 地址信息
     * @return 影响行数
     */
    @Insert("INSERT INTO address (member_id, accept_name, mobile, sex, province, city, area, " +
            "province_name, city_name, area_name, street, door_number, is_default, created_at) " +
            "VALUES (#{memberId}, #{acceptName}, #{mobile}, #{sex}, #{province}, #{city}, #{area}, " +
            "#{provinceName}, #{cityName}, #{areaName}, #{street}, #{doorNumber}, #{isDefault}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Address address);

    /**
     * 更新收货地址
     *
     * @param address 地址信息
     * @return 影响行数
     */
    @Update("UPDATE address SET accept_name = #{acceptName}, mobile = #{mobile}, sex = #{sex}, " +
            "province = #{province}, city = #{city}, area = #{area}, " +
            "province_name = #{provinceName}, city_name = #{cityName}, area_name = #{areaName}, " +
            "street = #{street}, door_number = #{doorNumber}, is_default = #{isDefault} " +
            "WHERE id = #{id} AND member_id = #{memberId}")
    int update(Address address);

    /**
     * 删除收货地址(软删除)
     *
     * @param id 地址ID
     * @param memberId 会员ID
     * @return 影响行数
     */
    @Delete("UPDATE address SET deleted = 1 WHERE id = #{id} AND member_id = #{memberId}")
    int delete(@Param("id") Long id, @Param("memberId") Long memberId);

    /**
     * 清除会员的所有默认地址标记
     *
     * 在设置新默认地址前调用
     *
     * @param memberId 会员ID
     * @return 影响行数
     */
    @Update("UPDATE address SET is_default = 0 WHERE member_id = #{memberId}")
    int clearDefault(@Param("memberId") Long memberId);

    /**
     * 设置默认地址
     *
     * @param id 地址ID
     * @param memberId 会员ID
     * @return 影响行数
     */
    @Update("UPDATE address SET is_default = 1 WHERE id = #{id} AND member_id = #{memberId}")
    int setDefault(@Param("id") Long id, @Param("memberId") Long memberId);
}
