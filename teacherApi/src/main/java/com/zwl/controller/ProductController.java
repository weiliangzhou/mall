package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Product;
import com.zwl.model.vo.ProductVo;
import com.zwl.service.ProductService;
import com.zwl.util.CheckUtil;
import com.zwl.util.MoneyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:25
 */
@RestController
@Slf4j
@RequestMapping("/teacher/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 获取商品列表
     * @param jsonObject
     * @return
     */
    @PostMapping("/getProductList")
    public String getProductList(@RequestBody JSONObject jsonObject) {
        String merchantId=jsonObject.getString("merchantId");
        Result result = new Result();
        List<Product> productList = productService.getProductList(merchantId);
        for (Product p:productList
             ) {
            try {
                p.setPriceDesc(MoneyUtil.changeF2Y(Long.valueOf(p.getPrice())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setData(productList);
        return JSON.toJSONString(result);
    }

    /**
     * 修改商品
     * @return
     */
    @PostMapping(value = "/updateProduct")
    public String updateProduct(/*@Validated(Update.class) */@RequestBody Product product) {
        Result result = new Result();
        //前端页面编辑传入的是元单位 ，需要转换成分
        if(product.getPriceDesc()!=null){
//            Integer price=product.getPrice()*100;
//            product.setPrice(Integer.parseInt(MoneyUtil.changeY2F(product.getPriceDesc())));
            product.setPrice(Integer.parseInt(MoneyUtil.changeY2F(product.getPriceDesc())));
        }
    /*  if(product.getPrice()!=null){
          Integer price=product.getPrice()*100;
          product.setPrice(price);
      }*/
        productService.updateProduct(product);
        return JSON.toJSONString(result);
    }

}
