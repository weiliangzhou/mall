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
}
