package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Product;
import com.zwl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:25
 */
@Api2Doc(name = "代理级别管理")
@ApiComment(seeClass = Product.class)
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @ApiComment("代理级别列表")
    @RequestMapping(name = "代理级别列表",
            value = "/teacher/product/getProductList", method = RequestMethod.POST)
    public List<Product> getAdminProductList(@ApiComment("商户号") String merchantId) {
        List<Product> productList = new ArrayList<>();
        return productList;
    }

    @ApiComment("编辑代理")
    @RequestMapping(name = "编辑代理",
            value = "/teacher/product/updateProduct", method = RequestMethod.POST)
    public Result updateProduct(@ApiComment("产品id") Integer id,
                                @ApiComment("等级名称") String levelName,
                                @ApiComment("产品名称") String productName,
                                @ApiComment("分佣百分比") Integer maidPercent,
                                @ApiComment("时效（天为单位）") Integer validityTime,
                                @ApiComment("产品价格") Integer priceDesc,
                                @ApiComment("商户号") String merchantId
    ) {
        Result result = new Result();
        return result;
    }


}
