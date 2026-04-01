package com.naixue.controller;

import com.naixue.common.Result;
import com.naixue.dto.AddressDTO;
import com.naixue.entity.Address;
import com.naixue.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址控制器
 *
 * 提供收货地址相关的API接口
 *
 * 接口列表:
 * - GET /address - 获取地址列表
 * - POST /address - 新增地址
 * - PUT /address/{id} - 更新地址
 * - DELETE /address/{id} - 删除地址
 * - PUT /address/{id}/default - 设置默认地址
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    /** 地址服务 */
    private final AddressService addressService;

    /**
     * 获取收货地址列表
     *
     * GET /address
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @return 地址列表
     */
    @GetMapping
    public Result<List<Address>> getAddressList(@RequestAttribute Long memberId) {
        List<Address> addresses = addressService.getAddressList(memberId);
        return Result.success(addresses);
    }

    /**
     * 新增收货地址
     *
     * POST /address
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param dto 地址信息
     * @return 成功标识
     */
    @PostMapping
    public Result<Void> createAddress(
            @RequestAttribute Long memberId,
            @RequestBody AddressDTO dto) {
        addressService.createAddress(memberId, dto);
        return Result.success();
    }

    /**
     * 更新收货地址
     *
     * PUT /address/{id}
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 地址ID
     * @param dto 地址信息
     * @return 成功标识
     */
    @PutMapping("/{id}")
    public Result<Void> updateAddress(
            @RequestAttribute Long memberId,
            @PathVariable Long id,
            @RequestBody AddressDTO dto) {
        addressService.updateAddress(memberId, id, dto);
        return Result.success();
    }

    /**
     * 删除收货地址
     *
     * DELETE /address/{id}
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 地址ID
     * @return 成功标识
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteAddress(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        addressService.deleteAddress(memberId, id);
        return Result.success();
    }

    /**
     * 设置默认地址
     *
     * PUT /address/{id}/default
     * 需要登录 (JWT Token)
     *
     * @param memberId 会员ID (从JWT Token中解析获取)
     * @param id 地址ID
     * @return 成功标识
     */
    @PutMapping("/{id}/default")
    public Result<Void> setDefaultAddress(
            @RequestAttribute Long memberId,
            @PathVariable Long id) {
        addressService.setDefaultAddress(memberId, id);
        return Result.success();
    }
}
