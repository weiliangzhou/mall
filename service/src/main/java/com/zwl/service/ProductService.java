package com.zwl.service;

import com.zwl.model.po.Product;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.ProductItemVo;

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

    BuyResult buy(Product product);

    BuyResult newH5Buy(Product product);

    List<Product> getAdminProductList();

    List<ProductItemVo> getUserLevelItemsList(String merchantId);

    Integer getMaidPercentByLevel(Integer referrerLevel, String merchantId);

    Product getProductByMemberLevel(Integer memberLevel, String merchantId);

    Product getProductById(Long id);

    int updateBuyCountById(Long productId, String merchantId);

    /**
     * 根据商品对应等级查询商品信息
     *
     * @param level      等级
     * @param merchantId 商户编号
     * @return 商品信息
     */
    Product getProductByLevelAndMerchantId(Integer level, String merchantId);
}
