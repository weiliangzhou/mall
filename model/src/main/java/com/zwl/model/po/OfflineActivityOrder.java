package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class OfflineActivityOrder {
    @ApiComment(value = "订单号", sample = "xx201810091831150968804165432")
    private String orderNo;
    @ApiComment(value = "活动id", sample = "1")
    private Integer activityId;
    @ApiComment(value = "activityCode", sample = "f0f34e514fed4b83ae19ec855df6c0c3")
    private String activityCode;
    @ApiComment(value = "活动价格", sample = "100")
    private Integer activityPrice;
    @ApiComment(value = "实际支付价格", sample = "100")
    private Integer actualMoney;
    @ApiComment(value = "订单状态", sample = "0待支付 1支付成功 -1超时")
    private Integer orderStatus;
    @ApiComment(value = "用户id", sample = "3cd54fab3eff4168a7151e48ec389170")
    private String userId;
    @ApiComment(value = "性别", sample = "0男 1女")
    private Integer sex;
    @ApiComment(value = "手机号码", sample = "18258201865")
    private String phone;
    @ApiComment(value = "真实姓名", sample = "李小龙")
    private String realName;
    @ApiComment(value = "所在城市", sample = "杭州")
    private String city;
    @ApiComment(value = "身份证号码", sample = "330726199406141719")
    private String idCardNum;
    @ApiComment(value = "支付流水号", sample = "1111")
    private String paymentNo;
    @ApiComment(value = "支付时间", sample = "2018-10-10 13:37:54")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;
    @ApiComment(value = "商户号", sample = "1509688041")
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-10-09 18:31:31")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-10-09 18:31:31")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @ApiComment(value = "是否返佣，0不返佣，1返佣", sample = "1")
    private Integer isMaid;
    @ApiComment(value = "是否复训 0不是1是", sample = "1")
    private Integer isRetraining;
    @ApiComment(value = "主题id", sample = "1")
    private Integer activityThemeId;
    @ApiComment(value = "沙龙推荐人", sample = "线下沙龙推荐人的UUID")
    private String slReferrer;
    @ApiComment(value = "订单类型", sample = "订单类型 0:线下活动 1:线下沙龙")
    private Integer orderType;
    @ApiComment(value = "微信号", sample = "文本输入没什么意思")
    private String wechatNo;
}