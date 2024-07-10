package com.cpy.source_back_manager.utils;

import com.cpy.source_back_manager.model.entity.User;

import javax.servlet.http.HttpServletRequest;

import static com.cpy.source_back_manager.constant.UserConstant.ADMIN_ROLE;
import static com.cpy.source_back_manager.constant.UserConstant.LOGIN_USER;

/**
 * @Author:成希德
 */
public class UserUtils {
    public static boolean isAdmin(HttpServletRequest request){
        if (!isLongin(request)){
            return false;
        }
        User loginUser = getLoginUser(request);
        if (loginUser.getRole()!=ADMIN_ROLE)return false;
        return true;
    }
    public static User getLoginUser(HttpServletRequest request){
        User loginUser = (User)request.getSession().getAttribute(LOGIN_USER);
        return loginUser;
    }
    public static boolean isLongin(HttpServletRequest request){
       if (getLoginUser(request)==null){
           return false;
       }
       return true;
    }
}
