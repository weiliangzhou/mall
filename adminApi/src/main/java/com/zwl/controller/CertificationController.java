package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserCertification;
import com.zwl.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  用户实名认证controller
 */
@RequestMapping("/admin/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;



    /**
     * 审核用户提交的实名信息
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public Result modifyById(@RequestBody UserCertification userCertification){
        Result result = new Result();
        certificationService.modifyById(userCertification);
        return result;
    }
    /**
     * 查找merchantId下的所有用户实名申请信息
     * @return
     */
    @PostMapping("/getListByMerchantId")
    Result getListByMerchantId(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        String merchantId=jsonObject.getString("merchantId");
        List<UserCertification> list=certificationService.getListByMerchantId(merchantId);
        result.setData(list);
        return result;
    }
    /**
     * 根据Id查询用户提交的实名认证信息
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject){
        Result result = new Result();
        Long id=Long.parseLong(jsonObject.getString("id"));
        UserCertification userCertification=certificationService.getById(id);
        result.setData(userCertification);
        return result;
    }
}
