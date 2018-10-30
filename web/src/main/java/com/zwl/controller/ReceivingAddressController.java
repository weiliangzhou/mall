package com.zwl.controller;

import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserReceivingAddress;
import com.zwl.service.UserReceivingAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result saveUserReceivingAddress(@RequestBody @Validated(Update.class) UserReceivingAddress userReceivingAddress) {
        userReceivingAddressService.insert(userReceivingAddress);
        return new Result();
    }

}
