package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityTheme {
    @ApiComment(value = "ID", sample = "1")
    private Integer id;
    @ApiComment(value = "活动名称", sample = "微商夜大201809期")
    private String themeName;
    @ApiComment(value = "图片地址或者视频地址", sample = "12312312")
    private String themeHrefUrl;
    @ApiComment(value = "类型 0图片 1视频", sample = "12312312")
    private Integer themeType;
    @ApiComment(value = "带格式简介", sample = "12312312")
    private String content;
    @ApiComment(value = "不带格式简介", sample = "12312312")
    private String contentText;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isRecommend;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isShow;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer merchantId;
    @ApiComment(value = "创建时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiComment(value = "更新时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;

}