package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.UserVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员管理controller
 */
@Api2Doc(name = "会员管理")
@RestController
public class UserController {
    /**
     * 保存城市、性别
     *
     * @param city
     * @param gender
     * @return
     */
    @RequestMapping(name = "保存城市、性别",
            value = "/wx/user/auth/saveUserInfo", method = RequestMethod.POST)
    public Result saveUserInfo(@ApiComment("省") String province, @ApiComment("市") String city, @ApiComment("性别0男 1女") String gender) {
        return new Result();
    }

    /**
     * @param merchantId
     * @param slReferrer
     * @return
     */
    @RequestMapping(name = "线下沙龙报名信息",
            value = "/wx/offlineActivity/getSLUserInfo", method = RequestMethod.POST)
    public UserVo getSLUserInfo(@ApiComment("merchantId") String merchantId, @ApiComment("slReferrer") String slReferrer
    ) {
        UserVo userVo = new UserVo();
        return userVo;
    }


}
