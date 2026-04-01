package com.naixue.dto;

import lombok.Data;

/**
 * 更新会员信息DTO
 *
 * @author naixue-backend
 * @version 1.0.0
 */
@Data
public class UpdateMemberDTO {

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 性别: 0-未知 1-男 2-女 */
    private Integer gender;

    /** 生日 */
    private String birthday;
}