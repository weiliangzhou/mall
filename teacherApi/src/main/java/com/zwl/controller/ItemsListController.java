package com.zwl.controller;

import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.vo.ClassSetItemVo;
import com.zwl.model.vo.ProductItemVo;
import com.zwl.service.ClassSetService;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: 下拉框值
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/2219:58
 */
@RestController
@RequestMapping("/teacher/items")
public class ItemsListController {
    @Autowired
    private ClassSetService classSetService;
    @Autowired
    private ProductService productService;

    @PostMapping("/getClassSetItemsList")
    public Result getClassSetItemsList(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        Integer categoryId = jsonObject.getInteger("categoryId");
        List<ClassSetItemVo> list = classSetService.getClassSetItemsList(categoryId, merchantId);
        result.setData(list);
        return result;
    }

    @PostMapping("/getUserLevelItemsList")
    public Result getUserLevelItemsList(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        String merchantId = jsonObject.getString("merchantId");
        List<ProductItemVo> productItemVoList = productService.getUserLevelItemsList(merchantId);
        result.setData(productItemVoList);
        return result;
    }
}
