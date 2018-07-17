package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Product;
import com.zwl.service.ProductService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:25
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/teacher/getProductList")
    public String getProductList(@RequestBody JSONObject jsonObject) {
        String merchantId=jsonObject.getString("merchantId");
        Result result = new Result();
        List<Product> productList = productService.getProductList(merchantId);
        result.setData(productList);
        return JSON.toJSONString(result);
    }

    @PostMapping(value = "/teacher/updateProduct")
    public String updateProduct(@Validated(Update.class) @RequestBody Product product) {
        //前端页面编辑传入的是元单位 ，需要转换成分
        Integer price=product.getPrice()*100;
        product.setPrice(price);
        Result result = new Result();
        productService.updateProduct(product);
        return JSON.toJSONString(result);
    }


}
