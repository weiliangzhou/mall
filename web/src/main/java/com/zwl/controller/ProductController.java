package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.baseresult.Result;
import com.zwl.groups.Buy;
import com.zwl.model.Product;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/wx/product/buy")
    public String buy(@Validated(Buy.class) @RequestBody Product product) {
        Result result = new Result();
        productService.buy(product);
        return JSON.toJSONString(result);
    }


}
