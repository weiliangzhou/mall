package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Order;
import com.zwl.model.vo.OrderVo;
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
 * @date 2018/7/913:30
 */
@RestController
@RequestMapping("/teacher/order")
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
        Order order = new Order();
        order.setMerchantId(merchantId);
        order.setPhone(phone);
        order.setOrderStatus(orderStatus);
        Result result = new Result();
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderService.getOrderList(order);
        OrderVo orderVo = new OrderVo();
        orderVo.setTotalPage(page.getTotal());
        orderVo.setPageNum(pageNum);
        orderVo.setOrderList(orderList);
        result.setData(orderVo);
        return JSON.toJSONString(result);
    }
}
