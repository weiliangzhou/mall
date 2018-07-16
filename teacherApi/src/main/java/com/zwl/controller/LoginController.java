package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.dao.mapper.TeacherAdminMapper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.util.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: LoginController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1617:02
 */
@RestController
public class LoginController {
    @Autowired
    private TeacherAdminMapper teacherAdminMapper;

    @PostMapping("/teacher/login")
    public String login(@RequestBody JSONObject jsonObject) {
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String pwd = EncryptUtil.md5(password);
        Integer count = teacherAdminMapper.checkLogin(userName, pwd);
        if (null == count || count != 1)
            BSUtil.isTrue(false, "用户名密码错误");
        Result result = new Result();
        return JSON.toJSONString(result);
    }

}
