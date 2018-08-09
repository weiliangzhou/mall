package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.CertificationVal;
import com.zwl.model.po.UserCertification;
import com.zwl.model.po.UserInfo;
import com.zwl.model.vo.CertificationVo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserInfoService;
import com.zwl.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户提交实名认证
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/add")
    public Result addCertification(@Validated(CertificationVal.class) @RequestBody UserCertification userCertification) {
        Result result = new Result();
//        身份证防重,只通过才,
        String cardNum = userCertification.getIdCard();
        UserCertification queryUserCertification = new UserCertification();
        queryUserCertification.setIdCard(cardNum);
        queryUserCertification.setStatus(2);
        UserCertification isExist = certificationService.getOneByParams(queryUserCertification);
        if (isExist != null)
            BSUtil.isTrue(false, "身份证已经存在，请更换其他绑定！");
        queryUserCertification.setStatus(1);
        UserCertification isBinding = certificationService.getOneByParams(queryUserCertification);
        if (isBinding != null)
            BSUtil.isTrue(false, "身份证已经在审核中！！！！");
        userCertification.setStatus(1);
        certificationService.add(userCertification);
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
        //查询是否存在以及提交实名认证，并审核通过
        UserCertification queryUserCertification = new UserCertification();
        queryUserCertification.setUserId(userId);
        queryUserCertification.setStatus(2);
        UserCertification userCertification2 = certificationService.getOneByParams(queryUserCertification);
        if (userCertification2 != null) {
            result.setData(userCertification2);
            return result;
        }
        //查询是否已经提交了实名认证，还在审核中状态
        queryUserCertification.setStatus(1);
        UserCertification userCertification1 = certificationService.getOneByParams(queryUserCertification);
        if (userCertification1 != null) {
            CertificationVo certificationVoResult = new CertificationVo();
            certificationVoResult.setStatus(1);
            result.setData(certificationVoResult);
            return result;
        }
        //查询是否已经提交了实名认证，已经被驳回状态
        queryUserCertification.setStatus(3);
        UserCertification userCertification3 = certificationService.getOneByParams(queryUserCertification);
        if (userCertification3 != null) {
            CertificationVo certificationVoResult = new CertificationVo();
            certificationVoResult.setStatus(3);
            certificationVoResult.setRejectReason(userCertification3.getRejectReason());
            result.setData(certificationVoResult);
            return result;
        }
        //用户还未提交实名认证
        CertificationVo certificationVoResult = new CertificationVo();
        certificationVoResult.setStatus(0);
        result.setData(certificationVoResult);
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
        //如果实名认证通过，则更新用户详情表
        if (userCertification.getStatus() == 2) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userCertification.getUserId());
            userInfo.setIsCertification(1);
            userInfoService.modifyByParams(userInfo);
        }
        return result;
    }


}
