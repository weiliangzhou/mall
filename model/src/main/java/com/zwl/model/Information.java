package com.zwl.model;

import lombok.Data;

import java.util.Date;

@Data
public class Information {
    private Integer id;
    private String informationId;
    private String merchantId;
    private String url;
    //是否发布状态 默认0不发布 1发布
    private Integer isShow;
    private String title;
    private String logoUrl;
    //    音频地址
    private String audioUrl;
    private String content;
    private Date createTime;
    private Date modifyTime;
    private Integer available;


}