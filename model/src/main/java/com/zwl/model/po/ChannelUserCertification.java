package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class ChannelUserCertification {
    private Long id;

    private String channelUserId;

    private Integer status;

    private String operator;

    private String realname;

    private String idCard;

    private String img1Url;

    private String img2Url;

    private String img3Url;

    private Date createTime;

    private Date modifyTime;

    private Integer available = 1;


}