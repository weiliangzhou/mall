package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Product;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/getProductList")
    public String getProductList(String merchantId) {
        Result result = new Result();
        List<Product> productList = productService.getProductList(merchantId);
        result.setData(productList);
        return JSON.toJSONString(result);
    }

    @PostMapping("/updateProduct")
    public Result updateProduct(Product product) {
        Result result = new Result();
        productService.updateProduct(product);
        return result;
    }

}
