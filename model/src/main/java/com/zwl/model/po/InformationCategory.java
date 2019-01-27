package com.zwl.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class InformationCategory {
    private Integer id;

    private Integer isShow;

    private String merchantId;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;


}