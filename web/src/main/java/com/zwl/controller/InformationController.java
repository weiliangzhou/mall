package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.baseresult.Result;
import com.zwl.model.Information;
import com.zwl.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: 资讯列表
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:04
 */
@RestController
public class InformationController {
    @Autowired
    private InformationService informationService;

    @PostMapping("/wx/getInformationList")
    public String getInformationList(@RequestBody JSONObject jsonObject) {
        String merchant_id = jsonObject.getString("merchant_id");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        Result result = new Result();
        List<Information> informationList = informationService.getInformationList(merchant_id);
        result.setData(informationList);
        return JSONObject.toJSONString(result);
    }
    @PostMapping("/wx/getInformationInfo")
    public String getInformationInfo(@RequestBody JSONObject jsonObject) {
        String merchant_id = jsonObject.getString("informationId");
        Result result = new Result();
        List<Information> informationList = informationService.getInformationList(merchant_id);
        result.setData(informationList);
        return JSONObject.toJSONString(result);
    }
}
