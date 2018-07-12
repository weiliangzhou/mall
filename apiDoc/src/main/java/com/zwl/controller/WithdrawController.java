package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.baseresult.Result;
import com.zwl.model.Withdraw;
import com.zwl.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Api2Doc(name = "提现管理")
@ApiComment(seeClass = Withdraw.class)
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    @ApiComment("提现列表")
    @RequestMapping(name = "提现列表",
            value = "/admin/getWithdrawList", method = RequestMethod.POST)
    public List<Withdraw> getWithdrawList(@ApiComment("pageNum") Integer pageNum,@ApiComment("pageSize") Integer pageSize) {
        List<Withdraw> withdrawList = withdrawService.getWithdrawList();
        return withdrawList;
    }

    @ApiComment("提现审核")
    @RequestMapping(name = "提现审核",
            value = "/admin/approveWithdraw", method = RequestMethod.POST)
    public String approveWithdraw(@ApiComment("0审核中 1审核通过 2未通过") Short status, @ApiComment("审核人") String operator, @ApiComment("提现业务id") Long withdrawId) {
        Result result = new Result();
        return JSON.toJSONString(result);
    }


}
