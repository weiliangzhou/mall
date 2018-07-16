package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.model.groups.QueryMaidInfo;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class MaidInfo {
    @RestPackIgnore
    @JSONField(serialize = false)
    private Long id;
    @NotBlank(message = "订单号不能为空", groups = {Update.class})
    @ApiComment(value = "订单号", sample = "201807101142dy1")
    private String orderNo;
    @NotBlank(message = "userid不能为空", groups = {Update.class, QueryMaidInfo.class})
    @ApiComment(value = "userid", sample = "asfsdfdsf5615")
    private String userId;
    @NotNull(message = "分佣金额不能为空", groups = {Update.class})
    @ApiComment(value = "分佣金额", sample = "250000")
    private Integer maidMoney;
    @NotNull(message = "分佣百分比不能为空", groups = {Update.class})
    @ApiComment(value = "分佣百分比", sample = "50")
    private Integer maidPercent;
    @NotNull(message = "实付订单金额不能为空", groups = {Update.class})
    @ApiComment(value = "实付订单金额", sample = "500000")
    private Integer orderActualMoney;
//    @NotBlank(message = "渠道ID不能为空", groups = {QueryMaidInfo.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @NotNull(message = "产品Id不能为空", groups = {Update.class})
    @ApiComment(value = "产品Id", sample = "1")
    private Long productId;
    @NotBlank(message = "产品名称不能为空", groups = {Update.class})
    @ApiComment(value = "产品名称", sample = "套餐A")
    private String productName;
    @NotNull(message = "等级不能为空", groups = {Update.class})
    @ApiComment(value = "等级", sample = "1")
    private Integer level;
    @NotBlank(message = "等级名称不能为空", groups = {QueryMaidInfo.class})
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;


}