package com.cpy.source_back_manager.model.dto.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author:成希德
 */
@Data
public class ArticleAddRequest {
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
     * 文章创建时间
     */
    private Date createTime;

    /**
     * 文章更新时间
     */
    private Date updateTime;

    /**
     * 文章状态['0草稿','1发布','2删除']
     */
    private Integer state;

    /**
     * 文章分类id
     */
    private Integer cateId;

}
