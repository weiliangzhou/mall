package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OfflineActivityOrderMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.exception.BusinessException;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.vo.OfflineActivityOrderVo;
import com.zwl.service.*;
import com.zwl.util.DateUtil;
import com.zwl.util.ThreadVariable;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.zwl.util.BigDecimalUtil.div;

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
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private UserWechatService userWechatService;

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
            if (memberLevel < minRequirement) {
                BSUtil.isTrue(false, "未获得购买线下课程的资格！");
            }
        }
        Integer isRebuy = offlineActivity.getIsRebuy();
        //不能重复购买
        //则判断该用户是否购买过
        if (0 == isRebuy) {
            //通过userId  activityId
            OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByUserIdAndOfflineActivityId(userId, offlineActivityId);
            if (offlineActivityCode != null) {
                BSUtil.isTrue(false, "不能重复购买！");
            }
        }
        //已购数量
        Integer buyCount = offlineActivity.getBuyCount() == null ? 0 : offlineActivity.getBuyCount();
        //可购数量
        Integer limitCount = offlineActivity.getLimitCount() == null ? 0 : offlineActivity.getLimitCount();
        if (limitCount <= buyCount) {
            BSUtil.isTrue(false, "超过可购买数量");
        }
        String orderNo = "xx" + sdf_yMdHm.format(new Date()) + merchantId + userLongId + productId;
        OfflineActivityOrder offlineActivityOrder = new OfflineActivityOrder();
        Integer activityId = offlineActivity.getId();
        Integer isRetraining = offlineActivity.getIsRetraining();
        Integer activityPrice = offlineActivity.getActivityPrice();
        if (isRetraining == 1) {
            Integer count = offlineActivityCodeService.getAlreadyBuyCountByUserIdAndThemeId(userId, offlineActivity.getActivityThemeId(), merchantId);
            activityPrice = count == 0 ? activityPrice : offlineActivity.getRetrainingPrice();
        }

        if (offlineActivityBuy.getOrderType() == 1) {
            //更新用户  性别  省 市
            User sysUser = userService.getByUserId(userId);
            if (sysUser.getGender() == null) {
                User sysUserParam = new User();
                sysUserParam.setUserId(userId);
                sysUserParam.setGender(offlineActivityBuy.getSex());
                userService.updateUserByUserId(sysUserParam);
            }
            if (sysUser.getProvince() == null) {
                User sysUserParam = new User();
                sysUserParam.setUserId(userId);
                sysUserParam.setProvince(offlineActivityBuy.getProvince());
                userService.updateUserByUserId(sysUserParam);
            }
            if (sysUser.getCity() == null) {
                User sysUserParam = new User();
                sysUserParam.setUserId(userId);
                sysUserParam.setCity(offlineActivityBuy.getCity());
                userService.updateUserByUserId(sysUserParam);
            }

            //更新用户微信号
            UserWechat userWechat = userWechatService.getUserWechatByUserId(userId);
            if (null == userWechat) {
                userWechatService.saveUserWechat(offlineActivityBuy.getWechatNo());
            }
        }

        offlineActivityOrder.setOrderNo(orderNo);
        offlineActivityOrder.setActivityId(activityId);
        //活动兑换码生成规则
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String activityCode = sdf.format(new Date());
        //String activityCode = UUIDUtil.getUUID32();
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        activityCode = activityCode + result;
        offlineActivityOrder.setActivityCode(activityCode);
        offlineActivityOrder.setUserId(userId);
        offlineActivityOrder.setPhone(offlineActivityBuy.getPhone());
        offlineActivityOrder.setProvince(offlineActivityBuy.getProvince());
        offlineActivityOrder.setCity(offlineActivityBuy.getCity());
        offlineActivityOrder.setCreateTime(new Date());
        offlineActivityOrder.setAvailable(1);
        offlineActivityOrder.setMerchantId(merchantId);
        offlineActivityOrder.setOrderStatus(0);
        offlineActivityOrder.setIsRetraining(offlineActivity.getIsRetraining());
        offlineActivityOrder.setIsMaid(offlineActivity.getIsMaid());
        offlineActivityOrder.setSex(offlineActivityBuy.getSex());
        offlineActivityOrder.setRealName(offlineActivityBuy.getRealName());
        offlineActivityOrder.setIdCardNum(offlineActivityBuy.getIdCardNum());
        offlineActivityOrder.setActualMoney(activityPrice);
        offlineActivityOrder.setActivityPrice(activityPrice);
        offlineActivityOrder.setActivityThemeId(offlineActivity.getActivityThemeId());
        offlineActivityOrder.setSlReferrer(offlineActivityBuy.getSlReferrer());
        offlineActivityOrder.setOrderType(offlineActivityBuy.getOrderType());
        offlineActivityOrder.setWechatNo(offlineActivityBuy.getWechatNo());
        offlineActivityOrder.setActivityDate(offlineActivity.getActivityStartTime());
        log.info("订单数据" + offlineActivityOrder);
        try {
            offlineActivityOrderMapper.insertSelective(offlineActivityOrder);
        } catch (Exception e) {
            log.error("生成订单出错:", e);
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

    @Override
    public List<OfflineActivityOrderVo> getActivityOrderListByUserId(String userId, String merchantId) {
        List<OfflineActivityOrder> offlineActivityOrderList = offlineActivityOrderMapper.getActivityOrderListByUserId(userId, merchantId);
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (OfflineActivityOrder offlineActivityOrder : offlineActivityOrderList) {
            Integer orderType = offlineActivityOrder.getOrderType();
            OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
            offlineActivityOrderVo.setRealName(offlineActivityOrder.getRealName());
            offlineActivityOrderVo.setPhone(offlineActivityOrder.getPhone());
            OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivityOrder.getMerchantId(), offlineActivityOrder.getActivityThemeId());
            offlineActivityOrderVo.setImgUrl(offlineActivityTheme.getImgUrl());
            offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
            OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder.getActivityId());
            offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
            OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(offlineActivityOrder.getActivityCode());
            offlineActivityOrderVo.setIsUsed(offlineActivityCode.getIsUsed());
            offlineActivityOrderVo.setCreateTimeDesc(simpleDateFormat.format(offlineActivityOrder.getCreateTime()));
            offlineActivityOrderVo.setActivityPrice(offlineActivityOrder.getActivityPrice());
            offlineActivityOrderVo.setActivityPriceDesc(div(100, offlineActivityOrder.getActivityPrice(), 2) + "");
            offlineActivityOrderVo.setOrderNo(offlineActivityOrder.getOrderNo());
            offlineActivityOrderVo.setActivityId(offlineActivityOrder.getActivityId());
            offlineActivityOrderVo.setCreateTime(offlineActivityOrder.getCreateTime());
            offlineActivityOrderVo.setSlActivityStartTime(offlineActivity.getActivityStartTime());
            offlineActivityOrderVo.setSlActivityEndTime(offlineActivity.getApplyEndTime());
            if (null != orderType && orderType.equals(1)) {
                User user = userService.getByUserId(offlineActivityOrder.getSlReferrer());
                offlineActivityOrderVo.setOrderType(1);
                offlineActivityOrderVo.setSlReferrerName(user.getRealName());
                offlineActivityOrderVo.setSlReferrerPhone(user.getRegisterMobile());
                if (offlineActivity.getActivityStartTime().getTime() - System.currentTimeMillis() > 0) {
                    offlineActivityOrderVo.setSlStatus("报名成功");
                } else {
                    offlineActivityOrderVo.setSlStatus("已结束");
                }
            } else {
                offlineActivityOrderVo.setOrderType(0);
                offlineActivityOrderVo.setAmount(1);
            }
            offlineActivityOrderVoList.add(offlineActivityOrderVo);
        }
        return offlineActivityOrderVoList;
    }

    @Override
    public void updateStatusByOrderNo(String out_trade_no, String payment_no, String paymentTime) {
        offlineActivityOrderMapper.updateStatusByOrderNo(out_trade_no, payment_no, paymentTime);
    }

    @Override
    public OfflineActivityOrderVo findOrderDetailByOrderNo(String orderNo, String userId) {

        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderMapper.selectByPrimaryKey(orderNo);
        Integer orderType = offlineActivityOrder.getOrderType();
        OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
        offlineActivityOrderVo.setRealName(offlineActivityOrder.getRealName());
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivityOrder.getMerchantId(), offlineActivityOrder.getActivityThemeId());
        offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
        offlineActivityOrderVo.setImgUrl(offlineActivityTheme.getImgUrl());
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder.getActivityId());
        offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
        offlineActivityOrderVo.setActivityStartTime(offlineActivity.getActivityStartTime());
        offlineActivityOrderVo.setActivityEndTime(offlineActivity.getActivityEndTime());
        offlineActivityOrderVo.setActivityPrice(offlineActivityOrder.getActivityPrice());
        offlineActivityOrderVo.setActivityPriceDesc(div(100, offlineActivityOrder.getActivityPrice(), 2) + "");
        OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByUserIdAndOfflineActivityId(offlineActivityOrder.getUserId(), offlineActivityOrder.getActivityId());
        if (null != orderType && orderType.equals(1)) {
            offlineActivityOrderVo.setCreateTime(offlineActivityOrder.getCreateTime());
            offlineActivityOrderVo.setWechatNo(offlineActivityOrder.getWechatNo());
            offlineActivityOrderVo.setSex(offlineActivityOrder.getSex());
            offlineActivityOrderVo.setActivityCode(offlineActivityOrder.getActivityCode());
        } else {
            offlineActivityOrderVo.setQrCodeUrl(offlineActivityCode.getQrCodeUrl());
            offlineActivityOrderVo.setAmount(1);
        }
        return offlineActivityOrderVo;
    }

    @Override
    public OfflineActivityOrder getOfflineActivityOrderByActivityDate(String userId, String merchantId, Date activityDate) {
        if (StringUtils.isBlank(userId)) {
            throw new BusinessException("用户编号不能为空");
        }
        if (null == activityDate) {
            throw new BusinessException("活动时间不能为空");
        }
        Date startDate = DateUtil.getDateDayStart(activityDate);
        Date endDate = DateUtil.getDateDayEnd(activityDate);
        OfflineActivityOrder offlineActivityOrder = this.offlineActivityOrderMapper.getOfflineActivityOrderByActivityDate(userId, merchantId, startDate, endDate);
        return offlineActivityOrder;
    }

    @Override
    public List<OfflineActivityOrderVo> getMySLActivityOrderList(String userId, String merchantId, String activityThemeId) {
        List<OfflineActivityOrder> offlineActivityOrderList = offlineActivityOrderMapper.findSLOrderByUserId(userId, merchantId,activityThemeId);
        List<OfflineActivityOrderVo> offlineActivityOrderVoList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (OfflineActivityOrder offlineActivityOrder : offlineActivityOrderList) {
            OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
            offlineActivityOrderVo.setOrderType(offlineActivityOrder.getOrderType());
            offlineActivityOrderVo.setRealName(offlineActivityOrder.getRealName());
            offlineActivityOrderVo.setPhone(offlineActivityOrder.getPhone());
            OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivityOrder.getMerchantId(), offlineActivityOrder.getActivityThemeId());
            offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
            OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder.getActivityId());
            offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
            OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(offlineActivityOrder.getActivityCode());
            offlineActivityOrderVo.setIsUsed(offlineActivityCode.getIsUsed());
            offlineActivityOrderVo.setCreateTime(offlineActivityOrder.getCreateTime());
            offlineActivityOrderVo.setCreateTimeDesc(simpleDateFormat.format(offlineActivityOrder.getCreateTime()));
            offlineActivityOrderVo.setActivityPrice(offlineActivityOrder.getActivityPrice());
            offlineActivityOrderVo.setActivityPriceDesc(div(100, offlineActivityOrder.getActivityPrice(), 2) + "");
            offlineActivityOrderVo.setOrderNo(offlineActivityOrder.getOrderNo());
            offlineActivityOrderVo.setActivityId(offlineActivityOrder.getActivityId());
            User user = userService.getByUserId(offlineActivityOrder.getSlReferrer());
            offlineActivityOrderVo.setSlReferrerName(user.getRealName());
            offlineActivityOrderVo.setSlReferrerPhone(user.getRegisterMobile());
            User userV = userService.getByUserId(offlineActivityOrder.getUserId());
            offlineActivityOrderVo.setImgUrl(userV.getLogoUrl());
            offlineActivityOrderVoList.add(offlineActivityOrderVo);

        }
        return offlineActivityOrderVoList;
    }

    @Override
    public OfflineActivityOrderVo getSLActivityOrderDetail(String orderNo) {
        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderMapper.selectByPrimaryKey(orderNo);
        OfflineActivityOrderVo offlineActivityOrderVo = new OfflineActivityOrderVo();
        offlineActivityOrderVo.setCreateTime(offlineActivityOrder.getCreateTime());
        offlineActivityOrderVo.setActivityId(offlineActivityOrder.getActivityId());
        offlineActivityOrderVo.setRealName(offlineActivityOrder.getRealName());
        offlineActivityOrderVo.setWechatNo(offlineActivityOrder.getWechatNo());
        offlineActivityOrderVo.setSex(offlineActivityOrder.getSex());
        offlineActivityOrderVo.setCity(offlineActivityOrder.getCity());
        offlineActivityOrderVo.setActivityThemeId(offlineActivityOrder.getActivityThemeId());
        offlineActivityOrderVo.setActivityCode(offlineActivityOrder.getActivityCode());
        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivityOrder.getMerchantId(), offlineActivityOrder.getActivityThemeId());
        offlineActivityOrderVo.setImgUrl(offlineActivityTheme.getImgUrl());
        offlineActivityOrderVo.setThemeName(offlineActivityTheme.getThemeName());
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(offlineActivityOrder.getActivityId());
        offlineActivityOrderVo.setActivityAddress(offlineActivity.getActivityAddress());
        offlineActivityOrderVo.setSlActivityStartTime(offlineActivity.getActivityStartTime());
        offlineActivityOrderVo.setSlActivityEndTime(offlineActivity.getActivityEndTime());
        offlineActivityOrderVo.setActivityPrice(offlineActivityOrder.getActivityPrice());
        offlineActivityOrderVo.setActivityPriceDesc(div(100, offlineActivityOrder.getActivityPrice(), 2) + "");
        //OfflineActivityCode offlineActivityCode = offlineActivityCodeService.getOneByActivityCode(offlineActivityOrder.getActivityCode());
        if (offlineActivity.getActivityStartTime().getTime() - System.currentTimeMillis() > 0) {
            offlineActivityOrderVo.setSlStatus("报名成功");
        } else {
            offlineActivityOrderVo.setSlStatus("已结束");
        }

        return offlineActivityOrderVo;
    }
}
