package com.cpy.source_back_manager.model.dto.user;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserAddRequest {
    /**
     * 用户账号
     */
    private String username;
    /**
     *  权限 admin user
     */
    private String role;

    /**
     * 用户名
     */
    private String password;

}
