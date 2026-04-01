package com.naixue.service;

import com.naixue.dto.AddressDTO;
import com.naixue.entity.Address;
import java.util.List;

/**
 * 地址服务接口
 *
 * 定义收货地址相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
public interface AddressService {

    /**
     * 获取收货地址列表
     *
     * @param memberId 会员ID
     * @return 地址列表
     */
    List<Address> getAddressList(Long memberId);

    /**
     * 获取收货地址详情
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     * @return 地址详情
     */
    Address getAddressById(Long memberId, Long addressId);

    /**
     * 新增收货地址
     *
     * @param memberId 会员ID
     * @param dto 地址信息
     */
    void createAddress(Long memberId, AddressDTO dto);

    /**
     * 更新收货地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     * @param dto 地址信息
     */
    void updateAddress(Long memberId, Long addressId, AddressDTO dto);

    /**
     * 删除收货地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     */
    void deleteAddress(Long memberId, Long addressId);

    /**
     * 设置默认地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     */
    void setDefaultAddress(Long memberId, Long addressId);
}
