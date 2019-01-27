package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.baseController.BaseController;
import com.zwl.model.groups.CertificationVal;
import com.zwl.model.po.UserCertification;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.CertificationVo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserInfoService;
import com.zwl.util.CheckUtil;
import com.zwl.util.IdCardVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * 用户实名认证controller
 */
@RequestMapping("/wx/certification")
@RestController
public class CertificationController extends BaseController {
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户提交实名认证
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/add")
    public synchronized String addCertification(@Validated(CertificationVal.class) @RequestBody UserCertification userCertification) {
        Integer cardType = userCertification.getCardType();
        String cardNum = userCertification.getIdCard();
        if (0 == cardType) {
            try {
                String msg = IdCardVerification.IDCardValidate(cardNum);
                if (!msg.contains("有效")) {
                    return setFailResult("401", msg);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return setFailResult("205", "身份证号码不正确");
            }
        }

        UserCertification temp = certificationService.getOneByUserId(userCertification.getUserId());
        switch (temp.getStatus()) {
            case 1:
                return setFailResult("401", "该用户实名认证正在审核中");
            case 2:
                return setFailResult("401", "该用户实名认证已经提交并审批通过");
        }

        userCertification.setStatus(1);
        certificationService.add(userCertification);
        return setSuccessResult();
    }

    /**
     * 根据userId查询用户提交的实名认证信息
     *
     * @return
     */
    @PostMapping("/getOneByUserId")
    public String getOneByUserId(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        if (CheckUtil.isEmpty(userId)) {
            return setFailResult("900", "");
        }
        UserCertification userCertification = certificationService.getOneByUserId(userId);
        Integer status = userCertification.getStatus();
        CertificationVo certificationVoResult = new CertificationVo();
        switch (status) {
            case 0:
                certificationVoResult.setStatus(0);
                break;
            case 1:
                certificationVoResult.setStatus(1);
                break;
            case 2:
                certificationVoResult.setStatus(2);
                break;
            case 3:
                certificationVoResult.setStatus(3);
                certificationVoResult.setRejectReason(userCertification.getRejectReason());
                break;
        }
        return setSuccessResult(certificationVoResult);
    }

    /**
     * 修改用户自己提交的实名信息
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public String modifyById(@RequestBody UserCertification userCertification) {
        certificationService.modifyById(userCertification);
        //如果实名认证通过，则更新用户详情表
        if (userCertification.getStatus() == 2) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userCertification.getUserId());
            userInfo.setIsCertification(1);
            userInfoService.modifyByParams(userInfo);
        }
        return setSuccessResult();
    }


}
