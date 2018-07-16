package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zwl.model.baseresult.Result;
import com.zwl.model.po.Order;
import com.zwl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/teacher/getOrderList")
    public String getOrderList(@RequestBody JSONObject jsonObject) {
        String merchantId =jsonObject.getString("merchantId");
        Integer pageNum=jsonObject.getInteger("pageNum");
        Integer pageSize=jsonObject.getInteger("pageSize");
        Result result = new Result();
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderService.getOrderList(merchantId);
        result.setData(orderList);
        return JSON.toJSONString(result);
    }
}
