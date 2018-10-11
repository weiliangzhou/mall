package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.User;
import com.zwl.model.po.UserQuotaCount;
import com.zwl.model.vo.MaidInfoVVo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.service.MaidInfoService;
import com.zwl.service.UserQuotaCountService;
import com.zwl.service.UserService;
import com.zwl.util.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1019:26
 */
@RestController
@RequestMapping("/wx/maidInfo")
public class MaidInfoController {
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private UserQuotaCountService userQuotaCountService;
    @Autowired
    private UserService userService;

    @PostMapping("/auth/getMaidInfoList")
    public String getMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Result result = new Result();
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        UserQuotaCount userQuotaCount = userQuotaCountService.getByUserId(userId);
        Integer totalAmount = maidInfoService.getTotalMaidMoneyByUserId(userId);
        Integer totalAmountByMonth=maidInfoService.getTotalAmountByMonthByUserId(userId);
        Integer totalAmountAll=(totalAmount == null ? 0 : totalAmount)+(totalAmountByMonth == null ? 0 : totalAmountByMonth);
        User referrerUser=userService.getReferrerByUserId(userId);
        User user=userService.getByUserId(userId);
        MaidInfoVVo maidInfoVVo = new MaidInfoVVo();
        Integer count = 0;
        Integer totalCount = 0;
        if (userQuotaCount != null) {
            count = userQuotaCount.getCount();
            totalCount = userQuotaCount.getTotalCount();
        }
        maidInfoVVo.setCount(count);
        maidInfoVVo.setTotalAmount(totalAmountAll == null ? 0 : totalAmountAll/100);
        maidInfoVVo.setMaidInfoVoList(maidInfoList);
        maidInfoVVo.setTotalCount(totalCount);
        maidInfoVVo.setLogoUrl(user.getLogoUrl()==null?"":user.getLogoUrl());
        maidInfoVVo.setReferrerPhone(referrerUser==null?"":referrerUser.getRegisterMobile());
        result.setData(maidInfoVVo);
        return JSON.toJSONString(result);
    }




}
