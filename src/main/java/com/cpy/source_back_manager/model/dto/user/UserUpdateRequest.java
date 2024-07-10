package com.cpy.source_back_manager.model.dto.user;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserUpdateRequest {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户头像图片-base64
     */
    private byte[] userPic;
}

