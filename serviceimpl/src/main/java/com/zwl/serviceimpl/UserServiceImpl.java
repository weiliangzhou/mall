package com.zwl.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.zwl.dao.mapper.UserInfoMapper;
import com.zwl.dao.mapper.UserMapper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.constant.UserRegisterType;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.vo.*;
import com.zwl.service.*;
import com.zwl.util.CheckUtil;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private MsgSenderService msgSenderService;


    //微信小程序登录的时候目前是静默授权  设置为默认头像
    private static String WX_DEFAULT_HEAD_IMG = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/6a989ec302994c6c98c2d4810f9fbcb2.png";

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
        user.setRegisterMobile(userLoginInfoVo.getPhone());
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
        if (StringUtils.isBlank(userLoginInfoVo.getLogoUrl())) {
            user.setLogoUrl(WX_DEFAULT_HEAD_IMG);
        } else {
            user.setLogoUrl(userLoginInfoVo.getLogoUrl());
        }
        user.setMemberLevel(MemberLevel.HY);
        user.setLevelName("会员");
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

    public static void main(String[] args) {
        String ss = "  ";
        System.out.println(CheckUtil.isNotEmpty(ss));
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
    public User getUserByGzhOpenIdAndMerchantId(String gzhOpenId, String merchantId) {
        if (StringUtils.isEmpty(gzhOpenId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入公众号");
        }
        if (StringUtils.isEmpty(merchantId)) {
            BSUtil.isTrue(Boolean.FALSE, "商户编号不能为空");
        }
        return userMapper.getUserByGzhOpenIdAndMerchantId(gzhOpenId, merchantId);
    }

    @Override
    public Boolean updateUserPhoneByUserId(String userId, String phone) {
        if (StringUtils.isEmpty(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "用户编号不能为空");
        }
        if (StringUtils.isEmpty(phone)) {
            BSUtil.isTrue(Boolean.FALSE, "手机号码不能为");
        }
        userMapper.updateUserPhoneByUserId(userId, phone);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateUserGzhOpenIdByUserId(String userId, String gzhOpenId) {
        if (StringUtils.isEmpty(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入用户编号");
        }
        if (StringUtils.isEmpty(gzhOpenId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要修改的公众号");
        }
        userMapper.updateUserGzhOpenIdByUserId(userId, gzhOpenId);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateUserWechatOpenidByUserId(String userId, String wechatOpenId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入用户编号");
        }
        if (StringUtils.isBlank(wechatOpenId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要修改的微信号");
        }
        userMapper.updateUserWechatOpenidByUserId(userId, wechatOpenId);
        return Boolean.TRUE;
    }

    @Override
    public Result miniAppWeChatAuthorization(UserLoginInfoVo userLoginInfoVo, String code, String merchantId) {
        log.info("====@@@@进入用户授权@@@@@==========");
        log.info("====@@@@推荐人传入参数为@@@@@==========：");
        if (null == code) {
            BSUtil.isTrue(Boolean.FALSE, "请输入微信授权code");
        }
        //兼容
        if (StringUtils.isBlank(userLoginInfoVo.getPhone())) {
            userLoginInfoVo.setPhone(userLoginInfoVo.getRegisterMobile());
        }
        if (StringUtils.isBlank(userLoginInfoVo.getPhone())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入手机号码");
        }
        if (null == merchantId) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要登录的小程序商户号");
        }
        boolean msgVerfig = msgSenderService.checkCode(userLoginInfoVo.getPhone(), userLoginInfoVo.getMsgCode(), "3");
        if (!msgVerfig) {
            BSUtil.isTrue(Boolean.FALSE, "短信验证码错误");
        }
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
        if (!(map.get("errcode") == null)) {
            result.setCode(map.get("errcode").toString());
            result.setMessage("微信返回错误信息：" + map.get("errmsg").toString());
            return result;
        }
        String openid = map.get("openid").toString();
        Boolean firstLogin = Boolean.FALSE;//验证用户是否是第一次登录
        //先查询用户之前是否授权登录过
        User userQuery = new User();
        userQuery.setRegisterMobile(userLoginInfoVo.getPhone());
        userQuery.setMerchantId(merchantId);
        userQuery = getOneByParams(userQuery);
        if (userQuery == null) {//用户之前没授权登录过
            firstLogin = Boolean.TRUE;
            String userId = saveAuthorization(userLoginInfoVo, openid);
            userQuery = getByUserId(userId);
        } else {
            if (userQuery.getWechatOpenid() == null) {
                User user = this.getUserByOpenIdAndMerchantId(openid, merchantId);
                if (null != user) {
                    // 存量用户在H5登录了 创建了新的账号  旧的账号未同步
                    log.info(String.format("!!!!!!!!!!!!!!!!!!!!! 微信小程序USERID:%s 微信公众号USERID:%s 微信小程序OPENDID:%s  微信公众号OPENDID:%s  手机号码:%s", userQuery.getUserId(), user.getUserId(), openid, userQuery.getGzhOpenid(), userLoginInfoVo.getPhone()));
                    BSUtil.isTrue(Boolean.FALSE, "账号正在迁移中请先使用H5登录使用页");
                }
                //用户在H5登录过没在小程序登录
                firstLogin = Boolean.TRUE;
                updateUserWechatOpenidByUserId(userQuery.getUserId(), openid);
            } else {
                //userQuery.getWechatOpenid()==null 已排除
                //userQuery.getWechatOpenid()不等于现在的openid
                //userQuery.getWechatOpenid()等于现在的openid
                if (!userQuery.getWechatOpenid().equals(openid)) {
                    BSUtil.isTrue(Boolean.FALSE, String.format("手机号码为:%s 已经在其他公众号上注册过请换个号码", userLoginInfoVo.getPhone()));
                }
            }
            log.info("====@@@@用户之前已经授权登录过，userId为：@@@@@==========：" + userQuery.getUserId());
            modifyAuthorization(userLoginInfoVo, userQuery);//FIXME 这边涉及到更新推荐人 是否会更新空字符串
        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(userQuery.getUserId());
        String token = model.getSignToken();
        Map resultMap = new HashMap<String, Object>();
        resultMap.put("token", token);
        resultMap.put("userId", userQuery.getUserId());
        resultMap.put("firstLogin", firstLogin);
        result.setData(resultMap);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public H5LoginResultVo h5WeChatLogin(String phone, String msgCode, String merchantId, String wxAccreditCode) {
        log.info("h5WeChatLogin:phone->" + phone + "msgCode->" + msgCode + "merchantId->" + merchantId + "wxAccreditCode->" + wxAccreditCode);
        if (StringUtils.isEmpty(phone)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入手机号码");
        }
//        if (StringUtils.isEmpty(gzhOpenId)) {
//            BSUtil.isTrue(Boolean.FALSE, "请输入公众号open id");
//        }
        if (StringUtils.isEmpty(merchantId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入商户编号");
        }
        if (StringUtils.isEmpty(wxAccreditCode)) {
            BSUtil.isTrue(Boolean.FALSE, "微信授权code不能为空");
        }
        boolean msgVerfig = msgSenderService.checkCode(phone, msgCode, "3");
        if (!msgVerfig) {
            BSUtil.isTrue(Boolean.FALSE, "短信验证码错误");
        }
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        if (merchant == null) {
            BSUtil.isTrue(Boolean.FALSE, "商户不存在");
        }
        WxH5AccessTokenVo accessTokenVo = h5AppWeChatService.getH5AccessToken(merchant.getGzAppId(), merchant.getGzAppKey(), wxAccreditCode);
        if (null == accessTokenVo) {
            BSUtil.isTrue(Boolean.FALSE, "微信没有返回授权信息");
        }
        if (accessTokenVo.getErrcode() != null) {
//            return new H5LoginResultVo(null, null, null , accessTokenVo.getErrcode() , accessTokenVo.getErrmsg());
            log.error("通过微信code 获取openid出错 微信code:" + accessTokenVo.getErrcode() + " 微信错误信息:" + accessTokenVo.getErrmsg());
            BSUtil.isTrue(Boolean.FALSE, "微信授权code无效");
        }
        Boolean firstLogin = Boolean.FALSE;//验证用户是否是第一次登录
        User user = getUserByPhoneAndMerchantId(phone, merchantId);
        if (null == user) {
            //注册用户 设置成会员
            user = h5AccreditCreateUser(accessTokenVo.getOpenid(), merchantId, phone, MemberLevel.HY, "会员");
            firstLogin = Boolean.TRUE;
        } else {
            if (user.getGzhOpenid() == null) {
                //用户可能在小程序上登录有手机号码   但是gzh openId 为空也算是第一次登录公众号
                firstLogin = Boolean.TRUE;
                //设置用户公众号
                updateUserGzhOpenIdByUserId(user.getUserId(), accessTokenVo.getOpenid());
            } else {
                //验证公众号openId 是否一致
                if (!user.getGzhOpenid().equals(accessTokenVo.getOpenid())) {
                    BSUtil.isTrue(Boolean.FALSE, "该手机绑定的微信与现在的微信不一致");
                }
            }
        }
        //用户已存在获取微信用户信息更新数据库中的资料
        WxUserInfoVo userInfoVo = h5AppWeChatService.getWeChatUserInfo(accessTokenVo.getAccess_token(), accessTokenVo.getOpenid());
        h5AccreditSaveEditUserInfo(userInfoVo, user.getUserId());
        //设置用户手机号码
        // user.setRegisterMobile(phone);
        // updateUserByUserId(user);
        //设置token
        TokenModel model = tokenManager.createToken(user.getUserId());
        String token = model.getSignToken();
        return new H5LoginResultVo(token, user.getGzhOpenid(), user.getUserId(), firstLogin);
    }

    public User getUserByPhoneAndMerchantId(String phone, String merchantId) {
        if (StringUtils.isEmpty(phone)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要查询的手机号码");
        }
        if (merchantId == null) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要查询的商户号");
        }
        User user = new User();
        user.setRegisterMobile(phone);
        user.setMerchantId(merchantId);
        User sysUser = getOneByParams(user);
        return sysUser;
    }

    private User h5AccreditCreateUser(String openId, String merchantId, String phone, Integer memberLevel, String memberName) {
        if (null == openId) {
            BSUtil.isTrue(Boolean.FALSE, "opendId不能为空");
        }
        User user = new User();
        user.setUserId(UUIDUtil.getUUID32());
        user.setGzhOpenid(openId);
        user.setMerchantId(merchantId);
        user.setRegisterMobile(phone);
        user.setMemberLevel(memberLevel);
        user.setLevelName(memberName);
        user.setRegisterFrom(UserRegisterType.WECHAT_ACCREDIT.getValue());
        user.setIsBuy(0);
        addUser(user);
        return user;
    }

    private UserInfo h5AccreditSaveEditUserInfo(WxUserInfoVo userInfoVo, String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        String nickName = userInfoVo.getNickname();
        if (nickName != null && nickName.length() > 0) {
            nickName = nickName.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        }
        userInfo.setNickName(nickName);
        userInfo.setLogoUrl(StringUtils.isBlank(userInfoVo.getHeadimgurl()) ? WX_DEFAULT_HEAD_IMG : userInfoVo.getHeadimgurl());
        UserInfo sysUserInfo = userInfoService.getByUserId(userId);
        if (sysUserInfo == null) {//验证数据库中是否有用户信息
            userInfo.setAvailable(1);
            userInfoService.add(userInfo);
        } else {
            userInfoService.modifyByParams(userInfo);
        }
        //同步更新用户USER表头像   USER表中做了头像的 冗余存储
        User user = new User();
        user.setUserId(userId);
        user.setLogoUrl(userInfo.getLogoUrl());
        this.updateUserByUserId(user);
        return userInfo;
    }

    private UserInfo h5AccreditUpdateUserInfo(WxUserInfoVo userInfoVo, String userId) {
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
