package com.zwl.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.OfflineActivityOrder;
import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityOrderVo extends OfflineActivityOrder {
    @ApiComment(value = "二维码地址", sample = "hangzhou.aliyuncs.com/upload/qrCodeImage/20181009/25cb3158843d42a4a528e72562c18143.jpg")
    private String qrCodeUrl;
    @ApiComment(value = "商品图片", sample = "hangzhou.aliyuncs.com/upload/qrCodeImage/20181009/25cb3158843d42a4a528e72562c18143.jpg")
    private String imgUrl;
    @ApiComment(value = "主题名称", sample = "微商夜大 20180920期")
    private String themeName;
    @ApiComment(value = "活动地点", sample = "义乌")
    private String activityAddress;
    @ApiComment(value = "0未使用1已使用", sample = "1")
    private Integer isUsed;
    @ApiComment(value = "活动开始时间", sample = "2018-09-29")
    @JSONField(format = "MM月dd日")
    private Date activityStartTime;
    @ApiComment(value = "活动结束时间", sample = "2018-09-30")
    @JSONField(format = "MM月dd日")
    private Date activityEndTime;
    @ApiComment(value = "数量", sample = "1")
    private Integer amount;
    @ApiComment(value = "创建时间", sample = "2018-09-30")
    private String createTimeDesc;
    @ApiComment(value = "活动价格", sample = "100")
    private String activityPriceDesc;
    @ApiComment(value = "沙龙邀约人姓名", sample = "张三")
    private String slReferrerName;
    @ApiComment(value = "沙龙邀约人电话", sample = "110")
    private String slReferrerPhone;
}
