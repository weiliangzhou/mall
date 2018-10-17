package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserWechat;
import com.zwl.service.UserWechatService;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api2Doc(name = "用户绑定微信")
@RestController
public class UserWechatController {

    @ApiComment("查询用户绑定的微信帐号")
    @RequestMapping(name = "查询用户绑定的微信帐号", value = "/wx/userWechat/getUserBindWechat", method = RequestMethod.POST)
    public Result getUserBindWechat() {
        Result result = new Result();
        return result;
    }

    @ApiComment("绑定微信账号")
    @RequestMapping(name = "绑定微信账号", value = "/wx/userWechat/bindUserWechat", method = RequestMethod.POST)
    public Result bindUserWechat(@ApiComment("微信账号") String wechatAccount) {
        Result result = new Result();
        return result;
    }
}
