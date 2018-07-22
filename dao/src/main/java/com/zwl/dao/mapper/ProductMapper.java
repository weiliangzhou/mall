package com.zwl.dao.mapper;

import com.zwl.model.po.Product;
import com.zwl.model.vo.ProductItemVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {


    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getProductList(String merchantId);

    List<Product> getAdminProductList();

    @Select("select level ,level_name as levelName from ss_product  where merchant_id=#{merchantId} and available =1")
    List<ProductItemVo> getUserLevelItemsList(String merchantId);
}