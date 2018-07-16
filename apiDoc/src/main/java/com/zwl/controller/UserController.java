package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.service.UserService;
import com.zwl.model.vo.UserLoginInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
