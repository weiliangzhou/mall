package com.zwl.dao.mapper;

import com.zwl.model.po.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface OrderMapper {

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    List<Order> getOrderList(Order order);

    Order findOrderByOrderNo(String orderNo);

    @Update("update ss_order set order_status = -1 where order_status= 0 ")
    void updateOrderSetOverTime();

    @Select("select count(*) from ss_order where order_status=1 and user_id =#{userId} and product_id =#{productId}")
    int getAlreadyBuyCount(@Param("userId") String userId, @Param("productId") Long productId);

    @Select("select sum(actual_money) from ss_order where order_status=1 and merchant_id =#{mid} and phone =#{registerMobile}")
    Integer getConsumeAmountByPhoneAndMid(@Param("registerMobile") String registerMobile, @Param("mid") String mid);
}
