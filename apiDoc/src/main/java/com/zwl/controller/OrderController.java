package com.zwl.controller;

import com.terran4j.commons.api2doc.annotations.Api2Doc;
import com.terran4j.commons.api2doc.annotations.ApiComment;
import com.zwl.model.po.Order;
import com.zwl.model.vo.OrderVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/913:30
 */
@Api2Doc(name = "订单管理")
@ApiComment(seeClass = Order.class)
@RestController
public class OrderController {
    @ApiComment("订单列表")
    @RequestMapping(name = "订单列表",
            value = "/teacher/order/getOrderList", method = RequestMethod.POST)
    public OrderVo getOrderList(@ApiComment("商户号") String merchantId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize, @ApiComment("手机号") String phone, @ApiComment("订单状态 0未付款 1成功 -1超时关闭") Integer orderStatus) {
        OrderVo orderVo=new OrderVo();
        return orderVo;
    }
    @ApiComment("个人消费列表")
    @RequestMapping(name = "个人消费列表",
            value = "/teacher/order/getOrderList1", method = RequestMethod.POST)
    public List<Order> getOrderListByUserId(@ApiComment("商户号") String merchantId,@ApiComment("userId") String userId, @ApiComment("pageNum") Integer pageNum, @ApiComment("pageSize") Integer pageSize) {
        List<Order> orderList = new ArrayList<>();
        return orderList;
    }


}
