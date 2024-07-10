package com.cpy.source_back_manager.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class UserVo {
    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 0 用户 1 admin
     */
    private Integer role;

    /**
     * 用户头像图片-http
     */
    private String userPic;
}
