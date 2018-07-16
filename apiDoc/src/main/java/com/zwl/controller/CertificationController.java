package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserCertification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实名认证controller
 */
@Api2Doc(name = "用户实名认证")
@ApiComment(seeClass = UserCertification.class)
@RestController
@RequestMapping("/admin/certification")
public class CertificationController {

    /**
     * 审核用户提交的实名信息
     *
     * @return
     */
    @ApiComment("审核用户提交的实名信息")
    @RequestMapping(name = "审核用户提交的实名信息",
            value = "/modifyById", method = RequestMethod.POST)
    public Result modifyById(@ApiComment("实名认证id") Long id) {
        Result result = new Result();
        return result;
    }

    /**
     * 查找merchantId下的所有用户实名申请信息
     *
     * @return
     */
    @ApiComment("查找所有用户实名申请信息")
    @RequestMapping(name = "查找所有用户实名申请信息",
            value = "/getListByMerchantId", method = RequestMethod.POST)
    public List<UserCertification> getListByMerchantId(@ApiComment("商户号") String merchantId) {
        List<UserCertification> list = new ArrayList<>();
        return list;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     *
     * @return
     */
    @ApiComment("根据Id查询用户提交的实名认证信息")
    @RequestMapping(name = "根据Id查询用户提交的实名认证信息",
            value = "/getById", method = RequestMethod.POST)
    public UserCertification getById(@ApiComment("实名认证id") Long id) {
        UserCertification userCertification = new UserCertification();
        return userCertification;
    }
}
