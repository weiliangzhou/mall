package com.zwl.controller;

import com.zwl.baseController.BaseController;
import com.zwl.model.po.BankCard;
import com.zwl.service.BankCardService;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/bankCard")
public class BankCardController extends BaseController {

    @Autowired
    private BankCardService bankCardService;

    /**
     * 获取当前用户银行卡绑定信息
     */
    @PostMapping("/getUserBankCard")
    public String getUserBankCard() {
        String userId = ThreadVariable.getUserID();
        BankCard bankCard = bankCardService.getBankCardBaseInfoUserId(userId);
        return setSuccessResult(bankCard);
    }

    /**
     * 获取当前用户银行卡绑定信息
     */
    @PostMapping("/setUserBankCard")
    public String setUserBankCard(@RequestBody BankCard bankCard) {
        BankCard resultBank = bankCardService.saveBankCard(bankCard);
        return setSuccessResult(resultBank);
    }


}
