package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.*;
import com.zwl.model.vo.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
            value = "/wx/product/auth/buy", method = RequestMethod.POST)
    public BuyResult buy(@ApiComment("产品id") String id, @ApiComment("微信商户号") String merchantId, @ApiComment("userId") String userId) {
        BuyResult buyResult = new BuyResult();
        return buyResult;
    }

    @ApiComment("支付")
    @RequestMapping(name = "支付",
            value = "/wx/pay/auth/pay.do", method = RequestMethod.POST)
    public String pay(@ApiComment("openId") String openId, @ApiComment("订单号") String orderNo, @ApiComment("totalFee") String totalFee, @ApiComment("merchantId") String merchantId) {
        Result result = new Result();
        result.setData(0);
        return JSON.toJSONString(result);
    }

    @ApiComment("提现")
    @RequestMapping(name = "提现",
            value = "/wx/withdraw/auth/apply", method = RequestMethod.POST)
    public Result apply(@ApiComment("收款方式 微信1") Integer payWay, @ApiComment("收款账号") String account, @ApiComment("提现金额") Integer money, @ApiComment("userId") String userId, @ApiComment("商户号") String merchantId) {
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

    @ApiComment("提现列表")
    @RequestMapping(name = "提现列表",
            value = "/wx/withdraw/auth/getWithdrawList", method = RequestMethod.POST)
    public List<Withdraw> getWithdrawList(@ApiComment("userId") String userId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        List<Withdraw> withdrawList = new ArrayList<>();
        return withdrawList;
    }


    @ApiComment("邀请列表")
    @RequestMapping(name = "邀请列表",
            value = "/wx/maidInfo/auth/getMaidInfoList", method = RequestMethod.POST)
    public MaidInfoVVo getMaidInfoList(@ApiComment("userId") String userId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        MaidInfoVVo maidInfoVVo = new MaidInfoVVo();
        return maidInfoVVo;

    }

    @ApiComment("小程序授权登录")
    @RequestMapping(name = "授权登录", value = "/wx/user/authorization", method = RequestMethod.POST)
    public UserLoginInfoVo wechatLogin(
            @ApiComment("小程序授权微信登录code") String code, @ApiComment("商户号") String merchantId,
            @ApiComment("用户的微信手机号") String wechatMobile, @ApiComment("微信昵称") String nickName,
            @ApiComment("微信头像url") String logoUrl, @ApiComment("推荐人userId") String referrer
    ) {
        Result result = new Result();
        //插入用户表

        //返回用户登录态
        String token = "fae1233gggwwef1==";
        String userId = "df4de4b0034a4da8ab4c5b18da8ac718";
     /*   Map resultMap=new HashMap<String,Object>();
        resultMap.put("token",token);
        resultMap.put("userId","df4de4b0034a4da8ab4c5b18da8ac718");*/
//        result.setData(resultMap);
        UserLoginInfoVo u = new UserLoginInfoVo();
        u.setToken(token);
        u.setUserId(userId);
        return u;
    }

    @ApiComment("小程序获取用户信息")
    @RequestMapping(name = "获取用户信息", value = "/wx/user/auth/getUserInfoByUserId", method = RequestMethod.GET)
    public UserLoginInfoVo getUserInfoByUserId(@RequestParam("userId") String userId) {
        UserLoginInfoVo userLoginInfoVo = new UserLoginInfoVo();
        userLoginInfoVo.setNickName("我是一只小小鸟");
        userLoginInfoVo.setLogoUrl("https://wx.qlogo.cn/mmopen/vi_32/pM9miba3MPibic2cxVdbSZlEneEOKPXTqqwVwGjOwDGLXkwj049fbgPLG4HfPMedjsiaekpITiagEw5P19jIVY0ZGxw/132");
        return userLoginInfoVo;
    }

    @ApiComment(value = "获取资讯列表", seeClass = Information.class)
    @RequestMapping(name = "获取资讯列表",
            value = "/wx/information/getInformationList", method = RequestMethod.POST)
    public Information getInformationList(@ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize, @ApiComment("商户号") String merchantId) {
        Information information = new Information();
        return information;
    }

    @ApiComment(value = "用户上传实名认证信息", seeClass = UserCertification.class)
    @RequestMapping(name = "用户上传实名认证信息",
            value = "/wx/certification/add", method = RequestMethod.POST)
    public Result addCertification(@ApiComment("realname") String realname, @ApiComment("idCard") String idCard,
                                   @ApiComment("img1Url") String img1Url, @ApiComment("img2Url") String img2Url,
                                   @ApiComment("img3Url") String img3Url, @ApiComment("userId") String userId,
                                   @ApiComment("merchantId") String merchantId) {
        Result result = new Result();

        return result;
    }

    @ApiComment(value = "根据userId查询用户提交的实名认证信息", seeClass = UserCertification.class)
    @RequestMapping(name = "根据userId查询用户提交的实名认证信息",
            value = "/wx/certification/getOneByUserId", method = RequestMethod.POST)
    public UserCertification getOneCertificationByUserId(@ApiComment("userId") String userId) {
        Result result = new Result();
        UserCertification u = new UserCertification();
        return u;
    }

    @ApiComment(value = "修改用户自己提交的实名信息", seeClass = UserCertification.class)
    @RequestMapping(name = "修改用户自己提交的实名信息",
            value = "/wx/certification/modifyById", method = RequestMethod.POST)
    public Result modifyCertificationById(@ApiComment("realname") String realname, @ApiComment("idCard") String idCard,
                                          @ApiComment("img1Url") String img1Url, @ApiComment("img2Url") String img2Url,
                                          @ApiComment("img3Url") String img3Url, @ApiComment("id") String id) {
        Result result = new Result();

        return result;
    }

    @ApiComment("课程列表")
    @RequestMapping(name = "课程列表",
            value = "/wx/classset/getPageAllClass", method = RequestMethod.POST)
    public PageClassVo getPageAllClass(@ApiComment("merchantId") String merchantId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        PageClassVo pageClassVo = new PageClassVo();
        return pageClassVo;
    }

    @ApiComment("根据id查询套课程")
    @RequestMapping(name = "根据id查询套课程",
            value = "/wx/classset/getById", method = RequestMethod.POST)
    public ClassSet getById(@ApiComment("id") Long id) {
        ClassSet classSet = new ClassSet();
        return classSet;
    }

    @ApiComment("获取套课程下的节课程列表")
    @RequestMapping(name = "获取套课程下的节课程列表",
            value = "/wx/classinfo/getPageByClassSetId", method = RequestMethod.POST)
    public PageClassInfoVo getPageByClassSetId(@ApiComment("classSetId") Long classSetId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        Result result = new Result();
        PageClassInfoVo pageVo = new PageClassInfoVo();

        return pageVo;
    }

    @ApiComment("根据Id获取节课程")
    @RequestMapping(name = "根据Id获取节课程",
            value = "/wx/classinfo/getById", method = RequestMethod.POST)
    public ClassInfo getClassinfoById(@ApiComment("id") Long id) {

        ClassInfo classInfo = new ClassInfo();
        return classInfo;
    }

    @ApiComment("套课程收听人数+1")
    @RequestMapping(name = "套课程收听人数+1",
            value = "//wx/classset/setpAddBrowseCount", method = RequestMethod.POST)
    public Result setpAddClasssetBrowseCount(@ApiComment("套课程id") Long classSetId) {
        Result result = new Result();
        return result;
    }

    @ApiComment("节课程收听人数+1")
    @RequestMapping(name = "节课程收听人数+1",
            value = "/wx/classinfo/setpAddBrowseCount", method = RequestMethod.POST)
    public Result setpAddClassinfoBrowseCount(@ApiComment("节课程id") Long classInfoId) {
        Result result = new Result();
        return result;
    }

    @ApiComment("发送验证码")
    @RequestMapping(name = "发送验证码",
            value = "/wx/user/sendRegisterCode", method = RequestMethod.POST)
    public Result sendRegisterCode(@ApiComment("phone") String phone) {
        Result result = new Result();
        return result;
    }

    @ApiComment("绑定手机")
    @RequestMapping(name = "绑定手机",
            value = "/wx/user/bindingMobile", method = RequestMethod.POST)
    public Result sendCode(@ApiComment("phone") String phone, @ApiComment("userId") String userId, @ApiComment("msgCode") String msgCode) {
        Result result = new Result();
        return result;
    }

    @ApiComment("发送公众号openid")
    @RequestMapping(name = "发送公众号openid",
            value = "/gzh/sendFormId", method = RequestMethod.POST)
    public Result sendFormId(@ApiComment("formId") String formId, @ApiComment("userId") String userId) {
        Result result = new Result();
        return result;
    }

    @ApiComment("获取公众号openid")
    @RequestMapping(name = "获取公众号openid",
            value = "/gzh/getFormId", method = RequestMethod.POST)
    public Result getFormId(@ApiComment("userId") String userId) {
        Result result = new Result();
        return result;
    }

    @ApiComment("获取可用余额")
    @RequestMapping(name = "获取可用余额",
            value = "/wx/useraccount/getBalance", method = RequestMethod.POST)
    public Result getBalance(@ApiComment("userId") String userId) {
        Result result = new Result();
        return result;
    }


}
