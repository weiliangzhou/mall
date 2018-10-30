package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserReceivingAddress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: ReceivingAddressController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3011:38
 */
@Api2Doc(name = "收货地址管理")
@RestController
@RequestMapping("/wx/userReceivingAddress")
@ApiComment(seeClass = UserReceivingAddress.class)
public class ReceivingAddressController {
    @ApiComment("保存")
    @RequestMapping(name = "保存",
            value = "/auth/saveUserReceivingAddress", method = RequestMethod.POST)
    public Result saveUserInfo(@ApiComment("收货人姓名") String receivingName,
                               @ApiComment("省") String province,
                               @ApiComment("市") String city,
                               @ApiComment("区") String area,
                               @ApiComment("详细地址") String address,
                               @ApiComment("商户号") String merchantId,
                               @ApiComment("是否默认地址 0不是 1是") String isDefault,
                               @ApiComment("手机号") String phone
    ) {
        return new Result();
    }

    @ApiComment("获取列表")
    @RequestMapping(name = "获取列表",
            value = "/auth/getUserReceivingAddressList", method = RequestMethod.POST)
    public UserReceivingAddress getUserReceivingAddressList(
            @ApiComment("商户号") String merchantId
    ) {
        return new UserReceivingAddress();
    }

    @ApiComment("修改")
    @RequestMapping(name = "修改",
            value = "/auth/updateUserReceivingAddress", method = RequestMethod.POST)
    public Result updateUserReceivingAddress(@ApiComment("收货人姓名") String receivingName,
                               @ApiComment("省") String province,
                               @ApiComment("市") String city,
                               @ApiComment("区") String area,
                               @ApiComment("详细地址") String address,
                               @ApiComment("商户号") String merchantId,
                               @ApiComment("是否默认地址 0不是 1是") String isDefault,
                               @ApiComment("手机号") String phone,
                                @ApiComment("id") Integer id
    ) {
        return new Result();
    }

    @ApiComment("获取单个收货地址")
    @RequestMapping(name = "获取单个收货地址",
            value = "/auth/getOneById", method = RequestMethod.POST)
    public UserReceivingAddress getOneById(
            @ApiComment("id") Integer id
    ) {
        return new UserReceivingAddress();
    }

    @ApiComment("删除收货地址")
    @RequestMapping(name = "删除收货地址",
            value = "/auth/deleteById", method = RequestMethod.POST)
    public Result deleteById(
            @ApiComment("id") Integer id
    ) {
        return new Result();
    }
}
