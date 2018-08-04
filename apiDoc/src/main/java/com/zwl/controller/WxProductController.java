package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;

import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.vo.ProductVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api2Doc(name = "微信小程序商品")
@RestController
public class WxProductController {
    @ApiComment("获取商品列表")
    @RequestMapping(name = "获取商品列表",
            value = "/wx/product/getProductList", method = RequestMethod.POST)
    public ProductVo sendCode(@ApiComment("商户号") String merchantId) {
        ProductVo productVo= new ProductVo();
        return productVo;
    }
    @ApiComment("根据id获取商品")
    @RequestMapping(name = "根据id获取商品",
            value = "/wx/product/getProductById", method = RequestMethod.POST)
    public ProductVo getProductById(@ApiComment("商品id") Long id) {
        ProductVo productVo= new ProductVo();
        return productVo;
    }



}
