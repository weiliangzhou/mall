package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.baseresult.Result;
import com.zwl.baseresult.ResultCodeEnum;
import com.zwl.model.UserInfo;
import com.zwl.service.UserService;
import com.zwl.vo.UserLoginInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2018/4/2 11:17
 * @Author: 二师兄超级帅
 * @Description:
 */
@RestController
@Api2Doc(name = "用户")
@ApiComment(seeClass = UserLoginInfoVo.class)
public class UserController {
    @Autowired
    private UserService userService;

}
