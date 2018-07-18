package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

/**
 * 套课程
 */
@Data
public class ClassSet {
    private Long id;

    private String title;

    private String bannerUrl;

    private Integer categoryId;

    private String merchantId;

    private Integer isShow;

    private Integer requiredMemberLevel;

    private Date createTime;

    private Date modifyTime;

    private Integer available;

    private String content;

}