package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.UserReceivingAddressService;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ReceivingAddressController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3011:38
 */
@RestController
@Slf4j
@RequestMapping("/wx/userReceivingAddress")
public class ReceivingAddressController {
    @Autowired
    private UserReceivingAddressService userReceivingAddressService;

    @PostMapping("/auth/saveUserReceivingAddress")
    public String saveUserReceivingAddress(@RequestBody @Validated(Update.class) UserReceivingAddress userReceivingAddress) {
        String userId = ThreadVariable.getUserID();
        String merchantId = userReceivingAddress.getMerchantId();
        Integer isDefault = userReceivingAddress.getIsDefault();
        if(1==isDefault.intValue()){
            userReceivingAddressService.updateIsDefaultByUserId(userId,merchantId);
        }
        userReceivingAddress.setUserId(userId);
        userReceivingAddress.setCreateTime(new Date());
        userReceivingAddress.setAvailable(1);
        userReceivingAddressService.insert(userReceivingAddress);
        return JSON.toJSONString(new Result());
    }

    @PostMapping("/auth/updateUserReceivingAddress")
    public String updateUserReceivingAddress(@RequestBody UserReceivingAddress userReceivingAddress) {
        String userId = ThreadVariable.getUserID();
        String merchantId = userReceivingAddress.getMerchantId();
        Integer isDefault = userReceivingAddress.getIsDefault();
        if(1==isDefault.intValue()){
            userReceivingAddressService.updateIsDefaultByUserId(userId,merchantId);
        }
        userReceivingAddressService.update(userReceivingAddress);
        return JSON.toJSONString(new Result());
    }

    @PostMapping("/auth/getUserReceivingAddressList")
    public String getUserReceivingAddressList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        String userID = ThreadVariable.getUserID();
        UserReceivingAddress userReceivingAddress = new UserReceivingAddress();
        userReceivingAddress.setMerchantId(merchantId);
        userReceivingAddress.setUserId(userID);
        List<UserReceivingAddress> userReceivingAddressList = userReceivingAddressService.getUserReceivingAddressList(userReceivingAddress);
        Result result = new Result();
        result.setData(userReceivingAddressList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/auth/getOneById")
    public String getOneById(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        UserReceivingAddress userReceivingAddress = userReceivingAddressService.getOneById(id);
        Result result = new Result();
        result.setData(userReceivingAddress);
        return JSON.toJSONString(result);
    }

    @PostMapping("/auth/deleteById")
    public String deleteById(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        userReceivingAddressService.deleteById(id);
        return JSON.toJSONString(new Result());
    }
}
