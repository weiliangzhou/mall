package com.zwl.service;

import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.OfflineActivityOrderVo;

import java.util.Date;
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

    List<OfflineActivityOrderVo> getActivityOrderListByUserId(String userId, String merchantId);

    void updateStatusByOrderNo(String out_trade_no, String payment_no, String paymentTime);

    OfflineActivityOrderVo findOrderDetailByOrderNo(String orderNo, String userId);

    /**
     * 根据线下活动时间查询订单
     *
     * @param userId       用户订单
     * @param activityDate 线下活动 时间
     */
    OfflineActivityOrder getOfflineActivityOrderByActivityDate(String userId, Date activityDate);

    /**
     *
     * @param userId     用户id
     * @param merchantId 商户号
     * @return
     */
    List<OfflineActivityOrderVo> getMySLActivityOrderList(String userId, String merchantId, String activityThemeId);

    /**
     *
     * @param orderNo     订单id
     * @return
     */
    OfflineActivityOrderVo getSLActivityOrderDetail(String orderNo);


}
