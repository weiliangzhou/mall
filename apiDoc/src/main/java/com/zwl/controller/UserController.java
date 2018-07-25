package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.PageUserVo;
import com.zwl.model.vo.UserVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员管理controller
 */
@Api2Doc(name = "会员管理")
@ApiComment(seeClass = UserVo.class)
@RestController
@RequestMapping("/teacher/user")
public class UserController {
    /**
     * 商户后台 添加用户
     */
    @ApiComment("添加用户")
    @RequestMapping(name = "添加用户",
            value = "/add", method = RequestMethod.POST)
    public Result add(@ApiComment("registerMobile") String registerMobile, @ApiComment("商户号") String merchantId,
                      @ApiComment("当前操作人员id") String operator, @ApiComment("真实姓名") String realName,
                      @ApiComment("会员等级") String memberLevel, @ApiComment("会员等级名称") String levelName) {
        Result result = new Result();
        return result;
    }

    /**
     * 根据会员等级获取用户列表
     *
     * @return
     */
    @ApiComment("获取会员列表")
    @RequestMapping(name = "获取会员列表",
            value = "/getUserList", method = RequestMethod.POST)
    public PageUserVo getPageList(@ApiComment("商户号") String merchantId, @ApiComment("当前页码") Integer pageNum,
                                  @ApiComment("每页条数") Integer pageSize, @ApiComment("0普通会员 1付费会员") String queryType,
                                  @ApiComment("6院长 5班长 4学员 1小班 0普通会员") String memberLevel, @ApiComment("手机号") String phone,
                                  @ApiComment("渠道注册 1 微信注册 2线下导入") Integer registerFrom


    ) {
        PageUserVo pageVo = new PageUserVo();
        return pageVo;
    }

    /**
     * 修改用户等级
     */
    @ApiComment("修改用户等级")
    @RequestMapping(name = "修改用户等级",
            value = "/modify", method = RequestMethod.POST)
    public Result modify(@ApiComment("用户id") String userId, @ApiComment("商户号") String merchantId,
                         @ApiComment("当前操作人员id") String operator, @ApiComment("会员等级") String memberLevel,
                         @ApiComment("会员等级名称") String levelName) {
        Result result = new Result();
        return result;
    }


}
