package com.cpy.source_back_manager.model.dto.user;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserLoginRequest {

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
}

