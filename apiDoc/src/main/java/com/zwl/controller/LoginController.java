package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: LoginController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1617:02
 */
@Api2Doc(name = "老师后台登录接口")
@RestController
public class LoginController {
    @ApiComment("老师后台登录接口")
    @RequestMapping(name = "老师后台登录接口",
            value = "/teacher/login", method = RequestMethod.POST)
    public String login(@ApiComment("userName") String userName, @ApiComment("password") String password) {
        Result result = new Result();
        return JSON.toJSONString(result);
    }

}
