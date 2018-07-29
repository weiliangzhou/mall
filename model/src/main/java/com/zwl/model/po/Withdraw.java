package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.model.groups.ApplyWithdraw;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Withdraw {
    @RestPackIgnore
    @JSONField(serialize = false)
    private Long id;
    @NotNull(message = "提现金额不能为空", groups = {ApplyWithdraw.class})
    @ApiComment(value = "金额", sample = "1000")
    private Integer money;
    private Integer moneyDesc;
    @NotBlank(message = "userId不能为空", groups = {ApplyWithdraw.class})
    @ApiComment(value = "userId", sample = "admin")
    private String userId;
    @ApiComment(value = "id", sample = "1")
    private String withdrawId;
    @RestPackIgnore
    @JSONField(serialize = false)
    private String openId;
    @ApiComment(value = "真实姓名", sample = "二师兄")
    private String realName;
    @NotBlank(message = "收款账号不能为空", groups = {ApplyWithdraw.class})
    @ApiComment(value = "收款账号", sample = "139****0001")
    private String account;
//    @ApiComment(value = "状态", sample = " 0审核中 1审核通过(前端显示到款中) 2未通过  3成功")
    @ApiComment(value = "状态", sample = " 0未提交 1 审核中 2审核通过(前端显示到款中) 3未通过 4成功（到账）")
    private Integer status;
    @NotNull(message = "收款方式不能为空", groups = {ApplyWithdraw.class})
    @ApiComment(value = "收款方式", sample = "微信1")
    private Integer payWay;
    @ApiComment(value = "操作人", sample = "马云")
    private String operator;
    @ApiComment(value = "审核意见", sample = "通过")
    private String remark;
    @ApiComment(value = "审核通过时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date successTime;
    @ApiComment(value = "微信支付订单号", sample = "201808080910")
    private String paymentNo;
    @ApiComment(value = "微信支付成功时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime = new Date();
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available = 1;
    @NotBlank(message = "商户号不能为空", groups = {ApplyWithdraw.class})
    @ApiComment(value = "商户号", sample = "dy")
    private String merchantId;

    public String getMoneyDesc() {
        return this.money / 100 + "";
    }


}