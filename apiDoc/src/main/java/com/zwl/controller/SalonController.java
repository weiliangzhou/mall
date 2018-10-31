package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.OfflineActivity;
import com.zwl.model.po.OfflineActivityOrder;
import com.zwl.model.po.OfflineActivityTheme;
import com.zwl.model.vo.ActivityCodeDetail;
import com.zwl.model.vo.OfflineActivityOrderVo;
import com.zwl.model.vo.UserVo;
import com.zwl.model.wxpay.WxPayVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "沙龙")
@RestController
public class SalonController {
    @RequestMapping(name = "沙龙主题列表",
            value = "/wx/salon/getSalonThemeList", method = RequestMethod.POST)
    public OfflineActivityTheme getSalonThemeList(@ApiComment("merchantId") String merchantId,
                                                  @ApiComment("queryType  0查询推荐列表 其他查询全部") String queryType,
                                                  @ApiComment("pageSize") String pageSize,
                                                  @ApiComment("pageNum") String pageNum,
                                                  @ApiComment("activityType 0线下课程 1沙龙") Integer activityType
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "沙龙主题详情介绍页",
            value = "/wx/salon/getSalonThemeDetailByThemeId", method = RequestMethod.POST)
    public OfflineActivityTheme getSalonThemeDetailByThemeId(@ApiComment("merchantId") String merchantId,
                                                             @ApiComment("主题id") String themeId
    ) {
        OfflineActivityTheme offlineActivityTheme = new OfflineActivityTheme();
        return offlineActivityTheme;
    }

    @RequestMapping(name = "沙龙主题详情购买",
            value = "/wx/salon/getSalonListByThemeId", method = RequestMethod.POST)
    public OfflineActivity getSalonListByThemeId(@ApiComment("merchantId") String merchantId,
                                                 @ApiComment("主题id") Integer activityThemeId
    ) {
        OfflineActivity offlineActivity = new OfflineActivity();
        return offlineActivity;
    }

    @RequestMapping(name = "沙龙线下报名",
            value = "/wx/salon/buy", method = RequestMethod.POST)
    public WxPayVo offlineActivityBuy(
            @ApiComment("activityId") Integer activityId,
            @ApiComment("realName") String realName,
            @ApiComment("0 男 1女") Integer sex,
            @ApiComment("city") String city,
            @ApiComment("phone") String phone,
            @ApiComment("idCardNum") String idCardNum,
            @ApiComment(value = "slReferrer", sample = "线下沙龙推荐人") String slReferrer,
            @ApiComment(value = "wechatNo", sample = "纯文本输入没什么意思") String wechatNo
    ) {
        WxPayVo wxPayVo = new WxPayVo();
        return wxPayVo;
    }

    @RequestMapping(name = "我的邀约人",
            value = "/wx/offlineActivity/getMySLActivityOrderList", method = RequestMethod.POST)
    public OfflineActivityOrderVo getActivityOrderList(@ApiComment("merchantId") String merchantId,@ApiComment("pageSize") String pageSize,
                                                       @ApiComment("pageNum") String pageNum
    ) {
        OfflineActivityOrderVo offlineActivityOrder = new OfflineActivityOrderVo();
        return offlineActivityOrder;
    }

    @RequestMapping(name = "我的邀约详情",
            value = "/wx/offlineActivity/getSLActivityOrderDetail", method = RequestMethod.POST)
    public OfflineActivityOrderVo getSLActivityOrderDetail(@ApiComment("orderNo") String orderNo
    ) {
        OfflineActivityOrderVo offlineActivityOrder = new OfflineActivityOrderVo();
        return offlineActivityOrder;
    }
    @RequestMapping(name = "线下沙龙报名信息",
            value = "/wx/offlineActivity/getSLUserInfo", method = RequestMethod.POST)
    public UserVo getSLUserInfo(@ApiComment("merchantId") String merchantId, @ApiComment("slReferrer") String slReferrer
    ) {
        UserVo userVo = new UserVo();
        return userVo;
    }
}
