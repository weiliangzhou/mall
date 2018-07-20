package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.zwl.model.po.Product;
import com.zwl.model.vo.BuyResult;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/auth/buy", method = {RequestMethod.POST, RequestMethod.GET})
    public String buy(HttpServletRequest request, String callback) {
//        HttpServletRequest request,@Validated(Buy.class) @RequestBody Product product
//        @Validated(Buy.class) @RequestBody Product product
//        Result result = new Result();
        Long id = Long.parseLong(request.getParameter("id"));
        String userId = request.getParameter("userId");
        String merchantId = request.getParameter("merchantId");
        Product product = new Product();
        product.setId(id);
        product.setUserId(userId);
        product.setMerchantId(merchantId);
        BuyResult buyResult = productService.buy(product);
//        result.setData(buyResult);
        String result = "{'ret':'true','data':"+JSON.toJSONString(buyResult)+"}";
        //加上返回参数
        result = callback + "(" + result +")";
        return result;
    }


}
