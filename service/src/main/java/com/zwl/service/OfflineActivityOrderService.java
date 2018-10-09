package com.zwl.service;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityOrderService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:08
 */
public interface OfflineActivityOrderService {
    BuyResult offlineActivityBuy(OfflineActivityBuy offlineActivityBuy);

    OfflineActivityOrder findOrderByOrderNo(String orderNo);

    OfflineActivityOrder findOrderByActivityCode(String activityCode);

    List<OfflineActivityOrder> findOrderByUserId(String userId, String merchantId);
}
