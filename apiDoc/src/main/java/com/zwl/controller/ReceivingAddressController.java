package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: ReceivingAddressController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3011:38
 */
@Api2Doc(name = "收货地址管理")
@RestController
@RequestMapping("/wx/userReceivingAddress")
public class ReceivingAddressController {
    @PostMapping("/auth/saveUserReceivingAddress")
    public Result saveUserInfo(@ApiComment("收货人姓名") String receivingName,
                               @ApiComment("性别0男 1女") String gender,
                               @ApiComment("省") String province,
                               @ApiComment("市") String city,
                               @ApiComment("区") String area,
                               @ApiComment("详细地址") String address
    ) {
        return new Result();
    }

}
