package com.zwl.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.dao.mapper.UserGiftMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Gift;
import com.zwl.model.po.User;
import com.zwl.model.po.UserGift;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.GiftService;
import com.zwl.service.UserGiftService;
import com.zwl.service.UserReceivingAddressService;
import com.zwl.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author houyuhui
 */
@Service("userGiftService")
public class UserGiftServiceImpl implements UserGiftService {

    @Autowired
    private UserGiftMapper userGiftMapper;
    @Autowired
    private GiftService giftService;
    @Autowired
    private UserReceivingAddressService userReceivingAddressService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserGift addUserExchangeGift(String userId, String merchantId, Long giftId, Long addressId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户信息不能为空");
        }
        if (null == giftId) {
            BSUtil.isTrue(false, "商品信息不能为空");
        }
        if (null == addressId) {
            BSUtil.isTrue(false, "收货地址不能为空");
        }
        User user = userService.getByUserId(userId);
        if (null == user || StringUtils.isBlank(user.getUserId())) {
            BSUtil.isTrue(false, "无效用户");
        }
        Gift gift = giftService.getGiftDetailById(giftId);
        if (null == gift || null == gift.getId()) {
            BSUtil.isTrue(false, "无效商品");
        }
        if (user.getMemberLevel().intValue() < gift.getMinRequirement().intValue()) {
            BSUtil.isTrue(false, "请升级您的等级");
        }
        if (gift.getStock() == null || gift.getStock().intValue() <= 0) {
            BSUtil.isTrue(false, "商品已售完");
        }
        UserReceivingAddress userReceivingAddress = userReceivingAddressService.getOneById(addressId);
        if (null == userReceivingAddress || null == userReceivingAddress.getId()) {
            BSUtil.isTrue(false, "无效地址");
        }
        UserGift sysUserGift = this.getUserGiftByGiftId(userId, merchantId, giftId);
        if (null != sysUserGift) {
            BSUtil.isTrue(false, "请勿重复兑换");
        }
        //减少相应库存数
        giftService.updateGiftStock(gift.getId(), gift.getStock(), gift.getStock().intValue() - 1);
        //更新商品销量
        Integer buyCount = gift.getBuyCount() == null ? 0 : gift.getBuyCount();
        giftService.updateGiftBuyCount(gift.getId(), buyCount + 1);
        //添加订单
        UserGift userGift = new UserGift();
        userGift.setGiftId(gift.getId());
        userGift.setPrice(gift.getPrice());
        userGift.setGiftMainImg(gift.getGiftMainImg());
        userGift.setGiftTitle(gift.getGiftMainTitle());
        userGift.setMerchantId(merchantId);
        userGift.setPhone(user.getRegisterMobile());
        userGift.setProvince(userReceivingAddress.getProvince());
        userGift.setCity(userReceivingAddress.getCity());
        userGift.setArea(userReceivingAddress.getArea());
        userGift.setAddress(userReceivingAddress.getAddress());
        userGift.setOrderState(0);
        userGift.setAvailable(1);
        userGift.setCreateTime(new Date());
        return addUserGift(userGift);
    }

    @Override
    public UserGift addUserGift(UserGift userGift) {
        verfiy(userGift);
        try {
            userGiftMapper.insertSelective(userGift);
        } catch (Exception e) {
            BSUtil.isTrue(false, "新增用户兑换礼品出错");
        }
        return userGift;
    }

    @Override
    public PageInfo<UserGift> findUserGiftListPage(String userId, String merchantId, Integer pageSize, Integer pageNum) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<UserGift> userGifts = userGiftMapper.findUserGiftListPage(userId, merchantId);
        return new PageInfo<>(userGifts);
    }

    @Override
    public UserGift getUserGiftById(Long id) {
        if (null == id) {
            BSUtil.isTrue(false, "查询的编号不能为空");
        }
        UserGift userGift = userGiftMapper.selectByPrimaryKey(id);
        return userGift;
    }

    @Override
    public UserGift getUserGiftByGiftId(String userId, String merchantId, Long giftId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户标识不能为空");
        }
        if (StringUtils.isBlank(merchantId)) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
        if (giftId == null) {
            BSUtil.isTrue(false, "请输入要查询的商品编号");
        }
        UserGift userGift = userGiftMapper.getUserGiftByGiftId(userId, merchantId, giftId);
        return userGift;
    }

    private void verfiy(UserGift userGift) {
        if (userGift == null) {
            BSUtil.isTrue(false, "参数错误");
        }
        if (StringUtils.isBlank(userGift.getUserId())) {
            BSUtil.isTrue(false, "用户编号不能为空");
        }
        if (StringUtils.isBlank(userGift.getPhone())) {
            BSUtil.isTrue(false, "手机号码不能为空");
        }
        if (StringUtils.isBlank(userGift.getMerchantId())) {
            BSUtil.isTrue(false, "商户号不能为空");
        }
    }
}
