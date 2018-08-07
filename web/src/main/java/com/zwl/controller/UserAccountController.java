package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: 获取公众号openid
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2415:12
 */
@RestController
@RequestMapping("/wx/useraccount")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/getBalance")
    public Result getBalance(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer balance = userAccountService.getBalanceByUserId(userId);
        Result result = new Result();
        result.setData(balance == null ? 0 : balance);
        return result;
    }

}
