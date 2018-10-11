package com.zwl.serviceimpl;

import com.zwl.dao.mapper.BankCardMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.BankCard;
import com.zwl.model.po.User;
import com.zwl.service.BankCardService;
import com.zwl.service.UserService;
import com.zwl.util.ThreadVariable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    private BankCardMapper bankCardMapper;
    @Autowired
    private UserService userService;

    @Override
    public BankCard getBankCardByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要查询的用户编号");
        }
        BankCard bankCard = bankCardMapper.getBankCardByUserId(userId);
        return bankCard;
    }

    @Override
    public BankCard getBankCardBaseInfoUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            BSUtil.isTrue(Boolean.FALSE, "请输入要查询的用户编号");
        }
        BankCard bankCard = bankCardMapper.getBankCardByUserId(userId);
        if (null == bankCard) {
            bankCard = new BankCard();
            User user = userService.getByUserId(userId);
            bankCard.setBankName(user.getRealName());
        }
        return bankCard;
    }

    @Override
    @Transactional
    public BankCard saveBankCard(BankCard bankCard) {
        verfiy(bankCard);
        String userId = ThreadVariable.getUserID();
        BankCard sysBankCard = this.getBankCardByUserId(userId);

        bankCard.setUserId(userId);
        if (null == sysBankCard || null == sysBankCard.getId()) {
            //用户没有银行卡信息 新增
            bankCard.setCreateTime(new Date());
            bankCardMapper.insertSelective(bankCard);
        } else {
            // 执行到这里说明已有银行卡信息  修改银行卡信息为最新
            bankCard.setModifyTime(new Date());
            this.bankCardMapper.updateByPrimaryKeySelective(bankCard);
        }
        return bankCard;
    }

    private void verfiy(BankCard bankCard) {
        if (bankCard == null) {
            BSUtil.isTrue(Boolean.FALSE, "参数错误");
        }
        if (StringUtils.isBlank(bankCard.getBankNo())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入银行卡号");
        }
        if (StringUtils.isBlank(bankCard.getBankName())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入银行卡用户名");
        }
        if (StringUtils.isBlank(bankCard.getBankProvince())) {
            BSUtil.isTrue(Boolean.FALSE, "请选择省");
        }
        if (StringUtils.isBlank(bankCard.getBankCity())) {
            BSUtil.isTrue(Boolean.FALSE, "请选择市");
        }
        if (StringUtils.isBlank(bankCard.getBankArea())) {
            BSUtil.isTrue(Boolean.FALSE, "请选择区");
        }
        if (StringUtils.isBlank(bankCard.getBankBranch())) {
            BSUtil.isTrue(Boolean.FALSE, "请输入开户支行");
        }
    }
}
