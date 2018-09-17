package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Information;
import com.zwl.service.InformationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/wx/information")
public class InformationController {
    @Autowired
    private InformationService informationService;

    @PostMapping("/getInformationList")
    public String getInformationList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        PageHelper.startPage(pageNum, pageSize);
        Result result = new Result();
        Information information=new Information();
        information.setMerchantId(merchantId);
        List<Information> informationList = informationService.getWxInformationList(information);
        for(Information information1:informationList){
            if(StringUtils.isBlank(information1.getAudioUrl())){
                information1.setAudioUrl("");
            }
        }
        result.setData(informationList);
        return JSON.toJSONString(result);
    }
    @PostMapping("/getInformationInfo")
    public String getInformationInfo(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInteger("id");
        Result result = new Result();
        Information information = new Information();
        information.setId(id);
        List<Information> informationList = informationService.getInformationInfo(information);
        result.setData(informationList);
        return JSONObject.toJSONString(result);
    }

}
