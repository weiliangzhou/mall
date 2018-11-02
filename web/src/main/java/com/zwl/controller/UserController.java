package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Product;
import com.zwl.model.po.User;
import com.zwl.model.po.UserCertification;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.H5LoginResultVo;
import com.zwl.model.vo.UserLoginInfoVo;
import com.zwl.service.*;
import com.zwl.serviceimpl.RedisTokenManagerImpl;
import com.zwl.util.CheckUtil;
import com.zwl.util.PhoneUtil;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 */
@Slf4j
@RestController
@RequestMapping("/wx/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MiniAppWeChatService miniAppWeChatService;
    @Autowired
    private RedisTokenManagerImpl tokenManager;
    @Autowired
    private MsgSenderService msgSenderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户小程序微信授权登录
     */
    @PostMapping("/authorization")
    public Result authorization(@RequestBody UserLoginInfoVo userLoginInfoVo) {
        if (userLoginInfoVo == null) {
            BSUtil.isTrue(Boolean.FALSE, "参数错误");
        }
//        if (userLoginInfoVo.getBusCode() == null) {
//            BSUtil.isTrue(Boolean.FALSE, "请输入要授权的方式 1:小程序 2:H5页面授权");
//        }
        Result result = new Result();
        if (userLoginInfoVo.getBusCode() == null || userLoginInfoVo.getBusCode() == 1) {
            result = userService.miniAppWeChatAuthorization(userLoginInfoVo, userLoginInfoVo.getCode(), userLoginInfoVo.getMerchantId());
        } else if (userLoginInfoVo.getBusCode() == 2) {
            H5LoginResultVo resultVo = userService.h5WeChatLogin(userLoginInfoVo.getPhone(), userLoginInfoVo.getMsgCode(), userLoginInfoVo.getMerchantId(), userLoginInfoVo.getWxAccreditCode());
            result.setData(resultVo);
        }
        return result;
    }

    /**
     * 用户注销
     */
    @PostMapping("/loginOut")
    public Result loginOut(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String busCode = jsonObject.getString("busCode");
        Result result = new Result();
        //删除对应的redis
        stringRedisTemplate.delete(userId + busCode);
        return result;
    }

    //购买前绑定手机号
    @PostMapping("/bindingMobile")
    public Result bindMobile(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String msgCode = jsonObject.getString("msgCode");
        String userId = jsonObject.getString("userId");
//        需要手机号码防重
        User queryUser = new User();
        queryUser.setRegisterMobile(phone);
        User validate_user = userService.getOneByParams(queryUser);
        if (validate_user != null)
            BSUtil.isTrue(false, "已存在该手机号码");

        //  校验验证码
        boolean isValidate = msgSenderService.checkCode(phone, msgCode, "1");
        if (!isValidate)
            BSUtil.isTrue(false, "验证码错误");
        Result result = new Result();
        //更新用户表
        User user = new User();
        user.setUserId(userId);
        user.setRegisterMobile(phone);
        //绑定手机成为会员
        user.setMemberLevel(0);
        int count = userService.updateUserByUserId(user);
        if (count == 0)
            BSUtil.isTrue(false, "绑定失败");
        //更新用户详情表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRegisterMobile(phone);
        userInfo.setIsBindMobile(1);
        userInfoService.modifyByParams(userInfo);

        return result;
    }

    @PostMapping("/sendMsgCode")
    public Result sendRegisterCode(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String busCode = jsonObject.getString("busCode");
        msgSenderService.sendCode(phone, busCode);
        Result result = new Result();
        return result;
    }

    @PostMapping("/checkCode")
    public Result checkCode(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String busCode = jsonObject.getString("busCode");
        String code = jsonObject.getString("code");
        boolean flag = msgSenderService.checkCode(phone, code, busCode);
        if (!flag)
            BSUtil.isTrue(false, "验证码错误");
        Result result = new Result();
        return result;
    }


    /**
     * 用户信息展示
     */
    @PostMapping("/getUserInfoByUserId")
    public Result getUserInfoByUserId(@RequestBody JSONObject jsonObject) {
        String userId_local = ThreadVariable.getUserID();
        String userId_param = jsonObject.getString("userId");
        String userId = org.apache.commons.lang3.StringUtils.isBlank(userId_local) ? userId_param : userId_local;
        Result result = new Result();
        //根据UserId查找用户详情表
        UserInfo userInfo = userInfoService.getByUserId(userId);
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        String logoUrl = "";
        String defaultLogoUrl = "http://chuang-saas.oss-cn-hangzhou.aliyuncs.com/upload/image/20180911/6a989ec302994c6c98c2d4810f9fbcb2.png";
        if (userInfo != null) {
            userLoginInfoVo.setNickName(userInfo.getNickName());
            logoUrl = userInfo.getLogoUrl();
        }

        User user = userService.getByUserId(userId);
        if (user == null) {
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无用户，请校验userId");
            return result;
        } else
            logoUrl = StringUtils.isBlank(logoUrl) ? StringUtils.isBlank(user.getLogoUrl()) ? defaultLogoUrl : user.getLogoUrl() : logoUrl;
        userLoginInfoVo.setLogoUrl(logoUrl);
        Integer memberLevel = user.getMemberLevel();
        String levelName;
        if (null == memberLevel || memberLevel == -1) {
            levelName = "游客";
        } else if (memberLevel == 0) {
            levelName = "会员";
        } else {
            Product product = productService.getProductByMemberLevel(memberLevel, user.getMerchantId());
            levelName = product.getLevelName();
        }
        log.info("memberLevel::" + memberLevel);
        userLoginInfoVo.setMemberLevel(null == memberLevel ? -1 : memberLevel);
        userLoginInfoVo.setLevelName(levelName);
//        userLoginInfoVo.setIsBindMobile(userInfo.getIsBindMobile()==null?0:1);
        //通过主表获取绑定手机号
        userLoginInfoVo.setIsBindMobile(user.getRegisterMobile() == null ? 0 : 1);
        userLoginInfoVo.setRegisterMobile(user.getRegisterMobile() == null ? "" : PhoneUtil.replace(user.getRegisterMobile()));
//        userLoginInfoVo.setIsCertification(userInfo.getIsCertification() == null ? 0 : 1);
        //实名认证状态
        UserCertification userCertification = certificationService.getOneByUserId(userId);
        Integer certificationStatus = userCertification.getStatus();
        userLoginInfoVo.setCertificationStatus(certificationStatus);
        if (certificationStatus == 2) {
            userLoginInfoVo.setIdCardNum(userCertification.getIdCard());
        } else {
            userLoginInfoVo.setIdCardNum("");
        }
        Integer xiaxianCount = maidInfoService.getMaidInfoCount(userId);
        userLoginInfoVo.setXiaxianCount(null == xiaxianCount ? 0 : xiaxianCount);
        //账户余额
        Integer balance = userAccountService.getBalanceByUserId(userId);
        //余额：分转元
        balance = balance == null ? 0 : balance / 100;
        userLoginInfoVo.setBalance(balance);
        userLoginInfoVo.setRealName(StringUtils.isBlank(user.getRealName()) ? "" : user.getRealName());
        //增加性别、城市、姓名、身份证
        userLoginInfoVo.setGender(user.getGender());
        userLoginInfoVo.setProvince(user.getProvince());
        userLoginInfoVo.setCity(user.getCity());

        String referrerId = user.getReferrer();
        if (referrerId != null) {
            User referrerUser = userService.getByUserId(referrerId);
            if (referrerUser != null) {
                userLoginInfoVo.setReferrerName(referrerUser.getRealName());
            } else {
                userLoginInfoVo.setReferrerName("");
            }

        }

        result.setData(userLoginInfoVo);
        return result;
    }

    /**
     * 分享绑定上下级关系
     */
    @PostMapping("/shareRelation")
    public Result shareRelation(@RequestBody JSONObject jsonObject) {
        String referrer = jsonObject.getString("referrer");
        String userId = jsonObject.getString("userId");
//        String merchantId = jsonObject.getString("merchantId");
        log.info("====@@@@进入用户授权@@@@@==========userId：" + userId);
        log.info("====@@@@推荐人传入参数为@@@@@==========：" + referrer);
        Result result = new Result();
        User userQuery = userService.getByUserId(userId);
        if (userQuery == null) {
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无用户，请检查userId");
            log.error("==============@@@@@@@@分享绑定上下级关系@@用户推荐人referrer：" + "为" + referrer + "的用户的userid不存在");
            return result;
        }

        //如果用户还未购买，则可以更新推荐人
        if ((userQuery.getIsBuy() == null || userQuery.getIsBuy() == 0) && CheckUtil.isNotEmpty(referrer)) {
            User user = new User();
            user.setUserId(userQuery.getUserId());
            //推荐人userId 推荐人必须购买过
            User userIsBuy = userService.getByUserId(referrer);
            if (userIsBuy == null) {
                result.setCode(ResultCodeEnum.EXCEPTION);
                result.setMessage("推荐人不存在，请检查referrer！");
                return result;
            }
            if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                user.setReferrer(referrer);
                userService.updateUserByUserId(user);
            } else {
                result.setCode(ResultCodeEnum.EXCEPTION);
                result.setMessage("推荐人还未购买，不绑定关系！");
            }
        } else {
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("该用户已经购买，不再改绑推荐人！");
        }

        return result;
    }

    @PostMapping("/auth/saveUserInfo")
    public Result saveUserInfo(@RequestBody JSONObject jsonObject) {
        String province = jsonObject.getString("province");
        String city = jsonObject.getString("city");
        Integer gender = jsonObject.getInteger("gender");
        String userId = ThreadVariable.getUserID();
        User user = new User();
        if (StringUtils.isBlank(province) && StringUtils.isBlank(city)) {
            user.setProvince(province);
            user.setCity(city);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        user.setUserId(userId);
        userService.updateUserByUserId(user);
        return new Result();

    }
}
