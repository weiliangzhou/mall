package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.PageUserVo;
import com.zwl.model.vo.UserVo;
import org.springframework.web.bind.annotation.*;

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
    public Result add(@ApiComment("registerMobile") String registerMobile,@ApiComment("商户号") String merchantId,
                      @ApiComment("当前操作人员id") String operator,@ApiComment("真实姓名") String realName,
                      @ApiComment("会员等级") String memberLevel) {
        Result result = new Result();
        return result;
    }

    /**
     * 根据会员等级获取用户列表
     * @return
     */
    @ApiComment("根据会员等级获取用户列表")
    @RequestMapping(name = "根据会员等级获取用户列表",
            value = "/getPageListByLevel", method = RequestMethod.POST)
    public PageUserVo getPageList(@ApiComment("商户号") String merchantId, @ApiComment("当前页码") Integer pageNum,
                              @ApiComment("每页条数") Integer pageSize,@ApiComment("最低会员等级") Integer loweLevel,@ApiComment("最高会员等级") Integer topLevel
    ) {
        PageUserVo pageVo=new PageUserVo();
        return pageVo;
    }

    /**
     * 修改用户等级
     */
    @ApiComment("修改用户等级")
    @RequestMapping(name = "修改用户等级",
            value = "/modify", method = RequestMethod.POST)
    public Result modify(@ApiComment("用户id") String userId,@ApiComment("商户号") String merchantId,
                         @ApiComment("当前操作人员id") String operator, @ApiComment("会员等级") String memberLevel) {
        Result result = new Result();
        return result;
    }
}
