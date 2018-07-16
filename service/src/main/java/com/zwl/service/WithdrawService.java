package com.zwl.service;

import com.zwl.model.po.Withdraw;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: WithdrawService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/520:51
 */
public interface WithdrawService {

    void approveWithdraw(Integer status, String operator, String withdrawId, String realIp);

    void apply(Withdraw withdraw);

    List<Withdraw> getWithdrawList();

    int updateByWithdrawId(String partner_trade_no, String partner_trade_no1, String payment_no);
}
