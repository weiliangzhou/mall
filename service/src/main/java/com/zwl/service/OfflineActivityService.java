package com.zwl.service;

import com.zwl.model.po.OfflineActivity;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2713:40
 */
public interface OfflineActivityService {
    OfflineActivity getOneByActivityId(Integer activityId);

    /**
     * 判断当前活动是否到期
     * @param activityId
     * @return
     */
    OfflineActivity getOneByActivityIdAndCheckTime(Integer activityId);

    /**
     * 根据活动主题获取详情
     * @param merchantId
     * @param activityThemeId
     * @return
     */
    List<OfflineActivity> getOfflineActivityListByThemeId(String merchantId, Integer activityThemeId, String userId);

    /**
     * 根据活动id更新已购买人数
     * @param activityId
     */
    void updateBuyCountById(Integer activityId);

    /**
     * 根据沙龙主题id查询该场沙龙
     * @param merchantId
     * @param themeId
     * @return
     */
    OfflineActivity getOfflineActivityByThemeId(String merchantId, Integer themeId);
}
