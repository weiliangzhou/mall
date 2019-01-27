package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.baseController.BaseController;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.UserReceivingAddressService;
import com.zwl.util.ThreadVariable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
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
public class ReceivingAddressController extends BaseController {
    @Autowired
    private UserReceivingAddressService userReceivingAddressService;

    @PostMapping("/auth/saveUserReceivingAddress")
    public String saveUserReceivingAddress(@RequestBody @Validated(Update.class) UserReceivingAddress userReceivingAddress) {
        String userId = ThreadVariable.getUserID();
        String merchantId = userReceivingAddress.getMerchantId();
        Integer isDefault = userReceivingAddress.getIsDefault();
        if (1 == isDefault.intValue()) {
            userReceivingAddressService.updateIsDefaultByUserId(userId, merchantId);
        }
        userReceivingAddress.setUserId(userId);
        userReceivingAddress.setCreateTime(new Date());
        userReceivingAddress.setAvailable(1);
        userReceivingAddressService.insert(userReceivingAddress);
        return setSuccessResult();
    }

    @PostMapping("/auth/updateUserReceivingAddress")
    public String updateUserReceivingAddress(@RequestBody UserReceivingAddress userReceivingAddress) {
        String userId = ThreadVariable.getUserID();
        String merchantId = userReceivingAddress.getMerchantId();
        Integer isDefault = userReceivingAddress.getIsDefault();
        if (1 == isDefault.intValue()) {
            userReceivingAddressService.updateIsDefaultByUserId(userId, merchantId);
        }
        userReceivingAddressService.update(userReceivingAddress);
        return setSuccessResult();
    }

    @PostMapping("/auth/getUserReceivingAddressList")
    public String getUserReceivingAddressList(@RequestBody JSONObject jsonObject) {
        Integer pageSize = jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNum");
        String merchantId = jsonObject.getString("merchantId");
        String userID = ThreadVariable.getUserID();
        UserReceivingAddress userReceivingAddress = new UserReceivingAddress();
        userReceivingAddress.setMerchantId(merchantId);
        userReceivingAddress.setUserId(userID);
        PageHelper.startPage(pageNum, pageSize);
        List<UserReceivingAddress> userReceivingAddressList = userReceivingAddressService.getUserReceivingAddressList(userReceivingAddress);
        return setSuccessResult(userReceivingAddressList);
    }

    @PostMapping("/auth/getOneById")
    public String getOneById(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        UserReceivingAddress userReceivingAddress = userReceivingAddressService.getOneById(id);
        return setSuccessResult(userReceivingAddress);
    }

    @PostMapping("/auth/deleteById")
    public String deleteById(@RequestBody JSONObject jsonObject) {
        Long id = jsonObject.getLong("id");
        userReceivingAddressService.deleteById(id);
        return setSuccessResult();
    }
}
