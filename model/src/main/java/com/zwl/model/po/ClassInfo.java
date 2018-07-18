package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

/**
 * 节课程
 */
@Data
public class ClassInfo {
    private Long id;

    private Long classSetId;

    private Long categoryId;

    private String merchantId;

    private String audioUrl;

    private String logoUrl;

    private String title;

    private String content;

    private Long listenCount;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

}