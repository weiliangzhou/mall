package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.baseresult.Result;
import com.zwl.baseresult.ResultCodeEnum;
import com.zwl.groups.ApplyWithdraw;
import com.zwl.model.Withdraw;
import com.zwl.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/wx/withdraw/apply")
    public String apply(@Validated(ApplyWithdraw.class) @RequestBody Withdraw withdraw) {
        Result result = new Result();
         withdrawService.apply(withdraw);
        return JSON.toJSONString(result);
    }



}
