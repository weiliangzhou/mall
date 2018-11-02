package com.zwl.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户兑换商品
 *
 * @author houyuhui
 */
@Data
public class UserGift {
    private Long id;

    private String userId;

    /**
     * 礼品编号
     */
    private Long giftId;
    /**
     * 价格(分)
     */
    private Integer price;
    /**
     * 礼品展示图
     */
    private String giftMainImg;
    /**
     * 标题
     */
    private String giftTitle;

    /**
     * 号码
     */
    private String phone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递公司 1韵达 2圆通 3EMS 4申通
     */
    private Integer expressCompany;

    private String merchantId;

    /**
     * 订单状态 0:待发货 1:已发货
     */
    private Integer orderState;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 返回商品购买等级对应的商品编号
     */
    private Long productId;

    private Date modifyTime;

    private Integer available;
}