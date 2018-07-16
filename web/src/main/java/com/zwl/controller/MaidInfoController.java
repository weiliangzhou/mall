package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.MaidInfo;
import com.zwl.model.vo.MaidInfoVVo;
import com.zwl.model.vo.MaidInfoVo;
import com.zwl.service.MaidInfoService;
import com.zwl.service.UserQuotaCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class MaidInfoController {
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private UserQuotaCountService userQuotaCountService;

    @PostMapping("/auth/wx/getMaidInfoList")
    public String getMaidInfoList(@RequestBody JSONObject jsonObject) {
        String userId=jsonObject.getString("userId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        Result result = new Result();
        PageHelper.startPage(pageNum, pageSize);
        List<MaidInfoVo> maidInfoList = maidInfoService.getMaidInfoList(userId);
        Integer count=userQuotaCountService.getCountByUserId(userId);
        Integer totalAmount=maidInfoService.getTotalMaidMoneyByUserId(userId);
        MaidInfoVVo maidInfoVVo=new MaidInfoVVo();
        maidInfoVVo.setCount(count==null?0:count);
        maidInfoVVo.setTotalAmount(totalAmount==null?0:count);
        maidInfoVVo.setMaidInfoVoList(maidInfoList);
        result.setData(maidInfoVVo);
        return JSON.toJSONString(result);
    }


}
