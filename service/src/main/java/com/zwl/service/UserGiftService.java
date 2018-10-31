package com.zwl.service;

import com.zwl.model.po.UserGift;

/**
 * @author houyuhui
 */
public interface UserGiftService {
    /**
     * 用户兑换商品
     *
     * @param userId    用户编号
     * @param giftId    礼品编号
     * @param addressId 收货地址
     * @return 兑换的礼品信息
     */
    UserGift addUserExhangeGift(String userId, Integer giftId, Integer addressId);

    /**
     * 添加用户礼品信息
     *
     * @param userGift 用户礼品
     */
    UserGift addUserGift(UserGift userGift);
}
