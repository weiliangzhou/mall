package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserWechatMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.UserWechat;
import com.zwl.service.UserWechatService;
import com.zwl.util.ThreadVariable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserWechatServiceImpl implements UserWechatService {

    @Autowired
    private UserWechatMapper userWechatMapper;
    /**
     * 微信账号命名规则
     */
    private String WECHAT_RULE = "^[a-zA-Z\\d_]{5,}$";

    @Override
    public UserWechat getUserWechatByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要查询的用户");
        }
        UserWechat result = userWechatMapper.getUserWechatByUserId(userId);
        return result;
    }

    @Override
    public UserWechat addUserWechat(UserWechat userWechat) {
        verfiy(userWechat);
        userWechat.setCreateTime(new Date());
        userWechatMapper.insertSelective(userWechat);
        return userWechat;
    }

    @Override
    public UserWechat saveUserWechat(String wechatAccount) {
        if (StringUtils.isBlank(wechatAccount)) {
            BSUtil.isTrue(Boolean.FALSE, "微信账号不能为空");
        }
        String userId = ThreadVariable.getUserID();
        UserWechat userWechat = new UserWechat();
        userWechat.setUserId(userId);
        userWechat.setWechatAccount(wechatAccount);
        return this.addUserWechat(userWechat);
    }

    private void verfiy(UserWechat userWechat) {
        if (userWechat == null) {
            BSUtil.isTrue(Boolean.FALSE, "参数错误");
        }
        if (StringUtils.isBlank(userWechat.getUserId())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入用户编号");
        }
        if (StringUtils.isBlank(userWechat.getWechatAccount())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入微信账号");
        }
        boolean isMatch = Pattern.matches(WECHAT_RULE, userWechat.getWechatAccount());
        if (!isMatch) {
            BSUtil.isTrue(Boolean.FALSE, "微信账号无效");
        }
        UserWechat sysUserWechat = this.getUserWechatByUserId(userWechat.getUserId());
        if (null != sysUserWechat) {
            BSUtil.isTrue(Boolean.FALSE, "账号已有绑定的微信号");
        }

    }

}
