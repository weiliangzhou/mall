package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityMapper;
import com.zwl.model.po.OfflineActivity;
import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.OfflineActivityTheme;
import com.zwl.service.OfflineActivityOrderService;
import com.zwl.service.OfflineActivityService;
import com.zwl.service.OfflineActivityThemeService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zwl.util.BigDecimalUtil.div;

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
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;

    @Override
    public OfflineActivity getOneByActivityId(Integer activityId) {
        return offlineActivityMapper.selectByPrimaryKey(activityId);
    }

    @Override
    public OfflineActivity getOneByActivityIdAndCheckTime(Integer activityId) {
        return offlineActivityMapper.getOneByActivityIdAndCheckTime(activityId);
    }

    @Override
    public List<OfflineActivity> getOfflineActivityListByThemeId(String merchantId, Integer activityThemeId, String userId) {
        List<OfflineActivity> offlineActivityList = offlineActivityMapper.getOfflineActivityListByThemeId(merchantId,activityThemeId);
        List<OfflineActivityOrder> offlineActivityOrderList = offlineActivityOrderService.findOrderByUserIdAndThemeId(userId,activityThemeId,merchantId);
        if(null == offlineActivityOrderList || offlineActivityOrderList.isEmpty()){
            for(OfflineActivity offlineActivity:offlineActivityList){
                offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getActivityPrice(),2)+"");
                if(null !=offlineActivity.getRetrainingPrice()){
                    offlineActivity.setRetrainingPriceDesc(div(100,offlineActivity.getRetrainingPrice(),2)+"");
                }else {
                    offlineActivity.setRetrainingPriceDesc("");
                }
                OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId,offlineActivity.getActivityThemeId());
                offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress());
            }
        }else{
            for(OfflineActivity offlineActivity:offlineActivityList){
                OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(merchantId,offlineActivity.getActivityThemeId());
                if(null !=offlineActivity.getRetrainingPrice()){
                    offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getActivityPrice(),2)+"");
                    offlineActivity.setRetrainingPriceDesc(div(100,offlineActivity.getRetrainingPrice(),2)+"");
                    offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                    offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                    offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress()+"-复训");
                }else {
                    offlineActivity.setActivityPriceDesc(div(100,offlineActivity.getActivityPrice(),2)+"");
                    offlineActivity.setRetrainingPriceDesc("");
                    offlineActivity.setImgUrl(offlineActivityTheme.getImgUrl());
                    offlineActivity.setThemeName(offlineActivityTheme.getThemeName());
                    offlineActivity.setActivityAddressDesc(offlineActivity.getActivityAddress());
                }
            }
        }
        return offlineActivityList;
    }

    @Override
    public void updateBuyCountById(Integer activityId) {
        offlineActivityMapper.updateBuyCountById(activityId);
    }
}
