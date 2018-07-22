package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class ClassExpand {
    private Integer id;

    private Integer fansCount;

    private Integer shareCount;

    private Integer listenCount;

    private Integer typeId;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;


}