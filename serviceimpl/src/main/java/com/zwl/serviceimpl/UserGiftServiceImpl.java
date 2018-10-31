package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserGiftMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.UserGift;
import com.zwl.service.UserGiftService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author houyuhui
 */
@Service("userGiftService")
public class UserGiftServiceImpl implements UserGiftService {

    @Autowired
    private UserGiftMapper userGiftMapper;

    @Override
    public UserGift addUserExhangeGift(String userId, Integer giftId, Integer addressId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(false, "用户信息不能为空");
        }
        if (null == giftId) {
            BSUtil.isTrue(false, "礼品信息不能为空");
        }
        if (null == addressId) {
            BSUtil.isTrue(false, "收货地址不能为空");
        }
        UserGift userGift = new UserGift();

        return null;
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
