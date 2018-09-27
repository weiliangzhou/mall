package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.H5Buy;
import com.zwl.model.po.OfflineActivityCode;
import com.zwl.model.po.Product;
import com.zwl.model.vo.SignInVo;
import com.zwl.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 二师兄超级帅
 * @Title: 线下活动
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2617:14
 */
@RestController
@Slf4j
@RequestMapping("/wx/offlineActivity")
public class OfflineActivityController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private UserService userService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private OfflineActivityCodeService offlineActivityCodeService;

    @PostMapping("/buy")
    public String offlineActivityBuy(HttpServletRequest request, @Validated(H5Buy.class) @RequestBody Product product) {
        Result result = new Result();
//        BuyResult buyResult = productService.offlineActivityBuy(product);
//        String orderNo = buyResult.getOrderNo();
//        Integer totalFee = buyResult.getTotalFee();
//        String merchantId = buyResult.getMerchantId();
//        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
//        String gzhAppId = merchant.getGzAppId();
//        String userId_local = product.getUserId();
//        User user = userService.getByUserId(userId_local);
//        String wxPayKey = merchant.getWxPayKey();
//        String realIp = IpKit.getRealIp(request);
//        if (StrKit.isBlank(realIp)) {
//            realIp = "127.0.0.1";
//        }
//        WxPayVo wxPayVo = wxPayService.pay(realIp, user.getGzhOpenid(), orderNo, totalFee.toString(), gzhAppId, merchantId, wxPayKey);
//        result.setData(wxPayVo);
        return JSON.toJSONString(result);
    }


    @PostMapping("/signIn")
    public Result signIn(@Validated(Buy.class) @RequestBody SignInVo signInVo) {
        //获取操作员
        String operator = signInVo.getOperator();
        if (!"weigu".equals(operator))
            BSUtil.isTrue(false, "非法操作!");
        //获取code
        String activityCode = signInVo.getActivityCode();
        //通过code 做一个比对
        //如果正确则更新ss_offline_activity_code
        OfflineActivityCode offlineActivity = offlineActivityCodeService.getOneByActivityCode(activityCode);
        if (offlineActivity == null)
            BSUtil.isTrue(false, "非法code!");
        else{
            OfflineActivityCode  offlineActivityCode=offlineActivityCodeService.getOneByActivityCode(activityCode);
            Integer activityId=offlineActivityCode.getActivityId();
            offlineActivityCodeService.updatePassByActivityCode(activityCode,activityId);
        }
        Result result = new Result();
        return result;

    }


}
