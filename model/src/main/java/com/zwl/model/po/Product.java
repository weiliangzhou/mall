package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.model.groups.Buy;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Product {
    @NotNull(message = "产品ID不能为空", groups = {Update.class, Buy.class})
    @ApiComment(value = "产品ID", sample = "1")
    private Long id;
    @ApiComment(value = "会员等级", sample = "1")
    private Integer level;
    @NotBlank(message = "等级名称不能为空", groups = {Update.class})
    @ApiComment(value = "等级名称", sample = "院长")
    private String levelName;
    @NotBlank(message = "产品名称不能为空", groups = {Update.class})
    @ApiComment(value = "产品名称", sample = "套餐a")
    private String productName;
    @NotNull(message = "分佣百分比不能为空", groups = {Update.class})
    @ApiComment(value = "分佣百分比", sample = "30")
    private Integer maidPercent;
    @NotNull(message = "时效不能为空", groups = {Update.class})
    @ApiComment(value = "时效（天为单位）", sample = "50")
    private Integer validityTime;
    @NotNull(message = "产品价格不能为空", groups = {Update.class})
    @ApiComment(value = "产品价格", sample = "99元为单位")
    private Integer price;
    private String priceDesc;
    @NotBlank(message = "商户号不能为空", groups = {Buy.class})
    @JSONField(serialize = false)
    @RestPackIgnore
    private String merchantId;
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @RestPackIgnore
    @JSONField(serialize = false)
    private Integer available;
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "userID不能为空", groups = {Buy.class})
    private String userId;
    //    微信H5支付终端ip
    @RestPackIgnore
    @JSONField(serialize = false)
    @NotBlank(message = "微信H5支付终端ip不能为空")
    private String spbillCreateIp;
    public String getPriceDesc() {
        return this.price/100+"";
    }

}