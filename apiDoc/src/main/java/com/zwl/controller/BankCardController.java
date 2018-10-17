package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api2Doc(name = "用户银行卡绑定信息")
@RestController
public class BankCardController {

    @ApiComment("查询用户绑定的银行卡信息")
    @RequestMapping(name = "查询用户绑定的银行卡信息", value = "/wx/bankCard/getUserBankCard", method = RequestMethod.POST)
    public Result getUserBankCard() {
        Result result = new Result();
        return result;
    }

    @ApiComment("设置用户绑定银行卡信息")
    @RequestMapping(name = "设置用户绑定银行卡信息", value = "/wx/bankCard/setUserBankCard", method = RequestMethod.POST)
    public Result setUserBankCard(@ApiComment("真实姓名") String bankName,
                                  @ApiComment("银行卡号") String bankNo,
                                  @ApiComment("银行卡 省") String bankProvince,
                                  @ApiComment("银行卡 市") String bankCity,
                                  @ApiComment("银行卡 区") String bankArea,
                                  @ApiComment("银行卡 支行") String bankBranch) {
        Result result = new Result();
        return result;
    }
}
