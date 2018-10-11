package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivity {
    @ApiComment(value = "ID", sample = "1")
    private Integer id;
    @ApiComment(value = "活动地点", sample = "义乌")
    private String activityAddress;
    @ApiComment(value = "活动开始时间", sample = "2018-09-29 11:00:00")
    @JSONField(format = "MM月dd日")
    private Date activityStartTime;
    @ApiComment(value = "活动结束时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "MM月dd日")
    private Date activityEndTime;
    @ApiComment(value = "活动价格", sample = "5000")
    private Integer activityPrice;
    @ApiComment(value = "活动类型 1980 20000", sample = "1980")
    private Integer activityType;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isRetraining;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer activityThemeId;
//    @RestPackIgnore
//    @JSONField(serialize = false)
//    private Integer activityParentId;
    @ApiComment(value = "容纳人数", sample = "100")
    private Integer limitCount;
    @ApiComment(value = "已购买人数", sample = "65")
    private Integer buyCount;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isRecommend;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isShow;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isRebuy;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer isMaid;
    @ApiComment(value = "最低购买等级", sample = "院长6")
    private Integer minRequirement;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiComment(value = "更新时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @ApiComment(value = "还剩多少名额", sample = "还剩多少名额")
    private String buyCountDesc;
    @ApiComment(value = "是否已满", sample = "0未满 1已满")
    private Integer isFull;
    @ApiComment(value = "报名开始时间", sample = "2018-09-29 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;
    @ApiComment(value = "报名结束时间", sample = "2018-09-30 11:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;
    @ApiComment(value = "复训价格", sample = "5000")
    private Integer retrainingPrice;
    @ApiComment(value = "活动价格", sample = "5000")
    private String activityPriceDesc;
    @ApiComment(value = "复训价格", sample = "5000")
    private String retrainingPriceDesc;
    @ApiComment(value = "logo图片", sample = "hangzhou.aliyuncs.com/upload/qrCodeImage/20181011/4fd769b161b542eaab4bf89371051f36.jpg")
    private String imgUrl;
    @ApiComment(value = "活动地点", sample = "义乌")
    private String activityAddressDesc;

    public String getBuyCountDesc() {
        Integer count = limitCount - buyCount;
        if (count == 0)
            return "名额已满";
        return "还剩" + count + "名额";
    }

    public Integer getIsFull() {
        Integer count = limitCount - buyCount;
        if (count == 0)
            return 1;
        return 0;
    }
}