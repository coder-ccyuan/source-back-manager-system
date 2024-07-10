package com.cpy.source_back_manager.controller;

import cn.hutool.core.bean.BeanUtil;
import com.cpy.source_back_manager.common.BaseResponse;
import com.cpy.source_back_manager.common.DeleteRequest;
import com.cpy.source_back_manager.common.ErrorCode;
import com.cpy.source_back_manager.common.ResultUtils;
import com.cpy.source_back_manager.constant.UserConstant;
import com.cpy.source_back_manager.exception.BusinessException;
import com.cpy.source_back_manager.model.dto.user.*;
import com.cpy.source_back_manager.model.entity.User;
import com.cpy.source_back_manager.model.vo.UserVo;
import com.cpy.source_back_manager.service.UserService;
import com.cpy.source_back_manager.utils.UserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:成希德
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public BaseResponse<UserVo> login(@RequestBody UserLoginRequest request,HttpServletRequest httpServletRequest) {
        if (request == null)
            new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        UserVo userVo = userService.userLogin(request.getUsername(), request.getPassword(),httpServletRequest);
        return ResultUtils.success(userVo);
    }

    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody UserRegisterRequest request) {
        if (request == null)
            new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        Boolean aBoolean = userService.userRegister(request.getUsername(), request.getPassword(), request.getCheckPassword());
        return ResultUtils.success(aBoolean);
    }

    @GetMapping("/loginUser")
    public BaseResponse<UserVo> loginUser(HttpServletRequest request) {
        if (request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        return ResultUtils.success(userService.getLoginUser(request));
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody UserUpdateRequest updateRequest, HttpServletRequest request) {
        if (updateRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        User user = BeanUtil.toBean(updateRequest, User.class);
        user.setId(UserUtils.getLoginUser(request).getId());
        boolean b = userService.updateById(user);
        if (!b) new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        return ResultUtils.success(b);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        request.getSession().setAttribute(UserConstant.LOGIN_USER, null);
        return ResultUtils.success(true);
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        //鉴权
        if (!UserUtils.isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        boolean b = userService.removeById(deleteRequest.getId());
        if (!b) new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        return ResultUtils.success(b);
    }

    @PostMapping("/query")
    public BaseResponse<List<UserVo>> query(@RequestBody UserQueryRequest queryRequest) {
        if (queryRequest == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        return ResultUtils.success(userService.userQuery(queryRequest));
    }

    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody UserAddRequest addRequest, HttpServletRequest request) {
        if (addRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        if (UserUtils.getLoginUser(request).getRole()!=UserConstant.ADMIN_ROLE)
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        userService.addUser(addRequest);
        return ResultUtils.success(true);
    }
}
