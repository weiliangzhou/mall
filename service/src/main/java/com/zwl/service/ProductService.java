package com.zwl.service;

import com.zwl.model.po.Product;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductService
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:04
 */
public interface ProductService {

    List<Product> getProductList(String merchantId);
    void updateProduct(Product product);
    String buy(Product product);
}
