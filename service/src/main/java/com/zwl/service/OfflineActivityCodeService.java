package com.zwl.service;

import com.zwl.model.po.OfflineActivityCode;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivity
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:54
 */
public interface OfflineActivityCodeService {
    OfflineActivityCode getOneByActivityCode(String activityCode);

    void updatePassByActivityCode(String activityCode);

    OfflineActivityCode getOneByUserIdAndOfflineActivityId(String userId, Integer offlineActivityId);

    Integer getBuyCountByUserIdAndThemeId(String userId, Integer themeId);

    void insert(OfflineActivityCode offlineActivityCode);

    Integer getAlreadyBuyCountByUserIdAndThemeId(String userId, Integer activityThemeId, String merchantId);
}
