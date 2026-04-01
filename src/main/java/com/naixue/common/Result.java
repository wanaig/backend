package com.naixue.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一响应结果类
 *
 * 所有API接口的响应都使用此格式
 *
 * 响应格式:
 * {
 *   "code": 0,
 *   "message": "success",
 *   "data": { ... }
 * }
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     * 0 - 成功
     * 其他 - 失败 (参考ResultCode)
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 返回成功结果 (带数据)
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回成功结果 (带数据)
     *
     * @param data 响应数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 返回错误结果
     *
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return error(ResultCode.SYSTEM_ERROR.getCode(), message);
    }

    /**
     * 返回错误结果
     *
     * @param code 错误码
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
