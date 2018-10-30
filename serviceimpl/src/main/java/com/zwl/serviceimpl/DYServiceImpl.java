package com.zwl.serviceimpl;

import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.wxpay.PaymentKit;
import com.zwl.service.*;
import com.zwl.util.BigDecimalUtil;
import com.zwl.util.DateUtil;
import com.zwl.util.PayNotifyProperties;
import com.zwl.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: DYServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/817:22
 */
@Service
@Slf4j
public class DYServiceImpl implements DYService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserQuotaCountService userQuotaCountService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private MsgSenderService msgSenderService;
    @Autowired
    private OfflineActivityService offlineActivityService;
    @Autowired
    private OfflineActivityThemeService offlineActivityThemeService;
    @Autowired
    private OfflineActivityOrderService offlineActivityOrderService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;
    @Autowired
    private PayNotifyProperties payNotifyProperties;
    @Autowired
    private UserMaidPercentService userMaidPercentService;
    private SimpleDateFormat sdf_yMdHms = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public String payNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id) {
        Order order = orderService.findOrderByOrderNo(out_trade_no);
        //如果order状态为支付成功，则不更新
        Integer status = order.getOrderStatus();
        //判断支付金额是否与订单实际支付金额一致，不一致则返回错误，防止被恶意刷单攻击
        //判断支付回调签名是否跟发送的签名一致，不一致则返回错误，防止被恶意刷单攻击
        log.info("回调订单签名--------->" + sign);
//                根据商户号获取支付key
        Merchant merchant = merchantService.getMerchantByMerchantId(mch_id);
        boolean checkSign = PaymentKit.isWechatSign(params, merchant.getWxPayKey());
        if (!checkSign) {
            BSUtil.isTrue(false, "签名错误!");
            //发送通知等
            Map<String, String> xml = new HashMap<>();
            xml.put("return_code", "ERROR");
            xml.put("return_msg", "ERROR");
            return PaymentKit.toXml(xml);
        }
        Integer orderActualMoney_temp = order.getActualMoney();
        if (Integer.parseInt(total_fee) < orderActualMoney_temp)
            BSUtil.isTrue(false, "支付失败");


        if (null == status || 1 != status) {
            Order order_t = new Order();
            order_t.setOrderNo(out_trade_no);
            try {
                order_t.setPaymentTime(sdf_yMdHms.parse(time_end));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            order_t.setPaymentNo(transaction_id);
            order_t.setOrderStatus(1);
            order_t.setPayWay(1);
            //更新订单信息
            int count = orderService.updateOrder(order_t);
            if (count != 1)
                BSUtil.isTrue(false, "异步支付更新失败");
            //                    支付完成时间，格式为yyyyMMddHHmmss
            Date paymentTime = null;
            try {
                paymentTime = sdf_yMdHms.parse(time_end);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //购买成功之后，更新用户会员等级，等级名称，会员到期时间
            String userId = order.getUserId();
            Integer validityTime = order.getValidityTime();
            User user = userService.getByUserId(userId);
            try {
                String memberValidityTime = sdf_yMdHms.format(paymentTime.getTime() + (long) validityTime * 24 * 60 * 60 * 1000);
                user.setExpiresTime(sdf_yMdHms.parse(memberValidityTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Integer memberLevel = order.getLevel();
            user.setMemberLevel(memberLevel);
            user.setLevelName(order.getLevelName());
            user.setIsBuy(1);
            //更新是否购买
            log.info("回调支付成功开始更新用户等级" + user);
            userService.updateUserByUserId(user);
            //订单支付成功，返佣给推荐人
            //后期加入MQ
            log.info("回调支付成功，开始分佣");
            String merchantId = order.getMerchantId();
            String orderNo = order.getOrderNo();
            Integer orderActualMoney = order.getActualMoney();
            Integer maidPercent = order.getMaidPercent();
            Long productId = order.getProductId();
            String productName = order.getProductName();
            Integer level = order.getLevel();
            String levelName = order.getLevelName();
            String referrerId = null;
            //fixme 推荐人关联沙龙活动
            //根据推荐人是否死绑推荐人
            //如果是0 或者为null
            //则先查询当天当天是否存在沙龙订单，如果存在 判断推荐人是否存在，存在 则获取  不存在则取user.getReferrer();
            Integer isBuy = user.getIsBuy();
            if (null == isBuy || isBuy == 0) {
                Date currentDate = new Date();
                OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.getOfflineActivityOrderByActivityDate(userId, currentDate);
                if( null != offlineActivityOrder){
                    referrerId = offlineActivityOrder.getSlReferrer();
                }
            } else if (isBuy == 1) {
                referrerId = user.getReferrer();
            } else {
                log.error("返佣出错无效的isBuy:" + isBuy);
                throw new RuntimeException("返佣出错无效的isBuy:" + isBuy);
            }

            //增加小班次数,可能是第一次购买需要insert，也可能是update
            switch (memberLevel) {
                case 4:
                    userQuotaCountService.saveOrUpdate(userId, 10);
                    break;
                case 5:
                    userQuotaCountService.saveOrUpdate(userId, 50);
                    break;
                case 6:
                    userQuotaCountService.saveOrUpdate(userId, 100);
                    //如果旗下院长数量=10,则升级为校长
                    log.info("如果旗下院长数量=10,则升级为校长");
                    Integer xzCount = userService.getXzCountByUserId(referrerId);
                    log.info("xzCount" + xzCount);
                    if (xzCount != null) {
                        userService.updateUserToXzByUserId(referrerId);
                    }
                    break;
                default:
                    break;
            }
            //购买成功之后,更新购买数量
            productService.updateBuyCountById(productId, merchantId);

            if (StringUtils.isNotBlank(referrerId)) {
                User referrerUser = userService.getByUserId(referrerId);
                if (null != referrerUser) {
                    //返佣规则：同级或者下级，高于当前级别是不返佣
//                如果产品的等级是1，分佣比列按照产品的分佣百分比计算
//                并且还要满足推荐人的邀请名额限定 院长 100 班长 50 学员10
//                新增一张名额限定表 ss_quota_count
//                如果产品等级为1，(则先判断推荐人的邀请名额是否满足，如果不满足则直接跳过 )
//                如果产品等级为 456 则相应添加邀请名额
                    if (1 == memberLevel) {
                        Integer userQuotaCount = userQuotaCountService.updateCountByUserId(referrerId);
                        //存在数据异常  userQuotaCount null
//                                if (userQuotaCount == 0 || userQuotaCount == null) {
//                                    log.info("邀请小班名额已满，不返分佣");
//                                    //发送通知等
//                                    Map<String, String> xml = new HashMap<String, String>();
//                                    xml.put("return_code", "SUCCESS");
//                                    xml.put("return_msg", "OK");
//                                    return PaymentKit.toXml(xml);
//                                } else {
                        log.info("邀请小班名额成功-1");
//                                }
                    }

                    //在不是试听课的时候，查询当前用户有效会员等级并且小于等于推荐人的有效会员等级才可以返佣
                    Integer referrerLevel = userService.getMemberLevel(referrerId);
                    log.info("referrerLevel:" + referrerLevel + "------------memberLevel:" + memberLevel);
                    if (null != referrerLevel && referrerLevel >= memberLevel && referrerLevel >= 1) {
//                       //通过memberLevel,merchantId获取推荐人对应的分佣比例
                        UserMaidPercent userMaidPercent = userMaidPercentService.getUserMaidPercentByMemberLevelAndMerchantId(referrerLevel, merchantId);
//                        Integer maidPercent_referrer = productService.getMaidPercentByLevel(referrerLevel, merchantId);
                        Integer maidPercent1 = userMaidPercent.getMaidPercent1();
                        Integer maidPercent4 = userMaidPercent.getMaidPercent4();
                        Integer maidPercent6 = userMaidPercent.getMaidPercent6();
                        Integer maidPercent_referrer = 0;
                        //根据产品的等级 分配不同的分佣比例
                        switch (memberLevel) {
                            case 6:
                                maidPercent_referrer = maidPercent6;
                                break;
                            case 4:
                                maidPercent_referrer = maidPercent4;
                                break;
                            case 1:
                                maidPercent_referrer = maidPercent1;
                                break;
                        }
                        if (maidPercent_referrer > 0) {
                            MaidInfo maidInfo = new MaidInfo();
                            maidInfo.setOrderNo(orderNo);
                            //分佣发送给推荐人
                            maidInfo.setUserId(referrerUser.getUserId());
                            maidInfo.setMaidUserId(userId);
                            //根据推荐的的分佣百分比返佣
                            //如果产品的等级是1，分佣比列按照产品的分佣百分比计算
                            Integer maidMoney = 0;
                            if (level == 1) {
                                maidMoney = orderActualMoney * maidPercent / 100;
                                maidInfo.setMaidPercent(maidPercent);
                            } else {
                                log.info("maidPercent_referrer:" + maidPercent_referrer);
                                maidMoney = orderActualMoney * maidPercent_referrer / 100;
                                maidInfo.setMaidPercent(maidPercent_referrer);
                            }
                            maidInfo.setMaidMoney(maidMoney);
                            maidInfo.setOrderActualMoney(orderActualMoney);
                            maidInfo.setMerchantId(merchantId);
                            maidInfo.setProductId(productId);
                            maidInfo.setProductName(productName);
                            maidInfo.setLevel(level);
                            maidInfo.setLevelName(levelName);
                            log.info("回调支付成功，分佣信息" + maidInfo);
                            int madiInfoCount = maidInfoService.save(maidInfo);
                            if (madiInfoCount == 0)
                                BSUtil.isTrue(false, "分佣失败");
                            log.info("回调支付成功，结束分佣");
                            //分佣完成之后，更新用户账户表ss_user_account
                            //存在未开户 直接开户
                            log.info("回调支付成功，更新用户余额userId" + userId + "--->" + "maidMoney--->" + maidMoney);
                            UserAccount userAccount = userAccountService.getUserAccountByUserId(referrerId);
                            if (userAccount == null) {
                                log.info("创建用户余额表开始");
                                UserAccount userAccount_t = new UserAccount();
                                userAccount_t.setUserId(referrerId);
                                userAccount_t.setBalance(maidMoney);
                                userAccountService.save(userAccount_t);
                                log.info("创建用户余额表结束");
                            } else
                                userAccountService.addBanlanceByUserId(referrerId, maidMoney);
                            log.info("回调支付成功，更新用户余额成功");
//                                【东遥课堂】尾号7903成功购买99元课程 , 你将获得奖励90元 ,  尽快查阅小程序
                            String referrerPhone = referrerUser.getRegisterMobile();
                            if (StringUtils.isNotBlank(referrerPhone)) {
                                String userMobile = user.getRegisterMobile();
                                String msg = "【东遥课堂】手机尾号" + userMobile.substring(userMobile.length() - 4) + "成功购买" + productName + ", 你将获得奖励" + maidMoney / 100 + "元 ,  尽快查阅小程序~";
                                msgSenderService.sendMsg(referrerPhone, msg);
                            }
                            //存在referrerUser.getGzhOpenid()==null
                            //发送公众号推送
//                                String referrerGzhOpenId = referrerUser.getGzhOpenid();
//                                log.info("referrerGzhOpenId is null ，referrerUserId:" + referrerId + ",userId:" + userId);
//                                if (StringUtils.isNotBlank(referrerGzhOpenId))
//                                    gzhService.sendBuyGzhMsgByOne(referrerGzhOpenId, orderNo, productName, orderActualMoney, user.getRegisterMobile(), merchantId, merchant.getGzAppId(), merchant.getGzAppKey(), merchant.getAppId(), "2AT4AIsTNNOJP3YFSUSlyDruKPTdPBgyieyqI0jKmVQ", maidMoney);


                        }
                    }
//

                }
            }

//                    根据商户号 获取购买模版 formId ,appSecret
//                    log.info("开始发送购买模版");
//                    String gzOpenId = stringRedisTemplate.boundValueOps("formId_" + userId).get();
////                    存在gzOpenId为null 7天失效
//                    if (!StringUtils.isBlank(gzOpenId)) {
//                        log.info("开始发送购买模版gzOpenId" + gzOpenId);
//                        String appSecret = merchant.getAppSecret();
//                        String buyTemplateId = merchant.getBuyTemplateId();
//                        //发送订单购买公众号提醒
//                        wxSenderService.sendBuyMsg(gzOpenId, productName, Integer.parseInt(total_fee), merchantId, appid, appSecret, buyTemplateId);
//                    }

            //发送通知等
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);
        } else {
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);

        }

    }

    @Override
    public String xxPayNotify(Map<String, String> params, String out_trade_no, String sign, String mch_id, String total_fee, String time_end, String transaction_id) {
        //线下活动回调通知
        //生成二维码
        //是否返佣
        //否
        //是  是否复训
        //是 结束
        //否 分佣
        //自然人 99 结束
        //980 课程类型1980 才能返佣30%
        //5000  校长   课程类型1980  返佣40%    20000元返佣30%
//        通过订单号 获取产品是否返佣


        OfflineActivityOrder offlineActivityOrder = offlineActivityOrderService.findOrderByOrderNo(out_trade_no);
        //如果order状态为支付成功，则不更新
        Integer status = offlineActivityOrder.getOrderStatus();
        if (1 == status || -1 == status) {
            //发送通知等
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);
        }
        Integer activityId = offlineActivityOrder.getActivityId();
        OfflineActivity offlineActivity = offlineActivityService.getOneByActivityId(activityId);
        Integer isMaid = offlineActivity.getIsMaid() == null ? 0 : 1;
        Integer isRetraining = offlineActivity.getIsRetraining() == null ? 0 : 1;
        Integer offlineType = offlineActivity.getActivityType();
        String userId = offlineActivityOrder.getUserId();
        Integer price = offlineActivityOrder.getActivityPrice();
        //生成二维码
        log.info("开始生成二维码");
        OfflineActivityCode offlineActivityCode = new OfflineActivityCode();
        String activityCode = offlineActivityOrder.getActivityCode();
        offlineActivityCode.setActivityCode(activityCode);
        String qrCodeUrl = QRCodeUtil.createQrCode(payNotifyProperties.getQrCodeUrl() + activityCode, null, null);
        offlineActivityCode.setActivityId(offlineActivity.getId());
        offlineActivityCode.setActivityThemeId(offlineActivity.getActivityThemeId());
        offlineActivityCode.setAvailable(1);
        offlineActivityCode.setMerchantId(mch_id);
        offlineActivityCode.setUserId(userId);
        offlineActivityCode.setCreateTime(new Date());
        offlineActivityCode.setQrCodeUrl(qrCodeUrl);
        offlineActivityCode.setIsUsed(0);
        offlineActivityCodeService.insert(offlineActivityCode);
        log.info("二维码生成成功:" + qrCodeUrl);

        OfflineActivityTheme offlineActivityTheme = offlineActivityThemeService.getOfflineActivityThemeDetailByThemeId(offlineActivity.getMerchantId(), offlineActivity.getActivityThemeId());
        //更新订单状态、支付流水号、支付时间
        offlineActivityOrderService.updateStatusByOrderNo(out_trade_no, transaction_id, time_end);
        //更新活动购买人数
        offlineActivityService.updateBuyCountById(activityId);
        //更新活动主题购买人数
        offlineActivityThemeService.updateBuyCountById(offlineActivity.getActivityThemeId());
        if (isMaid == 1) {
            //1返佣
            if (isRetraining == 0) {
                //不是复训 开始分佣
                //判断购买人的推荐人等级
                User user = userService.getByUserId(userId);
                String referrerId = user.getReferrer();
                if (referrerId != null) {
                    User referrer = userService.getByUserId(referrerId);
                    Integer memberLevel = referrer.getMemberLevel();
                    Integer maidMoney = 0;
                    //课程类型
                    log.info("开始返佣");
                    switch (offlineType) {
                        case 1980:
                            if (memberLevel >= 6) {
                                //课程类型1980  返佣40%
                                maidMoney = Integer.parseInt(BigDecimalUtil.mul(price, 0.4) + "");
                            } else if (memberLevel == 4) {
                                //课程类型1980  返佣30%
                                maidMoney = Integer.parseInt(BigDecimalUtil.mul(price, 0.3) + "");
                            } else {
                                //不返佣
                            }
                            break;
                        case 20000:
                            if (memberLevel >= 6) {
                                //返佣30%
                                maidMoney = Integer.parseInt(BigDecimalUtil.mul(price, 0.3) + "");
                            } else {
                                //不返佣
                            }
                            break;
                        default:
                            break;
                    }
                    log.info("结束返佣，返佣金额：" + maidMoney);
                    //开始更新账户余额
                    MaidInfo maidInfo = new MaidInfo();
                    maidInfo.setOrderNo(out_trade_no);
                    //分佣发送给推荐人
                    maidInfo.setUserId(referrer.getUserId());
                    maidInfo.setMaidUserId(userId);
                    maidInfo.setMaidMoney(maidMoney);
                    maidInfo.setOrderActualMoney(offlineActivityOrder.getActualMoney());
                    maidInfo.setMerchantId(offlineActivityOrder.getMerchantId());
                    maidInfo.setProductId(Long.parseLong(offlineActivity.getId() + ""));
                    maidInfo.setProductName(offlineActivityTheme.getThemeName());
                    maidInfo.setLevel(user.getMemberLevel());
                    maidInfo.setLevelName(user.getLevelName());
                    log.info("回调支付成功，分佣信息" + maidInfo);
                    int madiInfoCount = maidInfoService.save(maidInfo);
                    if (madiInfoCount == 0)
                        BSUtil.isTrue(false, "分佣失败");
                    log.info("回调支付成功，结束分佣");
                    //分佣完成之后，更新用户账户表ss_user_account
                    //存在未开户 直接开户
                    log.info("回调支付成功，更新用户余额userId" + userId + "--->" + "maidMoney--->" + maidMoney);
                    UserAccount userAccount = userAccountService.getUserAccountByUserId(referrerId);
                    if (userAccount == null) {
                        log.info("创建用户余额表开始");
                        UserAccount userAccount_t = new UserAccount();
                        userAccount_t.setUserId(referrerId);
                        userAccount_t.setBalance(maidMoney);
                        userAccountService.save(userAccount_t);
                        log.info("创建用户余额表结束");
                    } else {
                        userAccountService.addBanlanceByUserId(referrerId, maidMoney);
                    }
                    log.info("回调支付成功，更新用户余额成功");

                }


            }


            //发送通知等
            Map<String, String> xml = new HashMap<String, String>();
            xml.put("return_code", "SUCCESS");
            xml.put("return_msg", "OK");
            return PaymentKit.toXml(xml);
        }


        return null;
    }
}
