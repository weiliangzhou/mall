package com.zwl.model.po;

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

    private String giftTitle;

    private String phone;

    private String province;

    private String city;

    private String area;

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

    private Date createTime;

    private Date modifyTime;

    private Integer available;
}