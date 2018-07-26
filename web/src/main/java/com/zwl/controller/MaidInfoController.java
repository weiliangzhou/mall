package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.UserQuotaCount;
import com.zwl.model.vo.MaidInfoVVo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.service.MaidInfoService;
import com.zwl.service.UserQuotaCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PostMapping("/auth/getMaidInfoList")
    public String getMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Result result = new Result();
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        UserQuotaCount userQuotaCount = userQuotaCountService.getByUserId(userId);
        MaidInfoVVo maidInfoVVo = new MaidInfoVVo();
        if (userQuotaCount != null) {
            Integer totalAmount = maidInfoService.getTotalMaidMoneyByUserId(userId);
            Integer count = userQuotaCount.getCount();
            Integer totalCount = userQuotaCount.getTotalCount();
            maidInfoVVo.setCount(count == null ? 0 : count);
            maidInfoVVo.setTotalAmount(totalAmount == null ? 0 : totalAmount);
            maidInfoVVo.setMaidInfoVoList(maidInfoList);
            maidInfoVVo.setTotalCount(totalCount == null ? 0 : totalCount);
        } else {
            maidInfoVVo.setCount(0);
            maidInfoVVo.setTotalAmount(0);
            maidInfoVVo.setMaidInfoVoList(new ArrayList<>());
            maidInfoVVo.setTotalCount(0);
        }
        result.setData(maidInfoVVo);
        return JSON.toJSONString(result);
    }


}
