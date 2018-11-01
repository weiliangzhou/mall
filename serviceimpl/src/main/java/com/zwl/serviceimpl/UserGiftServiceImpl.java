package com.zwl.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwl.dao.mapper.UserGiftMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Gift;
import com.zwl.model.po.UserGift;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.GiftService;
import com.zwl.service.UserGiftService;
import com.zwl.service.UserReceivingAddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public UserGift addUserExchangeGift(String userId, String merchantId, Long giftId, Long addressId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户信息不能为空");
        }
        if (null == giftId) {
            BSUtil.isTrue(false, "礼品信息不能为空");
        }
        if (null == addressId) {
            BSUtil.isTrue(false, "收货地址不能为空");
        }
        Gift gift = giftService.getGiftDetailById(giftId);
        if (null == gift || null == gift.getId()) {
            BSUtil.isTrue(false, "无效礼品");
        }
        UserReceivingAddress userReceivingAddress = userReceivingAddressService.getOneById(addressId);
        if (null == userReceivingAddress || null == userReceivingAddress.getId()) {
            BSUtil.isTrue(false, "无效地址");
        }
        UserGift userGift = new UserGift();
        userGift.setGiftId(gift.getId());
        userGift.setGiftTitle(gift.getGiftMainTitle());
        userGift.setMerchantId(merchantId);
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
