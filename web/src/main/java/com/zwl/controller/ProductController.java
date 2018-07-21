package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.model.groups.Buy;
import com.zwl.model.po.Product;
import com.zwl.model.vo.BuyResult;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/wx/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/auth/buy")
    public String buy(@Validated(Buy.class) @RequestBody Product product) {
        Result result = new Result();
//        Long id = Long.parseLong(request.getParameter("id"));
//        String userId = request.getParameter("userId");
//        String merchantId = request.getParameter("merchantId");
//        Product product = new Product();
//        product.setId(id);
//        product.setUserId(userId);
//        product.setMerchantId(merchantId);
        BuyResult buyResult = productService.buy(product);
        result.setData(buyResult);
//        String result = "{'ret':'true','data':"+JSON.toJSONString(buyResult)+"}";
        //加上返回参数
//        result = callback + "(" + result +")";
        return JSON.toJSONString(result);
    }


}
