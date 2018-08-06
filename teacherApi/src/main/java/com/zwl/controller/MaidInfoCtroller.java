package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.XiaXianVVo;
import com.zwl.model.vo.XiaXianVo;
import com.zwl.service.MaidInfoService;
import com.zwl.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: MaidInfoCtroller
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1311:27
 */
@Api2Doc(name = "下线列表")
@RestController
public class MaidInfoCtroller {
    @Autowired
    private MaidInfoService maidInfoService;
    @Autowired
    private UserAccountService userAccountService;
    @PostMapping("/teacher/getXiaXianList")
    public XiaXianVVo getXiaXianList(@RequestBody JSONObject jsonObject) {
        String userId=jsonObject.getString("userId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        Page page=PageHelper.startPage(pageNum, pageSize);
        XiaXianVVo xiaXianVVo = new XiaXianVVo();
        Result result = new Result();
        List<XiaXianVo>  xiaXianVoList=maidInfoService.getXiaXianList(userId);
        Integer totalMoney=maidInfoService.getTotalMaidMoneyByUserId(userId);
        Integer balance=userAccountService.getBalanceByUserId(userId);
        xiaXianVVo.setTotalMoney(null==totalMoney?0:totalMoney);
        xiaXianVVo.setBalance(null==balance?0:balance);
        xiaXianVVo.setXiaXianVoList(xiaXianVoList);
        xiaXianVVo.setTotalPage(page.getTotal());
        xiaXianVVo.setUserId(userId);
        result.setData(xiaXianVVo);
        return xiaXianVVo;
    }

}
