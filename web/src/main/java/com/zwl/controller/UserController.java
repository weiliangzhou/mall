package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.vo.UserLoginInfoVo;
import com.zwl.service.*;
import com.zwl.serviceimpl.RedisTokenManagerImpl;
import com.zwl.util.CheckUtil;
import com.zwl.util.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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


    /**
     * 用户小程序微信授权登录
     */
    @PostMapping("/authorization")
    public Result authorization(@RequestBody UserLoginInfoVo userLoginInfoVo) {
        log.info("====@@@@进入用户授权@@@@@==========");
        log.info("====@@@@推荐人传入参数为@@@@@==========：" + userLoginInfoVo.getReferrer());
        Result result = new Result();
        //根据merchantid获取appid和secret
        Merchant merchant = merchantService.getMerchantByMerchantId(userLoginInfoVo.getMerchantId());
        if (merchant == null)
            BSUtil.isTrue(false, "商户不存在");
        //根据code获取openid 去掉数据库appid和appsecret的空格和换行等
        String resultStr = miniAppWeChatService.authorizationCode(userLoginInfoVo.getCode(), merchant.getAppId(), merchant.getAppSecret());
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
        userQuery.setMerchantId(userLoginInfoVo.getMerchantId());
        userQuery = userService.getOneByParams(userQuery);
        //先查询用户之前是否线下导入过


        String userId = null;
        //用户信息（头像、昵称等）
        UserInfo userInfo = new UserInfo();
        String nickName = userLoginInfoVo.getNickName();
        if (nickName != null && nickName.length() > 0) {
            nickName = nickName.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        }
        userInfo.setNickName(nickName);
        userInfo.setLogoUrl(userLoginInfoVo.getLogoUrl());
        userInfo.setAvailable(1);
        if (StringUtils.isEmpty(userQuery)) {
            //unionid可能为空
//        String unionId = map.get("unionid").toString();
            //1、微信授权的 2、线下导入的 3、手机号注册的
            userId = UUIDUtil.getUUID32();
            User user = new User();
            user.setWechatOpenid(openid);
            user.setUserId(userId);
            user.setMerchantId(userLoginInfoVo.getMerchantId());
            user.setRegisterFrom(1);
            //推荐人userId 推荐人必须购买过
           /* if (StringUtils.isEmpty(userLoginInfoVo.getReferrer()))
                user.setReferrer("admin");*/
            String referrer = userLoginInfoVo.getReferrer();
            if (CheckUtil.isNotEmpty(referrer)) {
                User userIsBuy = userService.getByUserId(referrer);
//                if (userIsBuy.getIsBuy() != null && userIsBuy.getMemberLevel() > 1) {
                if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                    user.setReferrer(userLoginInfoVo.getReferrer());
                    log.info("==============@@@@@@@@新增用户 开始 分享绑定上下级关系@@用户推荐人referrer：" + "为" + referrer);
                }
            }
            user.setIsBuy(0);
            user.setMemberLevel(0);
            //插入用户表
            user.setLogoUrl(userLoginInfoVo.getLogoUrl());
            userService.addUser(user);
            userInfo.setUserId(userId);
            userInfo.setAvailable(1);
            //插入用户详情表
            userInfoService.add(userInfo);

        } else {
            //如果用户还未购买，则可以更新推荐人
            if ((userQuery.getIsBuy() == null || userQuery.getIsBuy() == 0) && CheckUtil.isNotEmpty(userLoginInfoVo.getReferrer())) {
                User user = new User();
                user.setUserId(userQuery.getUserId());
                //推荐人userId 推荐人必须购买过
                String referrer = userLoginInfoVo.getReferrer();
                if (CheckUtil.isNotEmpty(referrer)) {
                    User userIsBuy = userService.getByUserId(referrer);
//                    if (userIsBuy.getIsBuy() != null && userIsBuy.getMemberLevel() > 1) {
                    if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                        user.setReferrer(userLoginInfoVo.getReferrer());
                        log.info("==============@@@@@@@@ 更新 开始 分享绑定上下级关系@@用户推荐人referrer：" + "为" + referrer);
                        userService.updateUserByUserId(user);
                    }
                }
            }

            userId = userQuery.getUserId();
            userInfo.setUserId(userId);
            //防止数据库 把用户信息表给删除了
            UserInfo queryUserInfo = userInfoService.getByUserId(userId);
            if (queryUserInfo == null) {
                userInfo.setUserId(userId);
                userInfo.setAvailable(1);
                //插入用户详情表
                userInfoService.add(userInfo);
            } else {
                userInfoService.modifyByParams(userInfo);
            }
            //用户主表头像也更新
            if (CheckUtil.isNotEmpty(userLoginInfoVo.getLogoUrl())) {
                User user = new User();
                user.setUserId(userQuery.getUserId());
                user.setLogoUrl(userLoginInfoVo.getLogoUrl());
                userService.updateUserByUserId(user);
            }
            result.setMessage("更新用户头像昵称成功！！");
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

    //购买前绑定手机号
    @PostMapping("/bindingMobile")
    public Result bindMobile(@RequestBody JSONObject jsonObject) {
        String phone = jsonObject.getString("phone");
        String msgCode = jsonObject.getString("msgCode");
        String userId = jsonObject.getString("userId");
        log.info("===========用户userid:===========：" + userId);
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


    /**
     * 用户信息展示
     */
    @PostMapping("/getUserInfoByUserId")
    public Result getUserInfoByUserId(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Result result = new Result();
        //根据UserId查找用户详情表
        UserInfo userInfo = userInfoService.getByUserId(userId);
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        if(userInfo!=null){
            userLoginInfoVo.setNickName(userInfo.getNickName());
            userLoginInfoVo.setLogoUrl(userInfo.getLogoUrl());
        }

        User user = userService.getByUserId(userId);
        if (user == null) {
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无用户，请校验userId");
            return result;
        }
        Integer memberLevel = user.getMemberLevel();
        String levelName;
        //存在 0会员 游客等级
//        if (memberLevel == 0) {
//            levelName = "会员";
//        } else
        if (memberLevel == 0 || memberLevel == null) {
            levelName = "游客";
        } else {
            Product product = productService.getProductByMemberLevel(memberLevel);
            levelName = product.getLevelName();
        }
        userLoginInfoVo.setMemberLevel(memberLevel);
        userLoginInfoVo.setLevelName(levelName);
//        userLoginInfoVo.setIsBindMobile(userInfo.getIsBindMobile()==null?0:1);
        //通过主表获取绑定手机号
        userLoginInfoVo.setIsBindMobile(user.getRegisterMobile() == null ? 0 : 1);
        userLoginInfoVo.setRegisterMobile(user.getRegisterMobile());
        userLoginInfoVo.setIsCertification(userInfo.getIsCertification() == null ? 0 : 1);

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
        String merchantId = jsonObject.getString("merchantId");
        log.info("==============@@@@@@@@分享绑定上下级关系@@用户" + userId + "推荐人referrer:" + referrer);
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
                result.setMessage("推荐人不存在，请检查referrer");
                return result;
            }
//            if (userIsBuy.getIsBuy() != null && userIsBuy.getMemberLevel() > 1) {
            if (userIsBuy.getIsBuy() != null && userIsBuy.getIsBuy() == 1) {
                user.setReferrer(referrer);
                userService.updateUserByUserId(user);
            }
        }

        return result;
    }
}
