package com.zwl.controller;

import com.zwl.baseresult.Result;
import com.zwl.model.UserCertification;
import com.zwl.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  用户实名认证controller
 */
@RequestMapping("/wx/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    @PostMapping("/add")
    public Result addCertification(@RequestBody UserCertification userCertification){
        Result result = new Result();


        return result;
    }

}
