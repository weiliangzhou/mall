package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.terran4j.commons.restpack.RestPackIgnore;
import com.zwl.util.BigDecimalUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Gift {
    private Long id;
    @ApiComment(value = "主标题", sample = "主标题")
    private String giftMainTitle;
    @ApiComment(value = "副标题", sample = "副标题")
    private String giftViceTitle;
    @ApiComment(value = "最低要求等级", sample = "最低要求等级")
    private Integer minRequirement;
    @ApiComment(value = "产品主图", sample = "产品主图")
    private String giftMainImg;
    @ApiComment(value = "产品副图1", sample = "产品副图")
    private String giftViceImg1;
    @ApiComment(value = "产品副图2", sample = "产品副图")
    private String giftViceImg2;
    @ApiComment(value = "产品副图3", sample = "产品副图")
    private String giftViceImg3;
    @ApiComment(value = "价格(分)", sample = "12")
    private Integer price;
    @ApiComment(value = "价格（元）", sample = "12")
    private String priceDesc;

    public String getPriceDesc() {
        return String.valueOf(BigDecimalUtil.div(100, this.price, 2));
    }

    @ApiComment(value = "快递费(分)", sample = "12")
    private Integer expressFee;
    @ApiComment(value = "快递费(元)", sample = "12")
    private String expressFeeDesc;

    public String getExpressFeeDesc() {
        return String.valueOf(BigDecimalUtil.div(100, this.expressFee, 2));
    }

    @ApiComment(value = "库存", sample = "12")
    private Integer stock;
    @ApiComment(value = "简介", sample = "12")
    private String giftDesc;
    @ApiComment(value = "是否推荐", sample = "0不推荐 1推荐")
    private Integer isRecommend;
    @ApiComment(value = "是否发布", sample = "0不发布 1发布")
    private Integer isShow;
    @ApiComment(value = "商户号", sample = "123123")
    private String merchantId;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "创建时间", sample = "2018-07-05 18:00:00")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiComment(value = "更新时间", sample = "2018-07-05 18:00:00")
    private Date modifyTime;
    @JSONField(serialize = false)
    @RestPackIgnore
    private Integer available;
    @ApiComment(value = "轮播图", sample = "轮播图")
    private List<String> imgList;
    @ApiComment(value = "销量", sample = "12")
    private Integer buyCount;
    @ApiComment(value = "当前用户是否已经购买", sample = "0未购买 1已购买")
    private Integer buyFlag;


}