package com.zwl.model.po;

import com.terran4j.commons.api2doc.annotations.ApiComment;
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

    private Integer available = 1;

    private String content;
    @ApiComment(value = "不带格式介绍", sample = "不带格式介绍")
    private String contentText;

}