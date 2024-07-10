package com.cpy.source_back_manager.model.dto.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author:成希德
 */
@Data
public class ArticleQueryRequest {
    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章创建时间
     */
    private Date createTime;

    /**
     * 文章更新时间
     */
    private Date updateTime;

}
