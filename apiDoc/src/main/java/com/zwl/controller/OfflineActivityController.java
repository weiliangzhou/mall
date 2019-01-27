package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.OfflineActivity;
import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.OfflineActivityTheme;
import com.zwl.model.vo.ActivityCodeDetail;
import com.zwl.model.vo.OfflineActivityOrderVo;
import com.zwl.model.wxpay.WxPayVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: 线下活动
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/2617:14
 */
@Api2Doc(name = "线下活动")
@RestController
public class OfflineActivityController {
    @RequestMapping(name = "线下报名",
            value = "/wx/offlineActivity/buy", method = RequestMethod.POST)
    public WxPayVo offlineActivityBuy(
            @ApiComment("activityId") Integer activityId,
            @ApiComment("realName") String realName,
            @ApiComment("0 男 1女") Integer sex,
            @ApiComment("city") String city,
            @ApiComment("phone") String phone,
            @ApiComment("idCardNum") String idCardNum
    ) {
        WxPayVo wxPayVo = new WxPayVo();
        return wxPayVo;
    }

    @RequestMapping(name = "操作员签到",
            value = "/wx/offlineActivity/offlineLogin", method = RequestMethod.POST)
    public Result operatorSignIn(@ApiComment("操作员") String operator, @ApiComment("密码") Integer password, @ApiComment("商户号") String merchantId) {
        Result result = new Result();
        return result;

    }

    @RequestMapping(name = "线下活动签到",
            value = "/wx/offlineActivity/signIn", method = RequestMethod.POST)
    public Result signIn(@ApiComment("activityCode") Integer activityCode,
                         @ApiComment("操作员") String operator) {
        Result result = new Result();
        return result;

    }

    @RequestMapping(name = "线下活动主题列表",
            value = "/wx/offlineActivity/getOfflineActivityThemeList", method = RequestMethod.POST)
    public OfflineActivityTheme getOfflineActivityThemeList(@ApiComment("merchantId") String merchantId,
                                                            @ApiComment("queryType  0查询推荐列表 其他查询全部") String queryType,
                                                            @ApiComment("pageSize") String pageSize,
                                                            @ApiComment("pageNum") String pageNum,
                                                            @ApiComment("activityType 0线下课程 1沙龙") Integer activityType
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "线下活动主题详情购买",
            value = "/wx/offlineActivity/getOfflineActivityListByThemeId", method = RequestMethod.POST)
    public OfflineActivity getOfflineActivityListByThemeId(@ApiComment("merchantId") String merchantId,
                                                           @ApiComment("主题id") Integer activityThemeId
    ) {
        OfflineActivity offlineActivity = new OfflineActivity();
        return offlineActivity;
    }

    @RequestMapping(name = "线下活动主题详情介绍页",
            value = "/wx/offlineActivity/getOfflineActivityThemeDetailByThemeId", method = RequestMethod.POST)
    public OfflineActivityTheme getOfflineActivityThemeDetailByThemeId(@ApiComment("merchantId") String merchantId,
                                                                       @ApiComment("主题id") String themeId
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "线下签到详情页",
            value = "/wx/offlineActivity/getActivityCodeDetail", method = RequestMethod.POST)
    public ActivityCodeDetail getActivityCodeDetail(@ApiComment("activityCode") String activityCode
    ) {
        ActivityCodeDetail activityCodeDetail = new ActivityCodeDetail();
        return activityCodeDetail;
    }

    @RequestMapping(name = "获取订单列表",
            value = "/wx/offlineActivity/getActivityOrderList", method = RequestMethod.POST)
    public OfflineActivityOrderVo getActivityOrderList(@ApiComment("pageSize") String pageSize,
                                                       @ApiComment("pageNum") String pageNum,
                                                        @ApiComment("merchantId") String merchantId
    ) {
        OfflineActivityOrderVo offlineActivityOrder = new OfflineActivityOrderVo();
        return offlineActivityOrder;
    }

    @RequestMapping(name = "获取订单详情",
            value = "/wx/offlineActivity/getActivityOrderDetail", method = RequestMethod.POST)
    public OfflineActivityOrderVo getActivityOrderDetail(@ApiComment("orderNo") String orderNo,
                                                         @ApiComment("activityId") String activityId
    ) {
        OfflineActivityOrderVo offlineActivityOrder = new OfflineActivityOrderVo();
        return offlineActivityOrder;
    }

}
