package com.zwl.dao.mapper;

import com.zwl.model.po.Product;
import com.zwl.model.vo.ProductItemVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {


    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> getProductList(String merchantId);

    List<Product> getAdminProductList();

    @Select("select level ,level_name as levelName from ss_product  where merchant_id=#{merchantId} and available =1")
    List<ProductItemVo> getUserLevelItemsList(@Param("merchantId") String merchantId);

    @Select("select maid_percent from ss_product  where level=#{referrerLevel} and available =1")
    Integer getMaidPercentByLevel(@Param("referrerLevel") Integer referrerLevel);

    Product getProductByMemberLevel(Integer memberLevel);

    @Update("update ss_product set buy_count = buy_count+1 where id = #{productId}")
    int updateBuyCountById(@Param("productId") Long productId);
}