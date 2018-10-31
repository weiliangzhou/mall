package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserGift;
import com.zwl.service.UserGiftService;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户礼品
 *
 * @author houyuhui
 */
@RestController
public class UserGiftController {

    @Autowired
    private UserGiftService userGiftService;

    /**
     * 兑换商品
     */
    @PostMapping("/exhangeGift")
    public Result exhangeGift(@RequestBody JSONObject jsonObject) {
        String userId = ThreadVariable.getUserID();
        Integer giftId = jsonObject.getInteger("giftId");
        Integer addressId = jsonObject.getInteger("addressId");
        UserGift userGift = userGiftService.addUserExhangeGift(userId, giftId, addressId);
        Result result = new Result();
        result.setData(userGift);
        return result;
    }
}
