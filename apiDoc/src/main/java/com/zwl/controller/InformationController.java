package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二师兄超级帅
 * @Title: 资讯列表
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1215:04
 */
@Api2Doc(name = "资讯管理")
@RestController
public class InformationController {
    @Autowired
    private InformationService informationService;

    @ApiComment("资讯添加")
    @RequestMapping(name = "资讯添加",
            value = "/teacher/addInformation", method = RequestMethod.POST)
    public String addInformation(@ApiComment("商户号") String merchantId, @ApiComment("标题") String title, @ApiComment("内容") String content) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }
    @ApiComment("资讯编辑")
    @RequestMapping(name = "资讯编辑",
            value = "/teacher/updateInformation", method = RequestMethod.POST)
    public String updateInformation(@ApiComment("id") String id,@ApiComment("商户号") String merchantId, @ApiComment("标题") String title, @ApiComment("内容") String content) {
        Result result = new Result();
        return JSONObject.toJSONString(result);
    }

}
