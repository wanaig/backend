package com.naixue.exception;

import com.naixue.common.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 *
 * 用于处理业务逻辑中的异常情况
 * 继承RuntimeException,不强制捕获
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     * 参考ResultCode枚举
     */
    private final Integer code;

    /**
     * 构造方法
     *
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法
     *
     * @param resultCode 响应码枚举
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
