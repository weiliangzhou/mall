package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityCodeMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.OfflineActivityCode;
import com.zwl.service.OfflineActivityCodeService;
import com.zwl.service.OfflineActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2710:56
 */
@Service
@Slf4j
public class OfflineActivityCodeServiceImpl implements OfflineActivityCodeService {
    @Autowired
    private OfflineActivityCodeMapper offlineActivityCodeMapper;

    @Override
    public OfflineActivityCode getOneByActivityCode(String activityCode) {
        return offlineActivityCodeMapper.getOneByActivityCode(activityCode);
    }

    @Override
    public void updatePassByActivityCode(String activityCode) {
        int count = offlineActivityCodeMapper.updatePassByActivityCode(activityCode);
        if (count != 1)
            BSUtil.isTrue(false, "签到错误，请重试！");
    }

    @Override
    public OfflineActivityCode getOneByUserIdAndOfflineActivityId(String userId, Integer offlineActivityId) {
        return offlineActivityCodeMapper.getOneByUserIdAndOfflineActivityId(userId, offlineActivityId);
    }

    @Override
    public Integer getBuyCountByUserIdAndThemeId(String userId, Integer themeId) {
        return offlineActivityCodeMapper.getBuyCountByUserIdAndThemeId(userId, themeId);
    }

    @Override
    public void insert(OfflineActivityCode offlineActivityCode) {
        int count = offlineActivityCodeMapper.insertSelective(offlineActivityCode);
        if (count == 0) {
            log.info("支付回调，生成code失败" + offlineActivityCode);
        }


    }

    @Override
    public Integer getAlreadyBuyCountByUserIdAndThemeId(String userId, Integer activityThemeId, String merchantId) {
        return offlineActivityCodeMapper.getAlreadyBuyCountByUserIdAndThemeId(userId,activityThemeId,merchantId);
    }
}
