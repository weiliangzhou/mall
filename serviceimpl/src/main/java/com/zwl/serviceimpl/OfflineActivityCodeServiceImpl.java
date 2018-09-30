package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityCodeMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.OfflineActivity;
import com.zwl.model.po.OfflineActivityCode;
import com.zwl.service.OfflineActivityCodeService;
import com.zwl.service.OfflineActivityService;
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
public class OfflineActivityCodeServiceImpl implements OfflineActivityCodeService {
    @Autowired
    private OfflineActivityCodeMapper offlineActivityCodeMapper;
    @Autowired
    private OfflineActivityService offlineActivityService;

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
        return offlineActivityCodeMapper.getOneByUserIdAndOfflineActivityId(userId,offlineActivityId);
    }

    @Override
    public Integer getBuyCountByUserIdAndThemeId(String userId, Integer themeId) {
        return offlineActivityCodeMapper.getBuyCountByUserIdAndThemeId(userId,themeId);
    }
}
