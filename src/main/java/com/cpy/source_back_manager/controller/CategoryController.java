package com.cpy.source_back_manager.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpy.source_back_manager.common.BaseResponse;
import com.cpy.source_back_manager.common.DeleteRequest;
import com.cpy.source_back_manager.common.ErrorCode;
import com.cpy.source_back_manager.common.ResultUtils;
import com.cpy.source_back_manager.exception.BusinessException;
import com.cpy.source_back_manager.model.dto.Category.CategoryAddRequest;
import com.cpy.source_back_manager.model.dto.Category.CategoryQueryRequest;
import com.cpy.source_back_manager.model.dto.Category.CategoryUpdateRequest;
import com.cpy.source_back_manager.model.entity.Category;
import com.cpy.source_back_manager.service.CategoryService;
import com.cpy.source_back_manager.utils.UserUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:成希德
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    CategoryService categoryService;

    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody CategoryUpdateRequest updateRequest, HttpServletRequest request) {
        if (updateRequest==null||request==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        Category category = BeanUtil.toBean(updateRequest, Category.class);
        //验证是否为管理员
        if (!UserUtils.isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"没有权限");
        //验证是否存在
        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("id",updateRequest.getId());
        Long aLong = categoryService.getBaseMapper().selectCount(categoryQueryWrapper);
        if (aLong!=1)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        boolean b = categoryService.updateById(category);
        if (!b)new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        return ResultUtils.success(b);
    }
    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest==null|| request==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        //鉴权
        if (!UserUtils.isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        boolean b = categoryService.removeById(deleteRequest.getId());
        if (!b)new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        return ResultUtils.success(b);
    }
    @PostMapping("/query")
    public BaseResponse<List<Category>> query(@RequestBody CategoryQueryRequest queryRequest) {
        if (queryRequest==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        return ResultUtils.success(categoryService.categoryQuery(queryRequest));
    }

    /**
     * 发布
     * @param addRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody CategoryAddRequest addRequest , HttpServletRequest request){
        if (addRequest==null||request==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        //验证是否为管理员
        if (!UserUtils.isAdmin(request))
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"没有权限");
        boolean b = categoryService.addCategory(addRequest, request);
        return ResultUtils.success(b);
    }
}
