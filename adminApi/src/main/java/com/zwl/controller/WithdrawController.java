package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Withdraw;
import com.zwl.model.vo.WithdrawVo;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @PostMapping("/admin/getWithdrawList")
    public String getWithdrawList(@RequestBody JSONObject jsonObject) {
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Result result = new Result();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Withdraw> withdrawList = withdrawService.getWithdrawList();
        WithdrawVo withdrawVo = new WithdrawVo();
        withdrawVo.setTotalPage(page.getTotal());
        withdrawVo.setPageNum(pageNum);
        withdrawVo.setWithdrawList(withdrawList);
        result.setData(withdrawVo);
        return JSON.toJSONString(result);
    }

    @PostMapping("/admin/approveWithdraw")
    public String approveWithdraw(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        Integer status = jsonObject.getInteger("status");
        String operator = jsonObject.getString("operator");
        String id = jsonObject.getString("id");
        String realIp = IpKit.getRealIp(request);
        if (StrKit.isBlank(realIp)) {
            realIp = "127.0.0.1";
        }
        Result result = new Result();
        withdrawService.approveWithdraw(status, operator, id, realIp);
        return JSON.toJSONString(result);
    }


}
