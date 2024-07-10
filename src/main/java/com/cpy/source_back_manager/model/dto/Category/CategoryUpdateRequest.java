package com.cpy.source_back_manager.model.dto.Category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author:成希德
 */
@Data
public class CategoryUpdateRequest {
    /**
     * 文章分类id
     */
    private Integer id;
    /**
     * 文章分类名称
     */
    private String cateName;

    /**
     * 文章分类别名
     */
    private String cateAlias;

    /**
     * 文章分类描述
     */
    private String desc;
}
