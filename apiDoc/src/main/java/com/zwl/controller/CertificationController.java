package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserCertification;
import com.zwl.model.vo.CertificationVo;
import com.zwl.model.vo.PageCertificationVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户实名认证controller
 */
@Api2Doc(name = "用户实名认证")
@ApiComment(seeClass = CertificationVo.class)
@RestController
@RequestMapping("/teacher/certification")
public class CertificationController {

    /**
     * 审核用户提交的实名信息
     *
     * @return
     */
    @ApiComment("审核实名信息")
    @RequestMapping(name = "审核实名信息",
            value = "/modifyById", method = RequestMethod.POST)
    public Result modifyById(@ApiComment("实名认证id") Long id,@ApiComment("审核状态") Integer status,@ApiComment("审核人员id") String operator) {
        Result result = new Result();
        return result;
    }

    /**
     * 查找merchantId下的所有用户实名申请信息
     *
     * @return
     */
    @ApiComment("分页获取实名认证信息列表")
    @RequestMapping(name = "分页获取实名认证信息列表",
            value = "/getPageListByMerchantId", method = RequestMethod.POST)
    public PageCertificationVo getListByMerchantId(@ApiComment("商户号") String merchantId, @ApiComment("当前页码") Integer pageNum,
                                                   @ApiComment("每页条数") Integer pageSize) {
        PageCertificationVo pageCertificationVo=new PageCertificationVo();
        return pageCertificationVo;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     * @return
     */
    @ApiComment("根据Id查询实名认证详情")
    @RequestMapping(name = "根据Id查询实名认证详情",
            value = "/getById", method = RequestMethod.POST)
    public CertificationVo getById(@ApiComment("实名认证id") Long id) {
        CertificationVo certificationVo = new CertificationVo();
        return certificationVo;
    }
}
