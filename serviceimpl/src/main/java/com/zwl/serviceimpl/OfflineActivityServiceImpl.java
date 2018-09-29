package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityMapper;
import com.zwl.model.po.OfflineActivity;
import com.zwl.service.OfflineActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2713:44
 */
@Service
public class OfflineActivityServiceImpl implements OfflineActivityService {
    @Autowired
    private OfflineActivityMapper offlineActivityMapper;

    @Override
    public OfflineActivity getOneByActivityId(Integer activityId) {
        return offlineActivityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public OfflineActivity getOneByActivityIdAndCheckTime(Integer activityId) {
        return offlineActivityMapper.getOneByActivityIdAndCheckTime(activityId);
    }

    @Override
    public List<OfflineActivity> getOfflineActivityListByThemeId(String merchantId, String themeId) {
        return offlineActivityMapper.getOfflineActivityListByThemeId(merchantId,themeId);
    }
}
