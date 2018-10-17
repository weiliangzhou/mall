package com.zwl.service;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.OfflineActivityOrderVo;

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

    List<OfflineActivityOrderVo> findOrderByUserId(String userId, String merchantId);

    void updateStatusByOrderNo(String out_trade_no,String payment_no,String paymentTime);

    OfflineActivityOrderVo findOrderDetailByOrderNo(String orderNo);
}
