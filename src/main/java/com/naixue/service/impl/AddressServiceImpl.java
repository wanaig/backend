package com.naixue.service.impl;

import com.naixue.common.ResultCode;
import com.naixue.dto.AddressDTO;
import com.naixue.entity.Address;
import com.naixue.entity.District;
import com.naixue.exception.BusinessException;
import com.naixue.mapper.AddressMapper;
import com.naixue.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地址服务实现类
 *
 * 实现AddressService接口,提供收货地址相关的业务逻辑
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    /** 地址Mapper */
    private final AddressMapper addressMapper;

    /**
     * 获取收货地址列表
     *
     * @param memberId 会员ID
     * @return 地址列表
     */
    @Override
    public List<Address> getAddressList(Long memberId) {
        return addressMapper.selectByMemberId(memberId);
    }

    /**
     * 获取收货地址详情
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     * @return 地址详情
     */
    @Override
    public Address getAddressById(Long memberId, Long addressId) {
        Address address = addressMapper.selectByIdAndMemberId(addressId, memberId);
        if (address == null) {
            throw new BusinessException(ResultCode.ADDRESS_NOT_FOUND);
        }
        return address;
    }

    /**
     * 新增收货地址
     *
     * 如果设置为默认地址,会先清除其他默认地址
     *
     * @param memberId 会员ID
     * @param dto 地址信息
     */
    @Override
    @Transactional
    public void createAddress(Long memberId, AddressDTO dto) {
        Address address = new Address();
        address.setMemberId(memberId);
        address.setAcceptName(dto.getAcceptName());
        address.setMobile(dto.getMobile());
        address.setSex(dto.getSex());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setArea(dto.getArea());
        address.setProvinceName(dto.getProvinceName());
        address.setCityName(dto.getCityName());
        address.setAreaName(dto.getAreaName());
        address.setStreet(dto.getStreet());
        address.setDoorNumber(dto.getDoorNumber());
        address.setIsDefault(dto.getIsDefault());

        // 如果设为默认地址,先清除其他默认
        if (dto.getIsDefault() != null && dto.getIsDefault() == 1) {
            addressMapper.clearDefault(memberId);
        }

        addressMapper.insert(address);
    }

    /**
     * 更新收货地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     * @param dto 地址信息
     */
    @Override
    @Transactional
    public void updateAddress(Long memberId, Long addressId, AddressDTO dto) {
        Address address = addressMapper.selectByIdAndMemberId(addressId, memberId);
        if (address == null) {
            throw new BusinessException(ResultCode.ADDRESS_NOT_FOUND);
        }

        address.setAcceptName(dto.getAcceptName());
        address.setMobile(dto.getMobile());
        address.setSex(dto.getSex());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setArea(dto.getArea());
        address.setProvinceName(dto.getProvinceName());
        address.setCityName(dto.getCityName());
        address.setAreaName(dto.getAreaName());
        address.setStreet(dto.getStreet());
        address.setDoorNumber(dto.getDoorNumber());
        address.setIsDefault(dto.getIsDefault());

        // 如果设为默认地址,先清除其他默认
        if (dto.getIsDefault() != null && dto.getIsDefault() == 1) {
            addressMapper.clearDefault(memberId);
        }

        addressMapper.update(address);
    }

    /**
     * 删除收货地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     */
    @Override
    @Transactional
    public void deleteAddress(Long memberId, Long addressId) {
        addressMapper.delete(addressId, memberId);
    }

    /**
     * 设置默认地址
     *
     * @param memberId 会员ID
     * @param addressId 地址ID
     */
    @Override
    @Transactional
    public void setDefaultAddress(Long memberId, Long addressId) {
        addressMapper.clearDefault(memberId);
        addressMapper.setDefault(addressId, memberId);
    }
}
