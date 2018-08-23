package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Order;
import com.zwl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: OrderController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/2310:42
 */
@RestController
@RequestMapping("/wx/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/getOrderList")
    public String getOrderList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Integer pageNum = jsonObject.getInteger("pageNum");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String phone = jsonObject.getString("phone");
        Integer orderStatus = jsonObject.getInteger("orderStatus");
        String userId = jsonObject.getString("userId");
        Order order = new Order();
        order.setMerchantId(merchantId);
        order.setPhone(phone);
        order.setOrderStatus(orderStatus);
        order.setUserId(userId);
        Result result = new Result();
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderService.getOrderList(order);
        result.setData(orderList);
        return JSON.toJSONString(result);
    }

    /**
     * 确认收货
     */
    @RequestMapping("/confirmReceipt")
    public String confirmReceipt(@RequestBody JSONObject jsonObject) {
        String orderNo = jsonObject.getString("orderNo");
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus(2);
        int updateCount = orderService.updateOrder(order);
        if (updateCount == 0)
            BSUtil.isTrue(false, "系统繁忙，请稍后重试！");
        Result result = new Result();
        return JSON.toJSONString(result);
    }


}
