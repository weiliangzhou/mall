package com.zwl.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 套课和单独的节课程列表vo
 */
@Data
public class ClassVo {
    private Long id;
    private String title;
    private String categoryTitle;
    private Date modifyTime;
    private String merchantId;
    private Integer classType;

}
