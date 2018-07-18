package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.zwl.model.baseresult.PageResult;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.User;
import com.zwl.model.po.UserCertification;
import com.zwl.model.vo.CertificationVo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实名认证controller
 */
@RequestMapping("/teacher/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private UserService userService;
    /**
     * 审核用户提交的实名信息
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public Result modifyById(@RequestBody UserCertification userCertification) {
        Result result = new Result();
        certificationService.modifyById(userCertification);
        return result;
    }

    /**
     * 查找merchantId下的所有用户实名申请信息
     * @return
     */
    @PostMapping("/getPageListByMerchantId")
    Result getPageListByMerchantId(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<UserCertification> list = certificationService.getListByMerchantId(merchantId);
        List<CertificationVo> listVo=new ArrayList<>();
        for (UserCertification uc:list
             ) {
            CertificationVo certificationVo = new CertificationVo();
            User user =userService.getByUserId(uc.getUserId());
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            UserCertification userCertification = certificationService.getOneByUserId(uc.getUserId());
            certificationVo.setRealname(userCertification.getRealname());
            certificationVo.setModifyTime(userCertification.getModifyTime());
            certificationVo.setStatus(userCertification.getStatus());
            certificationVo.setId(userCertification.getId());
            listVo.add(certificationVo);
        }
       /* CertificationVo certificationVo = new CertificationVo();
        list.stream().forEach(userCertification -> {
            UserCertification temp = new UserCertification();
            certificationVo.setStatus(temp.getStatus());
            certificationVo.setRealname(temp.getRealname());
            listVo.add(certificationVo);
        });*/
        PageResult p = new PageResult(listVo);
        result.setData(p);
        return result;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = Long.parseLong(jsonObject.getString("id"));
        UserCertification userCertification = certificationService.getById(id);
        result.setData(userCertification);
        return result;
    }
}
