package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.baseController.BaseController;
import com.zwl.model.groups.ApplyWithdraw;
import com.zwl.model.po.Withdraw;
import com.zwl.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/wx/withdraw")
public class WithdrawController extends BaseController {
    @Autowired
    private WithdrawService withdrawService;

    @PostMapping("/auth/apply")
    public String apply(@Validated(ApplyWithdraw.class) @RequestBody Withdraw withdraw) {
        withdrawService.apply(withdraw);
        return setSuccessResult();
    }

    @PostMapping("/auth/getWithdrawList")
    public String getWithdrawList(@RequestBody JSONObject jsonObject) {
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        String userId = jsonObject.getString("userId");
        List<Withdraw> withdrawList = withdrawService.getWithdrawListByUserId(userId);
        return setSuccessResult(withdrawList);
    }


}
