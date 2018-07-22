package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ClassType {
    private Integer id;

    private String title;

    private String banner;

    private String content;

    private Integer categoryId;

    private Integer show;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;


}