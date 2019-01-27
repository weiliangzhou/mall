package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class ClassListenCount {
    private Integer id;

    private Integer listenCount;

    private Integer classId;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;

}