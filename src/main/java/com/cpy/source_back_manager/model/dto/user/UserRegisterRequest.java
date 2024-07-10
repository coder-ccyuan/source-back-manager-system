package com.cpy.source_back_manager.model.dto.user;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserRegisterRequest {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String checkPassword;
}

