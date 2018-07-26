package com.zwl.service;

import com.zwl.model.po.UserAccount;

/**
 * @author 二师兄超级帅
 * @Title: UserAccount
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1311:47
 */
public interface UserAccountService {
    void addBanlanceByUserId(String userId, Integer newMaidMoney);

    Integer getBalanceByUserId(String userId);

    UserAccount getUserAccountByUserId(String referrerId);

    int save(UserAccount userAccount_t);
}
