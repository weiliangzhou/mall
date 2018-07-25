package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.User;
import com.zwl.model.po.UserCertification;
import com.zwl.model.vo.CertificationVo;
import com.zwl.model.vo.PageCertificationVo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserService;
import com.zwl.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户实名认证controller
 */
@RequestMapping("/teacher/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private UserService userService;
    /**
     * 审核用户提交的实名信息
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public Result modifyById(@RequestBody UserCertification userCertification) {
        Result result = new Result();
        certificationService.modifyById(userCertification);
        //如果实名认证通过，则更新用户表 真实姓名
        if(userCertification.getStatus()==1){
            UserCertification temp = certificationService.getOneByUserId(userCertification.getUserId());
            User user = new User();
            user.setUserId(userCertification.getUserId());
            user.setRealName(temp.getRealname());
            userService.updateUserByUserId(user);
        }
        return result;
    }

    /**
     * 查找merchantId下的所有用户实名申请信息
     * @return
     */
    @PostMapping("/getPageListByMerchantId")
    Result getPageListByMerchantId(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page =PageHelper.startPage(pageNum, pageSize);
        List<UserCertification> list = certificationService.getListByMerchantId(merchantId);
        List<CertificationVo> listVo=new ArrayList<>();
        for (UserCertification uc:list
             ) {
            CertificationVo certificationVo = new CertificationVo();
            User user =userService.getByUserId(uc.getUserId());
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            UserCertification userCertification = certificationService.getOneByUserId(uc.getUserId());
            certificationVo.setRealname(userCertification.getRealname());
            String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
            certificationVo.setModifyTime(modifyDateStr);
            certificationVo.setStatus(userCertification.getStatus());
            certificationVo.setId(userCertification.getId());
            listVo.add(certificationVo);
        }
       /* CertificationVo certificationVo = new CertificationVo();
        list.stream().forEach(userCertification -> {
            UserCertification temp = new UserCertification();
            certificationVo.setStatus(temp.getStatus());
            certificationVo.setRealname(temp.getRealname());
            listVo.add(certificationVo);
        });*/
       /* PageResult p = new PageResult(listVo);
        result.setData(p);*/

        PageCertificationVo pageVo=new PageCertificationVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return result;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = Long.parseLong(jsonObject.getString("id"));
        UserCertification userCertification = certificationService.getById(id);

        User user=userService.getByUserId(userCertification.getUserId());
        CertificationVo certificationVo=new CertificationVo();
        certificationVo.setRegisterMobile(user.getRegisterMobile());
        certificationVo.setId(userCertification.getId());
        certificationVo.setStatus(userCertification.getStatus());
        String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
        certificationVo.setModifyTime(modifyDateStr);
        certificationVo.setAuditTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getAuditTime()));
        certificationVo.setRejectReason(userCertification.getRejectReason());
        certificationVo.setRealname(userCertification.getRealname());
        certificationVo.setIdCard(userCertification.getIdCard());
        certificationVo.setImg1Url(userCertification.getImg1Url());
        certificationVo.setImg2Url(userCertification.getImg2Url());
        certificationVo.setImg3Url(userCertification.getImg3Url());


        result.setData(certificationVo);
        return result;
    }
    /**
     * 根据手机号搜索
     * @return
     */
    @PostMapping("/searchByRegisterMobile")
    Result searchByRegisterMobile(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        String registerMobile = jsonObject.getString("registerMobile");
        User query = new User();
        query.setRegisterMobile(registerMobile);
        query.setMerchantId(merchantId);
        User user = userService.getOneByParams(query);
        UserCertification userCertification = certificationService.getOneByUserId(user.getUserId());
        List<CertificationVo> listVo = new ArrayList<>();

        CertificationVo certificationVo = new CertificationVo();
        certificationVo.setRegisterMobile(user.getRegisterMobile());
        certificationVo.setRealname(userCertification.getRealname());
        String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
        certificationVo.setModifyTime(modifyDateStr);
        certificationVo.setStatus(userCertification.getStatus());
        certificationVo.setId(userCertification.getId());
        listVo.add(certificationVo);
        result.setData(listVo);
        return  result;
    }


    /**
     * 根据审核状态搜索
     * @return
     */
    @PostMapping("/searchByStatus")
    Result searchByStatus(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer status = jsonObject.getInteger("status");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");

        Page page = PageHelper.startPage(pageNum, pageSize);
        List<UserCertification> list = certificationService.getListByStatus(merchantId, status);
        List<CertificationVo> listVo = new ArrayList<>();
        for (UserCertification uc : list
                ) {
            CertificationVo certificationVo = new CertificationVo();
            User user = userService.getByUserId(uc.getUserId());
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            UserCertification userCertification = certificationService.getOneByUserId(uc.getUserId());
            certificationVo.setRealname(userCertification.getRealname());
            String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
            certificationVo.setModifyTime(modifyDateStr);
            certificationVo.setStatus(userCertification.getStatus());
            certificationVo.setId(userCertification.getId());
            listVo.add(certificationVo);
        }
        PageCertificationVo pageVo = new PageCertificationVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return result;



    }

}
