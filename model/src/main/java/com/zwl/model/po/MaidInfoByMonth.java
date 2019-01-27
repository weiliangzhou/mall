package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class MaidInfoByMonth {
    private Long id;
    private String userId;
    @ApiComment(value = "分佣金额", sample = "12312")
    private Integer maidMoney;
    private Integer maidPercent;
    @ApiComment(value = "总业绩", sample = "12312")
    private Integer totalPerformance;
    @ApiComment(value = "分佣类型", sample = "0团队奖 1纵向奖")
    private Integer maidType;
    @ApiComment(value = "商户号", sample = "12321312")
    private String merchantId;
    @JSONField(format = "yyyy-MM-dd")
    @ApiComment(value = "记录时间", sample = "2018-07-05")
    private Date recordTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime = new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    private Integer available = 1;

}