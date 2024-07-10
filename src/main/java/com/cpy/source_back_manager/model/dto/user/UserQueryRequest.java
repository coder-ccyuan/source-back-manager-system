package com.cpy.source_back_manager.model.dto.user;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserQueryRequest {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户账号
     */
    private String username;

    /**
     *  权限 admin user
     */
    private String role;

    /**
     * 昵称
     */
    private String nickname;



}

