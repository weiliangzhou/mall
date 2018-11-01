package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.CertificationVo;
import com.zwl.model.vo.PageCertificationVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api2Doc(name = "用户设置")
@RestController
public class UserController {

    @ApiComment("查询用户绑定的微信帐号")
    @RequestMapping(name = "查询用户绑定的微信帐号", value = "/wx/userWechat/getUserBindWechat", method = RequestMethod.POST)
    public Result getUserBindWechat() {
        Result result = new Result();
        return result;
    }

    @ApiComment("绑定微信账号")
    @RequestMapping(name = "绑定微信账号", value = "/wx/userWechat/bindUserWechat", method = RequestMethod.POST)
    public Result bindUserWechat(@ApiComment("微信账号") String wechatAccount) {
        Result result = new Result();
        return result;
    }

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
     * 审核用户提交的实名信息
     *
     * @return
     */
    @ApiComment("审核实名信息")
    @RequestMapping(name = "审核实名信息",
            value = "/modifyById", method = RequestMethod.POST)
    public Result modifyById(@ApiComment("实名认证id") String userId, @ApiComment("审核状态") Integer status, @ApiComment("审核人员id") String operator) {
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
        PageCertificationVo pageCertificationVo = new PageCertificationVo();
        return pageCertificationVo;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     *
     * @return
     */
    @ApiComment("根据Id查询实名认证详情")
    @RequestMapping(name = "根据Id查询实名认证详情",
            value = "/getById", method = RequestMethod.POST)
    public CertificationVo getById(@ApiComment("实名认证id") Long id) {
        CertificationVo certificationVo = new CertificationVo();
        return certificationVo;
    }

    /**
     * 根据手机号搜索
     */
    @ApiComment("根据手机号搜索")
    @RequestMapping(name = "根据手机号搜索",
            value = "/searchByRegisterMobile", method = RequestMethod.POST)
    public CertificationVo searchByRegisterMobile(@ApiComment("商户号") String merchantId, @ApiComment("注册手机号") String registerMobile) {
        CertificationVo certificationVo = new CertificationVo();

        return certificationVo;
    }

    /**
     * 根据审核状态搜索
     */
    @ApiComment("根据审核状态搜索")
    @RequestMapping(name = "根据审核状态搜索",
            value = "/searchByStatus", method = RequestMethod.POST)
    public PageCertificationVo searchByStatus(@ApiComment("商户号") String merchantId, @ApiComment("当前页码") Integer pageNum,
                                              @ApiComment("每页条数") Integer pageSize, @ApiComment("审核状态") Integer status) {
        PageCertificationVo pageCertificationVo = new PageCertificationVo();
        return pageCertificationVo;
    }
}
