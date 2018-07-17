package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.groups.ApplyWithdraw;
import com.zwl.model.po.Withdraw;
import com.zwl.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: WithdrawController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/520:52
 */
@RestController
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    @PostMapping("/auth/wx/withdraw/apply")
    public String apply(@Validated(ApplyWithdraw.class) @RequestBody Withdraw withdraw) {
        Result result = new Result();
        withdrawService.apply(withdraw);
        return JSON.toJSONString(result);
    }

    @PostMapping("/auth/wx/withdraw/getWithdrawList")
    public String getWithdrawList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Result result = new Result();
        List<Withdraw> withdrawList = withdrawService.getWithdrawListByUserId(userId);
        result.setData(withdrawList);
        return JSON.toJSONString(result);
    }


}
