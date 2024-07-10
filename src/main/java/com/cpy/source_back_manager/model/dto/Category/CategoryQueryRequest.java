package com.cpy.source_back_manager.model.dto.Category;

import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class CategoryQueryRequest {
    /**
     * 文章分类名称
     */
    private String cateName;

    /**
     * 文章分类别名
     */
    private String cateAlias;

}