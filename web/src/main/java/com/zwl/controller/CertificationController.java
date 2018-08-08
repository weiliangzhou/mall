package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.CertificationVal;
import com.zwl.model.po.UserCertification;
import com.zwl.model.po.UserInfo;
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
    public synchronized Result addCertification(@Validated(CertificationVal.class) @RequestBody UserCertification userCertification) {
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
        UserCertification queryUserCertification = new UserCertification();
        queryUserCertification.setUserId(userId);
        queryUserCertification.setStatus(2);
        UserCertification userCertification = certificationService.getOneByParams(queryUserCertification);
        UserCertification userCertification_temp = new UserCertification();
        UserCertification qyc = new UserCertification();
        qyc.setUserId(userId);
        qyc.setStatus(1);
        UserCertification ucf_1 = certificationService.getOneByParams(qyc);
        if (ucf_1 != null) {
            userCertification_temp.setStatus(1);
            result.setData(userCertification_temp);
            return result;
        }
        if (userCertification != null) {
            result.setData(userCertification);
            return result;
        }
        userCertification_temp.setStatus(0);
        result.setData(userCertification_temp);
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
