package com.zwl.controller;

import com.zwl.model.baseresult.Result;
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
public class BankCardController {

    @Autowired
    private BankCardService bankCardService;

    /**
     * 获取当前用户银行卡绑定信息
     */
    @PostMapping("/getUserBankCard")
    public Result getUserBankCard() {
        String userId = ThreadVariable.getUserID();
        BankCard bankCard = bankCardService.getBankCardBaseInfoUserId(userId);
        Result result = new Result();
        result.setData(bankCard);
        return result;
    }

    /**
     * 获取当前用户银行卡绑定信息
     */
    @PostMapping("/setUserBankCard")
    public Result setUserBankCard(@RequestBody BankCard bankCard) {
        BankCard resultBank = bankCardService.saveBankCard(bankCard);
        Result result = new Result();
        result.setData(resultBank);
        return result;
    }


}
