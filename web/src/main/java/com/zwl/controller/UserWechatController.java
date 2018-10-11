package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserWechat;
import com.zwl.service.UserWechatService;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx/userWechat")
public class UserWechatController {

    @Autowired
    private UserWechatService userWechatService;

    /**
     * 查询用户绑定的微信帐号
     */
    @GetMapping("/getUserBindWechat")
    public Result getUserBindWechat() {
        String userId = ThreadVariable.getUserID();
        UserWechat userWechat = userWechatService.getUserWechatByUserId(userId);

        Result result = new Result();
        result.setData(userWechat);
        return result;
    }
    /**
     *  绑定用户微信号
     */
    @PostMapping("/bindUserWechat")
    public Result bindUserWechat(@RequestBody JSONObject jsonObject){
        String wechatAccount = jsonObject.getString("wechatAccount");
        UserWechat userWechat = userWechatService.saveUserWechat(wechatAccount);

        Result result = new Result();
        result.setData(userWechat);
        return result;
    }
}
