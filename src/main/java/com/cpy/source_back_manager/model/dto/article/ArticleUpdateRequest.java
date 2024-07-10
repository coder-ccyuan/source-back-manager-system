package com.cpy.source_back_manager.model.dto.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author:成希德
 */
@Data
public class ArticleUpdateRequest {
    /**
     * 文章id
     */
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章封面地址
     */
    private String coverImg;

    /**
     * 文章状态['0草稿','1发布','2删除']
     */
    private Integer state;

}
