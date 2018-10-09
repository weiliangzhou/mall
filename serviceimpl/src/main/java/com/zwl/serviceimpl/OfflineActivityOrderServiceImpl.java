package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityOrderMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.service.*;
import com.zwl.util.ThreadVariable;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 二师兄超级帅
 * @Title: OfflineActivityOrderServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2811:08
 */
@Service
@Slf4j
public class OfflineActivityOrderServiceImpl implements OfflineActivityOrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityOrderMapper offlineActivityOrderMapper;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;

    @Override
    public BuyResult offlineActivityBuy(OfflineActivityBuy offlineActivityBuy) {
        String userId = ThreadVariable.getUserID();
        Integer offlineActivityId = offlineActivityBuy.getActivityId();
        log.info("开始生成订单================================>userId::" + userId);
        User user = userService.getByUserId(userId);
        String merchantId = user.getMerchantId();
        Integer memberLevel = user.getMemberLevel();
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (merchant == null) {
            BSUtil.isTrue(Boolean.FALSE, "商户号不存在:" + user.getMerchantId());
        }
//        String appid = merchant.getAppId();
        //查询产品信息
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityId);
        //生成订单(订单号使用 年月日时分秒+mch_no+userId（自增的Id）)
        //生成订单操作日志流水表
        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        //获取userId的自增Id
        Long userLongId = user.getId();
        Integer productId = offlineActivity.getId();
        //根据购买规则 判断是否拥有购买权限
        //自然人 99 980 5000 校长
        Integer minRequirement = offlineActivity.getMinRequirement();
        if (minRequirement != null) {
            if (memberLevel < minRequirement)
                BSUtil.isTrue(false, "未获得购买线下课程的资格！");
        }
        Integer isRebuy = offlineActivity.getIsRebuy();
        //不能重复购买
        //则判断该用户是否购买过
        if (0 == isRebuy) {
            //通过userId  activityId
            OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByUserIdAndOfflineActivityId(userId, offlineActivityId);
            if (offlineActivityCode != null)
                BSUtil.isTrue(false, "不能重复购买！");
        }
        String orderNo = "xx" + sdf_yMdHm.format(new Date()) + merchantId + userLongId + productId;
        OfflineActivityOrder offlineActivityOrder = new OfflineActivityOrder();
        Integer activityId = offlineActivity.getId();
        Integer activityPrice = offlineActivity.getActivityPrice();
        offlineActivityOrder.setOrderNo(orderNo);
        offlineActivityOrder.setActivityId(activityId);
        //活动兑换码生成规则
        String activityCode = UUIDUtil.getUUID32();
        offlineActivityOrder.setActivityCode(activityCode);
        offlineActivityOrder.setActivityPrice(activityPrice);
        offlineActivityOrder.setUserId(userId);
        offlineActivityOrder.setPhone(offlineActivityBuy.getPhone());
        offlineActivityOrder.setCity(offlineActivityBuy.getCity());
        offlineActivityOrder.setCreateTime(new Date());
        offlineActivityOrder.setAvailable(1);
        offlineActivityOrder.setMerchantId(merchantId);
        offlineActivityOrder.setOrderStatus(0);
        log.info("订单数据" + offlineActivityOrder);
        try {
            offlineActivityOrderMapper.insertSelective(offlineActivityOrder);
        } catch (Exception e) {
//            e.printStackTrace();
            BSUtil.isTrue(false, "操作频繁，请在一分钟后重试", e);
        }
        BuyResult buyResult = new BuyResult();
        buyResult.setOrderNo(orderNo);
        buyResult.setTotalFee(activityPrice);
        buyResult.setTotalFeeDesc(activityPrice / 100 + "");
        buyResult.setOpenId(user.getWechatOpenid());
        buyResult.setMerchantId(merchantId);
        log.info("订单完成返回结果" + buyResult);
        return buyResult;
    }

    @Override
    public OfflineActivityOrder findOrderByOrderNo(String orderNo) {
        return offlineActivityOrderMapper.selectByPrimaryKey(orderNo);
    }

    @Override
    public OfflineActivityOrder findOrderByActivityCode(String activityCode) {
        return offlineActivityOrderMapper.findOrderByActivityCode(activityCode);
    }
}
