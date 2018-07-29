package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import com.zwl.model.po.User;
import com.zwl.model.po.UserCertification;
import com.zwl.model.vo.CertificationVo;
import com.zwl.model.vo.PageCertificationVo;
import com.zwl.service.CertificationService;
import com.zwl.service.UserService;
import com.zwl.util.CheckUtil;
import com.zwl.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户实名认证controller
 */
@Slf4j
@RequestMapping("/teacher/certification")
@RestController
public class CertificationController {
    @Autowired
    private CertificationService certificationService;
    @Autowired
    private UserService userService;

    /**
     * 审核用户提交的实名信息
     *
     * @param userCertification
     * @return
     */
    @PostMapping("/modifyById")
    public Result modifyById(@RequestBody UserCertification userCertification) {
        Result result = new Result();
        Integer status = userCertification.getStatus();
        String realName = userCertification.getRealname();
        String userId = userCertification.getUserId();
        switch (status) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                //如果实名认证通过，则更新用户表 真实姓名
                User user = new User();
                user.setUserId(userId);
                user.setRealName(realName);
                userService.updateUserByUserId(user);
                break;
            case 3:
                //驳回 则把available = 0
                userCertification.setAvailable(0);
                break;
        }

        certificationService.modifyById(userCertification);
        return result;
    }

    /**
     * 查找merchantId下的所有用户实名申请信息
     *
     * @return
     */
    @PostMapping("/getPageListByMerchantId")
    Result getPageListByMerchantId(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<UserCertification> list = certificationService.getListByMerchantId(merchantId);
        List<CertificationVo> listVo = new ArrayList<>();
        for (UserCertification uc : list) {
            if (uc == null)
                continue;
            CertificationVo certificationVo = new CertificationVo();
            User user = userService.getByUserId(uc.getUserId());
            if(user==null){
                result.setCode(ResultCodeEnum.EXCEPTION);
                result.setMessage("实名认证表的userid:"+uc.getUserId()+"在user表里不存在");
                log.error("实名认证表的userid:"+uc.getUserId()+"在user表里不存在");
                return result;
            }
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            UserCertification ucQuery = new UserCertification();
            ucQuery.setId(uc.getId());
//            UserCertification userCertification = certificationService.getOneByUserId(uc.getUserId());
            UserCertification userCertification = certificationService.getOneByParams(ucQuery);
            certificationVo.setRealname(userCertification.getRealname());
            String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
            certificationVo.setModifyTime(modifyDateStr);
            String createDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getCreateTime());
            certificationVo.setCreateTime(createDateStr);
            certificationVo.setStatus(userCertification.getStatus());
            certificationVo.setId(userCertification.getId());
            certificationVo.setUserId(userCertification.getUserId());

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

        PageCertificationVo pageVo = new PageCertificationVo();
        pageVo.setPageNum(pageNum);
        pageVo.setTotalPage(page.getTotal());
        pageVo.setList(listVo);
        result.setData(pageVo);
        return result;
    }

    /**
     * 根据Id查询用户提交的实名认证信息
     *
     * @return
     */
    @PostMapping("/getById")
    public Result getById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = Long.parseLong(jsonObject.getString("id"));
        UserCertification userCertification = certificationService.getById(id);

        User user = userService.getByUserId(userCertification.getUserId());
        CertificationVo certificationVo = new CertificationVo();
        certificationVo.setRegisterMobile(user.getRegisterMobile());
        certificationVo.setId(userCertification.getId());
        certificationVo.setStatus(userCertification.getStatus());
        String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getModifyTime());
        certificationVo.setModifyTime(modifyDateStr);
        certificationVo.setCreateTime(DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", userCertification.getCreateTime()));
        certificationVo.setRejectReason(userCertification.getRejectReason());
        certificationVo.setRealname(userCertification.getRealname());
        certificationVo.setIdCard(userCertification.getIdCard());
        certificationVo.setImg1Url(userCertification.getImg1Url());
        certificationVo.setImg2Url(userCertification.getImg2Url());
        certificationVo.setImg3Url(userCertification.getImg3Url());
        certificationVo.setUserId(userCertification.getUserId());


        result.setData(certificationVo);
        return result;
    }

    /**
     * 根据手机号搜索
     *
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
        // 同一个用户 实名认证表可能存在多条数据
//        UserCertification userCertification = certificationService.getOneByUserId(user.getUserId());
        List<UserCertification> list = certificationService.getListByUserId(user.getUserId());
        List<CertificationVo> listVo = new ArrayList<>();
        if (CheckUtil.isEmpty(list)) {
            CertificationVo certificationVo = new CertificationVo();
            certificationVo.setStatus(0);
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            certificationVo.setRealname(user.getRealName());
            listVo.add(certificationVo);
            result.setData(listVo);
            return result;
        }
        for (UserCertification uc:list
             ) {
            CertificationVo certificationVo = new CertificationVo();
            certificationVo.setRegisterMobile(user.getRegisterMobile());
            certificationVo.setRealname(uc.getRealname());
            String modifyDateStr = DateUtil.getFormatString("yyyy-MM-dd HH:mm:ss", uc.getModifyTime());
            certificationVo.setModifyTime(modifyDateStr);
            certificationVo.setStatus(uc.getStatus());
            certificationVo.setId(uc.getId());
            listVo.add(certificationVo);
        }
        result.setData(listVo);
        return result;
    }


    /**
     * 根据审核状态搜索
     *
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
//            UserCertification userCertification = certificationService.getOneByUserId(uc.getUserId());
            //同一个用户 实名认证表可能存在多条数据
            UserCertification userCertification = certificationService.getById(uc.getId());
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
