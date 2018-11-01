package com.zwl.service;

import com.github.pagehelper.PageInfo;
import com.zwl.model.po.UserGift;

/**
 * @author houyuhui
 */
public interface UserGiftService {
    /**
     * 用户兑换商品
     *
     * @param userId     用户编号
     * @param merchantId 商户号
     * @param giftId     礼品编号
     * @param addressId  收货地址
     * @return 兑换的礼品信息
     */
    UserGift addUserExchangeGift(String userId, String merchantId, Long giftId, Long addressId);

    /**
     * 添加用户礼品信息
     *
     * @param userGift 用户礼品
     */
    UserGift addUserGift(UserGift userGift);

    /**
     * 分页查询用户兑换的礼品列表
     *
     * @param userId     用户标识
     * @param merchantId 商户号
     * @param pageSize   每页大小
     * @param pageNum    第几页
     * @return 分页数据信息
     */
    PageInfo<UserGift> findUserGiftListPage(String userId, String merchantId, Integer pageSize, Integer pageNum);
}
