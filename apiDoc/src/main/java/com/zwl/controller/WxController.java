package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.baseresult.Result;
import com.zwl.model.Information;
import com.zwl.model.MaidInfo;
import com.zwl.vo.UserLoginInfoVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/914:37
 */
@Api2Doc(name = "微信小程序")
@RestController
public class WxController {

    @ApiComment("购买")
    @RequestMapping(name = "购买",
            value = "/wx/product/buy", method = RequestMethod.POST)
    public String buy(@ApiComment("产品id") String productId, @ApiComment("微信商户号") String merchantId, @ApiComment("userId") String userId) {
        Result result = new Result();
        result.setData(0);
        return JSON.toJSONString(result);
    }

    @ApiComment("提现")
    @RequestMapping(name = "提现",
            value = "/wx/withdraw/apply", method = RequestMethod.POST)
    public Result apply(@ApiComment("收款方式 微信1") Short payWay, @ApiComment("收款账号") String account, @ApiComment("提现金额") Integer money, @ApiComment("userId") String userId, @ApiComment("商户号") String merchantId) {
        //       收款方式不能为空 -1
//        收款账号不能为空 -2
//        提现金额不能为空 -3
//        userId不能为空 -4
//        提现金额不能大于可用余额 -5
//        成功 0
//        失败1
//        未实名2
        Result result = new Result();
        return result;
    }

    @ApiComment("邀请列表")
    @RequestMapping(name = "邀请列表",
            value = "/wx/getMaidInfoList", method = RequestMethod.POST)
    public List<MaidInfo> getMaidInfoList(@ApiComment("userId") String userId) {
        List<MaidInfo> maidInfoList = new ArrayList<>();
        return maidInfoList;

    }

    @ApiComment("小程序授权登录")
    @RequestMapping(name = "授权登录", value = "/wx/user/authorization", method = RequestMethod.POST)
    public UserLoginInfoVo wechatLogin(
            @ApiComment("小程序授权微信登录code") String code,@ApiComment("商户号") String merchantId,
            @ApiComment("用户的微信手机号") String wechatMobile,@ApiComment("微信昵称") String nickName,
            @ApiComment("微信头像url") String logoUrl
          ) {
        Result result = new Result();
        //插入用户表

        //返回用户登录态
        String token = "fae1233gggwwef1==";
        String userId="df4de4b0034a4da8ab4c5b18da8ac718";
     /*   Map resultMap=new HashMap<String,Object>();
        resultMap.put("token",token);
        resultMap.put("userId","df4de4b0034a4da8ab4c5b18da8ac718");*/
//        result.setData(resultMap);
        UserLoginInfoVo u=new UserLoginInfoVo();
        u.setToken(token);
        u.setUserId(userId);
        return u;
    }

    @ApiComment("小程序获取用户信息")
    @RequestMapping(name = "获取用户信息", value = "/wx/user/getUserInfoByUserId", method = RequestMethod.GET)
    public UserLoginInfoVo getUserInfoByUserId(@RequestParam("userId") String userId) {
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        userLoginInfoVo.setNickName("我是一只小小鸟");
        userLoginInfoVo.setLogoUrl("https://wx.qlogo.cn/mmopen/vi_32/pM9miba3MPibic2cxVdbSZlEneEOKPXTqqwVwGjOwDGLXkwj049fbgPLG4HfPMedjsiaekpITiagEw5P19jIVY0ZGxw/132");
        return userLoginInfoVo;
    }

    @ApiComment(value = "获取资讯列表",seeClass = Information.class)
    @RequestMapping(name = "获取资讯列表",
            value = "/wx/getInformationList", method = RequestMethod.POST)
    public Information getInformationList(@ApiComment("pageNum") Integer pageNum,@ApiComment("pageSize") Integer pageSize,@ApiComment("商户号") String merchantId) {
        Information information=new Information();
        return information;
    }

}
