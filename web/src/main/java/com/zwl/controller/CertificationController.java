package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.UserCertification;
import com.zwl.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户实名认证controller
 */
@RequestMapping("/wx/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    /**
     * 用户提交实名认证
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/add")
    public Result addCertification(@RequestBody UserCertification userCertification) {
        Result result = new Result();
        UserCertification uc = certificationService.getOneByUserId(userCertification.getUserId());
        if (uc != null) {
            certificationService.modifyByUserId(userCertification);
        } else {
            userCertification.setStatus(1);
            certificationService.add(userCertification);
        }

        return result;
    }

    /**
     * 根据userId查询用户提交的实名认证信息
     *
     * @return
     */
    @PostMapping("/getOneByUserId")
    public Result getOneByUserId(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String userId = jsonObject.getString("userId");
        UserCertification userCertification = certificationService.getOneByUserId(userId);
        if(StringUtils.isEmpty(userCertification)){
            result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无用户");
            return result;
        }
        result.setData(userCertification);
        return result;
    }

    /**
     * 修改用户自己提交的实名信息
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public Result modifyById(@RequestBody UserCertification userCertification) {
        Result result = new Result();
        certificationService.modifyById(userCertification);
        return result;
    }


}
