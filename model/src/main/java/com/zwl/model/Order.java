package com.zwl.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import lombok.Data;

import java.util.Date;
@Data
public class Order {
    @ApiComment(value = "订单ID", sample = "201807091123zwl")
    private String orderNo;
    @ApiComment(value = "产品ID", sample = "1")
    private Long productId;
    @ApiComment(value = "产品名称", sample = "套餐A")
    private String productName;
    @ApiComment(value = "实际付款金额", sample = "98000")
    private Integer actualMoney;
    @ApiComment(value = "订单金额", sample = "98000")
    private Integer money;
    @ApiComment(value = "分佣百分比", sample = "50")
    private Integer maidPercent;
    @ApiComment(value = "支付方式", sample = "1")
    private Integer payWay;
    @ApiComment(value = "产品等级", sample = "1")
    private Integer level;
    @ApiComment(value = "等级名称", sample = "学员")
    private String levelName;
    @ApiComment(value = "有效期", sample = "365")
    private Integer validityTime;
    @ApiComment(value = "订单状态", sample = "订单状态  0待支付 1成功  -1超时关闭")
    private Integer orderStatus;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String userId;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String merchantId;
    @ApiComment(value = "微信订单号", sample = "wx1564646831")
    private String paymentNo;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "支付成功时间", sample = "2018-07-05 18:00:00")
    private Date paymentTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime=new Date();
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
}