package com.naixue.vo;

import lombok.Data;
import java.io.Serializable;

/**
 * 登录响应VO
 *
 * 登录接口的响应数据结构
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** JWT Token令牌 */
    private String token;

    /** Token类型,固定为"Bearer" */
    private String tokenType;

    /** 过期时间(秒),7天=604800秒 */
    private Integer expiresIn;

    /** 会员ID */
    private Long customerId;

    /** 会员昵称 */
    private String nickname;

    /** 会员头像URL */
    private String avatar;

    /** 手机号 */
    private String mobilePhone;

    /** 是否为新用户 */
    private Boolean isNewMember;
}
