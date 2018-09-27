package com.zwl.model.po;

import lombok.Data;

import java.util.Date;
@Data
public class OfflineActivity {
    private Integer id;
    private String activityAddress;
    private Date activityStartTime;
    private Date activityEndTime;
    private Integer activityPrice;
    private Integer buyCount;
    private Integer limitCount;
    private Integer isRecommend;
    private Integer isShow;
    private Integer themeId;
    private String merchantId;
    private Date createTime;
    private Date modifyTime;
    private Integer available;
}