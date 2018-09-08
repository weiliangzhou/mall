package com.zwl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwl.model.baseresult.Result;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.groups.Buy;
import com.zwl.model.groups.H5Buy;
import com.zwl.model.po.Merchant;
import com.zwl.model.po.Product;
import com.zwl.model.po.User;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.ProductVo;
import com.zwl.model.wxpay.IpKit;
import com.zwl.model.wxpay.StrKit;
import com.zwl.model.wxpay.WxPayVo;
import com.zwl.service.*;
import com.zwl.util.MathUtil;
import com.zwl.util.ThreadVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:25
 */
@RestController
@RequestMapping("/wx/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MsgSenderService msgSenderService;
    @Autowired
    private UserService userService;
    @Autowired
    private WxPayService wxPayService;
    private static final long CONSTANT_WAN = 10000;

    @PostMapping("/auth/buy")
    public String buy(@Validated(Buy.class) @RequestBody Product product) {
        Result result = new Result();
//        Long id = Long.parseLong(request.getParameter("id"));
//        String userId = request.getParameter("userId");
//        String merchantId = request.getParameter("merchantId");
//        Product product = new Product();
//        product.setId(id);
//        product.setUserId(userId);
//        product.setMerchantId(merchantId);
        BuyResult buyResult = productService.buy(product);
        result.setData(buyResult);
//        String result = "{'ret':'true','data':"+JSON.toJSONString(buyResult)+"}";
        //加上返回参数
//        result = callback + "(" + result +")";
        return JSON.toJSONString(result);
    }
    @PostMapping("/H5Buy")
    public String h5Buy(@Validated(H5Buy.class)@RequestBody Product product) {
        Result result = new Result();
//        Long id = Long.parseLong(request.getParameter("id"));
//        String userId = request.getParameter("userId");
//        String merchantId = request.getParameter("merchantId");
//        Product product = new Product();
//        product.setId(id);
//        product.setUserId(userId);
//        product.setMerchantId(merchantId);
        String code =product.getCode();
        String phone =product.getPhone();
//        校验验证码
        //  校验验证码
        boolean isValidate = msgSenderService.checkCode(phone, code,"2");
        if (!isValidate)
            BSUtil.isTrue(false, "验证码错误");
        BuyResult buyResult = productService.buy(product);
        result.setData(buyResult);
//        String result = "{'ret':'true','data':"+JSON.toJSONString(buyResult)+"}";
        //加上返回参数
//        result = callback + "(" + result +")";
        return JSON.toJSONString(result);
    }
    @PostMapping("/newH5Buy")
    public String newH5Buy(HttpServletRequest request, @Validated(H5Buy.class) @RequestBody Product product) {
        Result result = new Result();
        BuyResult buyResult = productService.newH5Buy(product);
        String orderNo = buyResult.getOrderNo();
        Integer totalFee = buyResult.getTotalFee();
        String merchantId = buyResult.getMerchantId();
        Merchant merchant = merchantService.getMerchantByMerchantId(merchantId);
        String gzhAppId = merchant.getGzAppId();
        String gzhKey = merchant.getGzAppKey();
        String userId_local = product.getUserId();
        User user = userService.getByUserId(userId_local);
        String wxPayKey = merchant.getWxPayKey();
        String redirectUrl=product.getRedirectUrl();
        String realIp = IpKit.getRealIp(request);
        if (StrKit.isBlank(realIp)) {
            realIp = "127.0.0.1";
        }
        WxPayVo wxPayVo = wxPayService.pay(realIp, user.getGzhOpenid(), orderNo, totalFee.toString(), gzhAppId, merchantId, wxPayKey);
        result.setData(wxPayVo);
        return JSON.toJSONString(result);
    }

    /**
     * 获取商品列表
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/getProductList")
    public String getProductList(@RequestBody JSONObject jsonObject) {
        String merchantId = jsonObject.getString("merchantId");
        Result result = new Result();
        List<Product> productList = productService.getProductList(merchantId);
        List<ProductVo> listVo = new ArrayList<>();
        for (Product p : productList) {
            ProductVo productVo = new ProductVo();
            productVo.setId(p.getId());
            productVo.setProductName(p.getProductName());
            //小程序 不返回带格式介绍（数据量可能比较大）
            // productVo.setContent(p.getContent());
            productVo.setContentText(p.getContentText());
            productVo.setImageUrl(p.getImageUrl());
            //返回给前端 单位:元
            productVo.setPrice(p.getPrice());
            if (p.getPrice() != null)
                productVo.setPriceDesc(String.valueOf(p.getPrice() / 100));
            Integer buyCount = p.getBuyCount() == null ? 0 : p.getBuyCount();
            String buyCountDesc = String.valueOf(buyCount);
            if (buyCount > CONSTANT_WAN) {
                buyCountDesc = MathUtil.changeWan(buyCountDesc) + "万";
            }
            buyCountDesc = buyCountDesc + "人购买";
            productVo.setBuyCount(buyCount);
            productVo.setBuyCountDesc(buyCountDesc);
            listVo.add(productVo);
        }
        result.setData(listVo);
        return JSON.toJSONString(result);
    }

    /**
     * 根据id获取商品
     *
     * @return
     */
    @PostMapping(value = "/getProductById")
    public String getProductById(@RequestBody JSONObject jsonObject) {
        Result result = new Result();
        Long id = jsonObject.getLong("id");
        Product p = productService.getProductById(id);
        ProductVo productVo = new ProductVo();
        productVo.setId(p.getId());
        productVo.setContent(p.getContent());
        productVo.setContentText(p.getContentText());
        productVo.setImageUrl(p.getImageUrl());
        productVo.setPrice(p.getPrice());
        if (p.getPrice() != null)
            productVo.setPriceDesc(String.valueOf(p.getPrice() / 100));
        result.setData(productVo);
        return JSON.toJSONString(result);
    }
}
