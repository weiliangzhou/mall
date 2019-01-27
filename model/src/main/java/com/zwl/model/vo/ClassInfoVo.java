package com.zwl.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 节课程信息详情vo
 */
@Data
public class ClassInfoVo {
    private Long id;

    private String audioUrl;

    private String logoUrl;

    private String title;

    private String content;

    private Date modifyTime;
}
