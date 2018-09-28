package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.dao.mapper.TeacherUserMapper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: LoginController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1617:02
 */
@RestController
@Slf4j
public class LoginController {
    @Autowired
    private TeacherUserMapper teacherUserMapper;

    @PostMapping("/teacher/login")
    public String login(@RequestBody JSONObject jsonObject) {
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        String pwd = EncryptUtil.md5(password);
        Integer count = teacherUserMapper.checkLogin(userName, pwd);
        if (null == count || count != 1)
            BSUtil.isTrue(false, "用户名密码错误");
        Map map=new HashMap();
        map.put("userName",userName);
        map.put("merchantId",userName.equals("dy")?"1509688041":"1511500801");
        map.put("access","admin");
        map.put("avator","");
        Result result = new Result();
        result.setData(map);
        log.info(map.toString());
        return JSON.toJSONString(result);
    }

}
