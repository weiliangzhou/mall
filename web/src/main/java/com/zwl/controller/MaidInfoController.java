package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.baseController.BaseController;
import com.zwl.model.po.User;
import com.zwl.model.po.UserQuotaCount;
import com.zwl.model.vo.*;
import com.zwl.service.MaidInfoService;
import com.zwl.service.UserQuotaCountService;
import com.zwl.service.UserService;
import com.zwl.service.WithdrawService;
import com.zwl.util.PhoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1019:26
 */
@RestController
@RequestMapping("/wx/maidInfo")
public class MaidInfoController extends BaseController {
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private UserQuotaCountService userQuotaCountService;
    @Autowired
    private UserService userService;
    @Autowired
    private WithdrawService withdrawService;

    @PostMapping("/auth/getMaidInfoList")
    public String getMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        UserQuotaCount userQuotaCount = userQuotaCountService.getByUserId(userId);
        Integer totalAmount = maidInfoService.getTotalMaidMoneyByUserId(userId);
        Integer totalAmountByMonth = maidInfoService.getTotalAmountByMonthByUserId(userId);
        Integer totalAmountAll = (totalAmount == null ? 0 : totalAmount) + (totalAmountByMonth == null ? 0 : totalAmountByMonth);
        User referrerUser = userService.getReferrerByUserId(userId);
        User user = userService.getByUserId(userId);
        MaidInfoVVo maidInfoVVo = new MaidInfoVVo();
        Integer count = 0;
        Integer totalCount = 0;
        if (userQuotaCount != null) {
            count = userQuotaCount.getCount();
            totalCount = userQuotaCount.getTotalCount();
        }
        maidInfoVVo.setCount(count);
        maidInfoVVo.setTotalAmount(totalAmountAll == null ? 0 : totalAmountAll / 100);
        maidInfoVVo.setMaidInfoVoList(maidInfoList);
        maidInfoVVo.setTotalCount(totalCount);
        maidInfoVVo.setLogoUrl(user.getLogoUrl() == null ? "" : user.getLogoUrl());
        maidInfoVVo.setReferrerPhone(referrerUser == null ? "" : referrerUser.getRegisterMobile());
        if (null == referrerUser) {
            maidInfoVVo.setReferrerRealName("");
        } else {
            maidInfoVVo.setReferrerRealName(referrerUser.getRealName() == null ? "" : referrerUser.getRealName());
        }
        return setSuccessResult(maidInfoVVo);
    }

    @PostMapping("/auth/getMyMaidInfoList")
    public String getMyMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        List<MyMaidInfoVo> myMaidInfoList = new ArrayList<MyMaidInfoVo>();
        Integer headmaster = 0;
        Integer dean = 0;
        Integer monitor = 0;
        Integer VipStudent = 0;
        Integer student = 0;
        Integer member = 0;
        Integer level;
        for (MaidInfoVo maidInfoVo : maidInfoList) {
            level = maidInfoVo.getLevel();
            if (99 == level) {
                headmaster++;
            } else if (6 == level) {
                dean++;
            } else if (5 == level) {
                monitor++;
            } else if (4 == level) {
                VipStudent++;
            } else if (1 == level) {
                student++;
            } else if (0 == level) {
                member++;
            }

        }

        UserQuotaCount userQuotaCount = userQuotaCountService.getByUserId(userId);

        Integer count = 0;
        Integer totalCount = 0;
        if (userQuotaCount != null) {
            count = userQuotaCount.getCount();
            totalCount = userQuotaCount.getTotalCount();
        }
        MyMaidInfoVo myHeadmaster = new MyMaidInfoVo();
        myHeadmaster.setLeval(99);
        myHeadmaster.setCount(headmaster);
        myHeadmaster.setTitle("校长");
        myMaidInfoList.add(myHeadmaster);

        MyMaidInfoVo myDean = new MyMaidInfoVo();
        myDean.setLeval(6);
        myDean.setCount(dean);
        myDean.setTitle("院长");
        myMaidInfoList.add(myDean);

        MyMaidInfoVo myMonitor = new MyMaidInfoVo();
        myMonitor.setLeval(5);
        myMonitor.setCount(monitor);
        myMonitor.setTitle("班长");
        myMaidInfoList.add(myMonitor);

        MyMaidInfoVo myVipStudent = new MyMaidInfoVo();
        myVipStudent.setLeval(4);
        myVipStudent.setCount(VipStudent);
        myVipStudent.setTitle("创业教练");
        myMaidInfoList.add(myVipStudent);

        MyMaidInfoVo myStudent = new MyMaidInfoVo();
        myStudent.setLeval(1);
        myStudent.setCount(student);
        myStudent.setTitle("学员");
        myStudent.setRestCount(count);
        myStudent.setTotalCount(totalCount);
        myMaidInfoList.add(myStudent);

        return setSuccessResult(myMaidInfoList);
    }

    @PostMapping("/auth/getMaidInfoByMonth")
    public String getMaidInfoByMonth(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoByMonth(userId);
        UserQuotaCount userQuotaCount = userQuotaCountService.getByUserId(userId);
        Integer totalAmount = maidInfoService.getTotalMaidMoneyByUserId(userId);
        Integer totalAmountByMonth = maidInfoService.getTotalAmountByMonthByUserId(userId);
        Integer totalAmountAll = (totalAmount == null ? 0 : totalAmount) + (totalAmountByMonth == null ? 0 : totalAmountByMonth);
        User referrerUser = userService.getReferrerByUserId(userId);
        User user = userService.getByUserId(userId);
        MaidInfoVVo maidInfoVVo = new MaidInfoVVo();
        Integer count = 0;
        Integer totalCount = 0;
        if (userQuotaCount != null) {
            count = userQuotaCount.getCount();
            totalCount = userQuotaCount.getTotalCount();
        }
        maidInfoVVo.setCount(count);
        maidInfoVVo.setTotalAmount(totalAmountAll == null ? 0 : totalAmountAll / 100);
        maidInfoVVo.setMaidInfoVoList(maidInfoList);
        maidInfoVVo.setTotalCount(totalCount);
        maidInfoVVo.setLogoUrl(user.getLogoUrl() == null ? "" : user.getLogoUrl());
        maidInfoVVo.setReferrerPhone(referrerUser == null ? "" : referrerUser.getRegisterMobile());
        if (null == referrerUser) {
            maidInfoVVo.setReferrerRealName("");
        } else {
            maidInfoVVo.setReferrerRealName(referrerUser.getRealName() == null ? "" : referrerUser.getRealName());
        }
        maidInfoVVo.setReferrerPhone(referrerUser == null ? "" : referrerUser.getRegisterMobile());
        return setSuccessResult(maidInfoVVo);
    }

    @PostMapping("/auth/getMyMaidInfoListByLevel")
    public String getMyMaidInfoListByLevel(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userNum");
        Integer level = jsonObject.getInteger("level");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<MyMaidInfoVVo> maidInfoList = maidInfoService.getMaidInfoListByLevel(userId, level);
        return setSuccessResult(maidInfoList);
    }

    @PostMapping("/auth/getEncourageDetail")
    public String getEncourageDetail(@RequestBody JSONObject jsonObject) {
        EncourageDetailVo encourageDetailVo = new EncourageDetailVo();
        String userId = jsonObject.getString("userId");
        encourageDetailVo.setUserId(userId);
        if (StringUtils.isNotBlank(jsonObject.getString("startTime"))) {
            Date startTime = jsonObject.getDate("startTime");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(startTime);
            calendar.add(Calendar.MONTH, 1);
            Date endTime = calendar.getTime();
            encourageDetailVo.setStartTime(startTime);
            encourageDetailVo.setEndTime(endTime);
        }

        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        List<MyMaidInfoVVo> maidInfoList = maidInfoService.getEncourageDetail(encourageDetailVo);
        return setSuccessResult(maidInfoList);
    }

    @PostMapping("/auth/getEncourageInfo")
    public String getEncourageInfo(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        EncourageInfoVo encourageInfoVo = new EncourageInfoVo();
        Integer balance = maidInfoService.getBalance(userId);
        Integer totalMoney = withdrawService.getTotalMoneyByUserId(userId);
        encourageInfoVo.setBalance(balance == null ? 0 : balance);
        encourageInfoVo.setTotalMoney(totalMoney == null ? 0 : totalMoney);
        return setSuccessResult(encourageInfoVo);
    }


}
