package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.zwl.dao.mapper.UserInfoMapper;
import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.constant.UserRegisterType;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Merchant;
import com.zwl.model.po.TokenModel;
import com.zwl.model.po.User;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.*;
import com.zwl.service.*;
import com.zwl.util.CheckUtil;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SuppressWarnings("all")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MiniAppWeChatService miniAppWeChatService;
    @Autowired
    private RedisTokenManagerImpl tokenManager;
    @Autowired
    private H5AppWeChatService h5AppWeChatService;
    @Autowired
    private UserInfoService userInfoService;

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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public void modifyAuthorization(UserLoginInfoVo userLoginInfoVo, User userQuery) {
        //如果用户还未购买，则可以更新推荐人
        if ((userQuery.getIsBuy() == null || userQuery.getIsBuy() == 0) && CheckUtil.isNotEmpty(userLoginInfoVo.getReferrer())) {
            User user = new User();
            user.setUserId(userQuery.getUserId());
            //推荐人userId 推荐人必须购买过
            String referrer = userLoginInfoVo.getReferrer();
            if (CheckUtil.isNotEmpty(referrer)) {
                User userIsBuy = userMapper.getUserByUserId(referrer);
                if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                    user.setReferrer(userLoginInfoVo.getReferrer());
                    userMapper.updateUserByUserId(user);
                }


            }
        }
        String userId = userQuery.getUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfoMapper.updateByParams(userInfo);
        //用户主表头像也更新
        if (CheckUtil.isNotEmpty(userLoginInfoVo.getLogoUrl())) {
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

    @Override
    public Integer getTotalPerformanceByUserId(String userId) {
        return userMapper.getTotalPerformanceByUserId(userId);
    }

    @Override
    public User getUserByOpenIdAndMerchantId(String openId, String merchantId) {
        if (null == openId) {
            BSUtil.isTrue(Boolean.FALSE, "微信openId不能为空");
        }
        if (null == merchantId) {
            BSUtil.isTrue(Boolean.FALSE, "商户编号不能为空");
        }
        return userMapper.getUserByOpenIdAndMerchantId(openId, merchantId);
    }

    @Override
    public Result miniAppWeChatAuthorization(UserLoginInfoVo userLoginInfoVo, String code, String merchantId) {
        if (null == code) {
            BSUtil.isTrue(Boolean.FALSE, "请输入code");
        }
        if (null == merchantId) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要登录的小程序编号");
        }
        log.info("====@@@@进入用户授权@@@@@==========");
        log.info("====@@@@推荐人传入参数为@@@@@==========：");
        //根据merchantid获取appid和secret
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        Result result = new Result();
        if (merchant == null)
            BSUtil.isTrue(false, "商户不存在");
        //根据code获取openid 去掉数据库appid和appsecret的空格和换行等
        String resultStr = miniAppWeChatService.authorizationCode(code, merchant.getAppId(), merchant.getAppSecret());
        if (StringUtils.isEmpty(resultStr))
            BSUtil.isTrue(false, "获取不到微信用户信息");
        Map map = JSON.parseObject(resultStr, Map.class);
        if (!StringUtils.isEmpty(map.get("errcode"))) {
            result.setCode(map.get("errcode").toString());
            result.setMessage("微信返回错误信息：" + map.get("errmsg").toString());
            return result;
        }
        String openid = map.get("openid").toString();
        //先查询用户之前是否授权登录过
        User userQuery = new User();
        userQuery.setWechatOpenid(openid);
        userQuery.setMerchantId(merchantId);
        userQuery = getOneByParams(userQuery);
        String userId;
        if (userQuery == null) {//用户之前没授权登录过
            userId = saveAuthorization(userLoginInfoVo, openid);
        } else {//如果用户还未购买，则可以更新推荐人
            userId = userQuery.getUserId();
            log.info("====@@@@用户之前已经授权登录过，userId为：@@@@@==========：" + userId);
            modifyAuthorization(userLoginInfoVo, userQuery);
        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(userId);
        String token = model.getSignToken();
        Map resultMap = new HashMap<String, Object>();
        resultMap.put("token", token);
        resultMap.put("userId", userId);
        result.setData(resultMap);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public Result h5WeChatAuthorization(UserLoginInfoVo userLoginInfoVo, String code, String merchantId) {
        if (null == code) {
            BSUtil.isTrue(Boolean.FALSE, "请输入code");
        }
        if (null == merchantId) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要登录的小程序编号");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (merchant == null) {
            BSUtil.isTrue(Boolean.FALSE, "商户不存在");
        }
        WxH5AccessTokenVo accessTokenVo = h5AppWeChatService.getH5AccessToken(merchant.getGzAppId(), merchant.getGzAppKey(), code);
        if (null == accessTokenVo) {
            BSUtil.isTrue(Boolean.FALSE, "微信没有返回授权信息");
        }
        if (accessTokenVo.getErrcode() == null) {//微信请求成功
            // 获取微信信息成功
            User user = getUserByOpenIdAndMerchantId(accessTokenVo.getOpenid(), merchantId);
            if (user == null) {
                //创建用户
                user = h5AccreditCreateUser(accessTokenVo.getOpenid(), merchantId);
                WxUserInfoVo userInfoVo = h5AppWeChatService.getWeChatUserInfo(accessTokenVo.getAccess_token(), accessTokenVo.getOpenid());
                //创建用户对应的用户信息
                h5AccreditCreateUserInfo(userInfoVo, user.getUserId());
            } else {
                //用户已存在获取微信用户信息更新数据库中的资料
                WxUserInfoVo userInfoVo = h5AppWeChatService.getWeChatUserInfo(accessTokenVo.getAccess_token(), accessTokenVo.getOpenid());
                h5AccreditUpdateUserInfo(userInfoVo,user.getUserId());
            }
            TokenModel model = tokenManager.createToken(user.getUserId());
            String token = model.getSignToken();
            return new WxResultSuccessMessage(user.getUserId(), token);
        }
        return new WxResultErrorMessage(accessTokenVo.getErrcode(), accessTokenVo.getErrmsg());
    }

    private User h5AccreditCreateUser(String openId, String merchantId) {
        if (null == openId) {
            BSUtil.isTrue(Boolean.FALSE, "opendId不能为空");
        }
        User user = new User();
        user.setUserId(UUIDUtil.getUUID32());
        user.setWechatOpenid(openId);
        user.setMerchantId(merchantId);
        user.setRegisterFrom(UserRegisterType.WECHAT_ACCREDIT.getValue());
        user.setIsBuy(0);
        addUser(user);
        return user;
    }

    private UserInfo h5AccreditCreateUserInfo(WxUserInfoVo userInfoVo, String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        String nickName = userInfoVo.getNickname();
        if (nickName != null && nickName.length() > 0) {
            nickName = nickName.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        }
        userInfo.setNickName(nickName);
        userInfo.setLogoUrl(userInfoVo.getHeadimgurl());
        userInfo.setAvailable(1);
        userInfoService.add(userInfo);
        return userInfo;
    }

    private UserInfo h5AccreditUpdateUserInfo(WxUserInfoVo userInfoVo, String userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        String nickName = userInfoVo.getNickname();
        if (nickName != null && nickName.length() > 0) {
            nickName = nickName.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        }
        userInfo.setNickName(nickName);
        userInfo.setLogoUrl(userInfoVo.getHeadimgurl());
        userInfo.setAvailable(1);
        userInfoService.modifyByParams(userInfo);
        return userInfo;
    }


}
