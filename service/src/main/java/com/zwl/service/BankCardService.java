package com.zwl.service;

import com.zwl.model.po.BankCard;

/**
 * 用户银行卡
 */
public interface BankCardService {

    /**
     * <br> 获取用户设置的银行卡信息   目前设置的用户的银行卡只会有一条 </br>
     *
     * @param userId 用户编号
     */
    BankCard getBankCardByUserId(String userId);

    /**
     * <br> 获取用户设置的银行卡信息   目前设置的用户的银行卡只会有一条 </br>
     * <br> 如果用户没有绑定卡号 则返回一个非空的对象  非空对象中设置真实姓名</br>
     *
     * @param userId 用户编号
     */
    BankCard getBankCardBaseInfoUserId(String userId);

    /**
     * 保存用户银行卡信息
     * <br>若 用户已有银行卡信息就更新 银行卡信息<br>
     * <br>否则  添加一条新的记录</br>
     */
    BankCard saveBankCard(BankCard bankCard);
}
