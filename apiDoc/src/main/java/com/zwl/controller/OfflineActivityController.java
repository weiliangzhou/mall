package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.OfflineActivityBuy;
import com.zwl.model.wxpay.WxPayVo;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@Slf4j
public class OfflineActivityController {
    @ApiComment(seeClass = WxPayVo.class)
    @RequestMapping(name = "线下报名",
            value = "/wx/offlineActivity/buy", method = RequestMethod.POST)
    public WxPayVo offlineActivityBuy(@ApiComment(seeClass = OfflineActivityBuy.class) OfflineActivityBuy offlineActivityBuy) {
        WxPayVo wxPayVo = new WxPayVo();
        return wxPayVo;
    }


    @RequestMapping(name = "线下活动签到",
            value = "/wx/offlineActivity/signIn", method = RequestMethod.POST)
    public Result signIn(@ApiComment("activityCode") Integer activityCode,
                         @ApiComment("操作员") String operator) {
        Result result = new Result();
        return result;

    }


}
