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

    private Integer giftId;

    private Integer giftTitle;

    private String phone;

    private String province;

    private String city;

    private String area;

    private String address;

    private String expressNo;

    private Integer expressCompany;

    private String merchantId;

    private Integer orderState;

    private Date createTime;

    private Date modifyTime;

    private Integer available;
}