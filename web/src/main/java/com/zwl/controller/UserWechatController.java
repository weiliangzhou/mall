package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.baseController.BaseController;
import com.zwl.model.po.UserWechat;
import com.zwl.service.UserWechatService;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/userWechat")
public class UserWechatController extends BaseController {

    @Autowired
    private UserWechatService userWechatService;

    /**
     * 查询用户绑定的微信帐号
     */
    @PostMapping("/getUserBindWechat")
    public String getUserBindWechat() {
        String userId = ThreadVariable.getUserID();
        UserWechat userWechat = userWechatService.getUserWechatByUserId(userId);
        return setSuccessResult(userWechat);
    }

    /**
     * 绑定用户微信号
     */
    @PostMapping("/bindUserWechat")
    public String bindUserWechat(@RequestBody JSONObject jsonObject) {
        String wechatAccount = jsonObject.getString("wechatAccount");
        UserWechat userWechat = userWechatService.saveUserWechat(wechatAccount);
        return setSuccessResult(userWechat);
    }
}
