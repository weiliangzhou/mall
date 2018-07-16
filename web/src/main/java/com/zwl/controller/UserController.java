package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.Merchant;
import com.zwl.model.po.TokenModel;
import com.zwl.model.po.User;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.UserLoginInfoVo;
import com.zwl.service.MerchantService;
import com.zwl.service.MiniAppWeChatService;
import com.zwl.service.UserInfoService;
import com.zwl.service.UserService;
import com.zwl.serviceimpl.RedisTokenManagerImpl;
import com.zwl.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户controller
 */
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

    /**
     * 用户小程序微信授权登录
     */
    @PostMapping("/authorization")
    public Result authorization(@RequestBody UserLoginInfoVo userLoginInfoVo) {
        Result result = new Result();
        //根据merchantid获取appid和secret
        Merchant merchant = merchantService.getMerchantByMerchantId(userLoginInfoVo.getMerchantId());
        if (merchant == null) {
            result.setCode("205");
            result.setMessage("商户不存在");
            return result;
        }
        //根据code获取openid 去掉数据库appid和appsecret的空格和换行等
        String resultStr = miniAppWeChatService.authorizationCode(userLoginInfoVo.getCode(), merchant.getAppId(), merchant.getAppSecret());
        if (StringUtils.isEmpty(resultStr)) {
            result.setCode("205");
            result.setMessage("获取不到微信用户信息");
        }
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
        userQuery=userService.getOneByParams(userQuery);

        String userId = null;
        //用户信息（头像、昵称等）
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(userLoginInfoVo.getNickName());
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
//        user.setMemberLevel("0");
            //插入用户表
            userService.addUser(user);
            userInfo.setUserId(userId);
//            userInfo.setAvailable(1);
            //插入用户详情表
           userInfoService.add(userInfo);


        } else {
            userId = userQuery.getUserId();
            userInfo.setUserId(userId);
            userInfoService.modifyByParams(userInfo);
            result.setMessage("更新用户头像昵称成功！！");
        }
        //返回用户登录态
        TokenModel model = tokenManager.createToken(userId);
        String token = model.getSignToken();
        Map resultMap=new HashMap<String,Object>();
        resultMap.put("token",token);
        resultMap.put("userId",userId);
        result.setData(resultMap);
        return result;
    }

    //购买前绑定手机号
    public Result Register(@RequestParam("registerMobile") String registerMobile) {
        Result result = new Result();
        //插入用户详情表

        return result;
    }

    /**
     * 用户信息展示
     */
    @RequestMapping("/getUserInfoByUserId")
    public Result getUserInfoByUserId(@RequestBody JSONObject jsonObject) {
        String userId=jsonObject.getString("userId");
        Result result = new Result();
        //根据UserId查找用户详情表
        UserInfo userInfo = userInfoService.getByUserId(userId);
        if (StringUtils.isEmpty(userInfo)) {
            result.setCode(ResultCodeEnum.FAIL);
            return result;
        }
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        userLoginInfoVo.setNickName(userInfo.getNickName());
        userLoginInfoVo.setLogoUrl(userInfo.getLogoUrl());
        result.setData(userLoginInfoVo);
        return result;
    }

    /**
     * 用户信息
     */
    public Result getUserInfoById() {
        Result result = new Result();
        //根据id查找用户信息


        return result;
    }


}
