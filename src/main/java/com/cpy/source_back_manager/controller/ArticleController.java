package com.cpy.source_back_manager.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cpy.source_back_manager.common.BaseResponse;
import com.cpy.source_back_manager.common.DeleteRequest;
import com.cpy.source_back_manager.common.ErrorCode;
import com.cpy.source_back_manager.common.ResultUtils;
import com.cpy.source_back_manager.exception.BusinessException;
import com.cpy.source_back_manager.model.dto.article.ArticleAddRequest;
import com.cpy.source_back_manager.model.dto.article.ArticleQueryRequest;
import com.cpy.source_back_manager.model.dto.article.ArticleUpdateRequest;
import com.cpy.source_back_manager.model.entity.Article;
import com.cpy.source_back_manager.model.entity.User;
import com.cpy.source_back_manager.model.vo.ArticleVo;
import com.cpy.source_back_manager.service.ArticleService;
import com.cpy.source_back_manager.service.UserService;
import com.cpy.source_back_manager.utils.UserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:成希德
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;
    @Resource
    UserService userService;

    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody ArticleUpdateRequest updateRequest, HttpServletRequest request) {
        if (updateRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        Article article = BeanUtil.toBean(updateRequest, Article.class);
        //验证是否为本人
        Integer userId = article.getAuthorId();
        if (userId != UserUtils.getLoginUser(request).getId())
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "没有权限");
        //验证是否存在
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", updateRequest.getId()).eq("id", userId);
        Long aLong = articleService.getBaseMapper().selectCount(articleQueryWrapper);
        if (aLong != 1)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        boolean b = articleService.updateById(article);
        if (!b) new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        return ResultUtils.success(b);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        //鉴权
        Article article = articleService.getById(deleteRequest.getId());
        if (article.getAuthorId() != UserUtils.getLoginUser(request).getId())
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        boolean b = articleService.removeById(deleteRequest.getId());
        if (!b) new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        return ResultUtils.success(b);
    }

    @PostMapping("/query")
    public BaseResponse<List<ArticleVo>> query(@RequestBody ArticleQueryRequest queryRequest) {
        if (queryRequest == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        return ResultUtils.success(articleService.articleQuery(queryRequest));
    }

    /**
     * 发布
     *
     * @param addRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> add(@RequestBody ArticleAddRequest addRequest, HttpServletRequest request) {
        if (addRequest == null || request == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        if (!UserUtils.isLongin(request)) throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        boolean b = articleService.addArticle(addRequest, request);
        return ResultUtils.success(b);
    }

    @GetMapping("/getByAuthorId")
    public BaseResponse<List<ArticleVo>> getByAuthorId(HttpServletRequest request) {
        if (request == null) throw new BusinessException(ErrorCode.PARAMS_ERROR);
        User loginUser = UserUtils.getLoginUser(request);
        Integer id = loginUser.getId();
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("author_id", id);
        List<Article> list = articleService.list(articleQueryWrapper);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : list) {
            Integer authorId = article.getAuthorId();
            User user = userService.getById(authorId);
            ArticleVo articleVo = BeanUtil.copyProperties(article, ArticleVo.class);
            articleVo.setAuthorNickname(user.getNickname());
            articleVo.setAuthorPic(user.getUserPic());
            articleVoList.add(articleVo);
        }
        return ResultUtils.success(articleVoList);
    }
}
