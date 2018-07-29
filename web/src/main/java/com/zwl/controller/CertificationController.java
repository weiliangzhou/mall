package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.UserCertification;
import com.zwl.model.po.UserInfo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result addCertification(@RequestBody UserCertification userCertification) {
        Result result = new Result();
//        身份证防重,只通过才,
        String cardNum = userCertification.getIdCard();
        UserCertification queryUserCertification = new UserCertification();
        queryUserCertification.setIdCard(cardNum);
        queryUserCertification.setStatus(2);
        UserCertification isExist = certificationService.getOneByParams(queryUserCertification);
        if (isExist != null)
            BSUtil.isTrue(false, "身份证已经存在，请更换其他绑定！");
//        UserCertification uc = certificationService.getOneByUserId(userCertification.getUserId());
//        if (uc != null) {
//            uc.setStatus(1);
//            certificationService.modifyByUserId(userCertification);
//        } else {
        userCertification.setStatus(1);
        certificationService.add(userCertification);
//        }

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
//        UserCertification userCertification = certificationService.getOneByUserId(userId);
        if (userCertification == null) {
            /*result.setCode(ResultCodeEnum.EXCEPTION);
            result.setMessage("查无用户");
            return result;*/

            UserCertification queryUC = new UserCertification();
            queryUC.setUserId(userId);
            queryUC.setStatus(3);
            UserCertification userCertification_nopass = certificationService.getOneByParams(queryUC);
            if (userCertification_nopass != null)
                result.setData(userCertification_nopass);
            else {
                UserCertification userCertification_temp = new UserCertification();
                userCertification_temp.setStatus(0);
                result.setData(userCertification_temp);
            }

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
