package com.zwl.serviceimpl;

import com.zwl.dao.mapper.UserInfoMapper;
import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.po.User;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.UserLoginInfoVo;
import com.zwl.model.vo.UserQueryVo;
import com.zwl.service.UserService;
import com.zwl.util.CheckUtil;
import com.zwl.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@SuppressWarnings("all")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public User getOneByParams(User user) {
        return userMapper.selectOneByParams(user);
    }

    @Override
    public int updateUserByUserId(User user) {
        return userMapper.updateUserByUserId(user);
    }

    @Override
    public List<User> getListByParams(User user) {
        return userMapper.selectListByParams(user);
    }

    @Override
    public List<User> getUserListByMerchantId(UserQueryVo userQueryVo) {
        return userMapper.getUserListByMerchantId(userQueryVo);
    }

    @Override
    public List<User> search(String merchantId, String registerMobile, Integer registerFrom) {
        return userMapper.search(merchantId, registerMobile, registerFrom);
    }

    @Override
    public Integer getMemberLevel(String userId) {
        return userMapper.getMemberLevel(userId);
    }

    @Override
    public Integer getMaidPercentByUserId(String userId) {
        return userMapper.getMaidPercentByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public String saveAuthorization(UserLoginInfoVo userLoginInfoVo, String openid) {
        String userId = UUIDUtil.getUUID32();
        User user = new User();
        user.setWechatOpenid(openid);
        user.setUserId(userId);
        user.setMerchantId(userLoginInfoVo.getMerchantId());
        //1、微信授权的 2、线下导入的 3、手机号注册的
        user.setRegisterFrom(1);
        //推荐人userId 推荐人必须购买过
        String referrer = userLoginInfoVo.getReferrer();
        if (CheckUtil.isNotEmpty(referrer)) {
            User userIsBuy = userMapper.getUserByUserId(referrer);
            if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                user.setReferrer(userLoginInfoVo.getReferrer());
            }
        }
        user.setIsBuy(0);
        //插入用户表
        user.setLogoUrl(userLoginInfoVo.getLogoUrl());
        userMapper.insert(user);
        //用户信息（头像、昵称等）
        UserInfo userInfo = new UserInfo();
        String nickName = userLoginInfoVo.getNickName();
        if (nickName != null && nickName.length() > 0) {
            nickName = nickName.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        }
        userInfo.setNickName(nickName);
        userInfo.setLogoUrl(userLoginInfoVo.getLogoUrl());
        userInfo.setUserId(userId);
        userInfo.setAvailable(1);
        //插入用户详情表
        userInfoMapper.insert(userInfo);
        return userId;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void modifyAuthorization(UserLoginInfoVo userLoginInfoVo, User userQuery) {
        //如果用户还未购买，则可以更新推荐人
        if ((userQuery.getIsBuy() == null || userQuery.getIsBuy() == 0) && CheckUtil.isNotEmpty(userLoginInfoVo.getReferrer())) {
            User user = new User();
            user.setUserId(userQuery.getUserId());
            //推荐人userId 推荐人必须购买过
            String referrer = userLoginInfoVo.getReferrer();
            if (CheckUtil.isNotEmpty(referrer)) {
                User userIsBuy = userMapper.getUserByUserId(referrer);
                if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy()==1) {
                    user.setReferrer(userLoginInfoVo.getReferrer());
                    userMapper.updateUserByUserId(user);
                }


            }
        }
        String userId = userQuery.getUserId();
        UserInfo userInfo=new UserInfo();
        userInfo.setUserId(userId);
        userInfoMapper.updateByParams(userInfo);
        //用户主表头像也更新
        if(CheckUtil.isNotEmpty(userLoginInfoVo.getLogoUrl())){
            User user = new User();
            user.setUserId(userQuery.getUserId());
            user.setLogoUrl(userLoginInfoVo.getLogoUrl());
            userMapper.updateUserByUserId(user);
        }
    }

    @Override
    public User getReferrerByUserId(String userId) {
        return userMapper.getReferrerByUserId(userId);
    }

}
