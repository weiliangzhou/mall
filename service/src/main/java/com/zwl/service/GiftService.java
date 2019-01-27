package com.zwl.service;

import com.zwl.model.po.Gift;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: GiftService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3111:33
 */
public interface GiftService {
    List getGiftList(Integer queryType, String merchantId);

    Gift getGiftDetailById(Long giftId);

    /**
     * 更新库存数量
     *
     * @param giftId       商品编号
     * @param stock        原有库存
     * @param currentStock 现有库存
     * @return 是否更新库存成功
     */
    Boolean updateGiftStock(Long giftId, Integer stock, Integer currentStock);

    /**
     * 更新商品销量
     *
     * @param giftId   商品编号
     * @param buyCount 新增销量
     * @return 更新销量是否成功
     */
    Boolean updateGiftBuyCount(Long giftId, Integer buyCount);
}
