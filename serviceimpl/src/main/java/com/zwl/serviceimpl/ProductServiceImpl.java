package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OrderFlowMapper;
import com.zwl.dao.mapper.OrderMapper;
import com.zwl.dao.mapper.ProductMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.model.vo.ProductItemVo;
import com.zwl.service.MerchantService;
import com.zwl.service.ProductService;
import com.zwl.service.UserInfoService;
import com.zwl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: ProductServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/515:15
 */
@SuppressWarnings("all")
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderFlowMapper orderFlowMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MerchantService merchantService;

    @Override
    public List getProductList(String merchantId) {
        return productMapper.getProductList(merchantId);
    }

    @Override
    public void updateProduct(Product product) {
//        会员名称
//        产品名称
//        分佣比例
//        产品价格
//        有效期
//        String levelName = product.getLevelName();
//        String productName = product.getProductName();
//        Integer maidPercent = product.getMaidPercent();
//        Integer price = product.getPrice();
//        Integer validityTime = product.getValidityTime();
//        if (StringUtils.isBlank(levelName) || StringUtils.isBlank(productName) || null == maidPercent || null == price || null == validityTime)
//            return -1;
//        else
        int count = productMapper.updateByPrimaryKeySelective(product);
        if (0 == count)
            BSUtil.isTrue(false, "更新失败,请稍后重试！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BuyResult buy(Product product) {
        log.info("开始生成订单================================>"+product);
        String phone = product.getPhone();
        String userId = "";
        User user = null;
        //根据phone查询userId
        if (!StringUtils.isBlank(phone)) {
            User queryUser = new User();
            queryUser.setRegisterMobile(phone);
            user = userService.getOneByParams(queryUser);
            if (user == null)
                BSUtil.isTrue(false,"请先进入微信小程序授权并绑定手机号");

            userId = user.getUserId();
        } else {
            userId = product.getUserId();
            user = userService.getByUserId(userId);
        }
        Merchant merchant=merchantService.getMerchantByMerchantId(user.getMerchantId());
        String appid=merchant.getAppId();
        //查询产品信息
        //生成订单(订单号使用 年月日时分秒+mch_no+userId（自增的Id）)
        //生成订单操作日志流水表
        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        String merchantId = product.getMerchantId();
        //获取userId的自增Id
        Long userLongId = user.getId();
        Long productId = product.getId();
        Product localProduct = productMapper.selectByPrimaryKey(productId);
        Integer level = localProduct.getLevel();
        String levelName = localProduct.getLevelName();
        String productName = localProduct.getProductName();
        Integer maidPercent = localProduct.getMaidPercent();
        Integer validityTime = localProduct.getValidityTime();
        Integer price = localProduct.getPrice();
        //不能购买同类产品，先查询当前购买的未到期的产品，如果存在判断
        //如果过期时间小于当前时间，则可以购买任何产品
        //否则判断等级如果等级一致则不可购买
        Integer alreadyLevel = userService.getMemberLevel(userId);

        if (null != alreadyLevel && alreadyLevel >= level)
            BSUtil.isTrue(false, "不能重复购买此商品！");
        String orderNo = sdf_yMdHm.format(new Date()) + merchantId + userLongId;
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setProductId(productId);
        order.setLevel(level);
        order.setLevelName(levelName);
        order.setProductName(productName);
        order.setMaidPercent(maidPercent);
        order.setValidityTime(validityTime);
        order.setActualMoney(price);
        order.setMoney(price);
        order.setUserId(userId);
        order.setMerchantId(merchantId);
        order.setRealName(user.getRealName());
        order.setPhone(user.getRegisterMobile());
        order.setOrderStatus(0);
        log.info("订单数据" + order);
        try {
            orderMapper.insertSelective(order);
        } catch (Exception e) {
            e.printStackTrace();
            BSUtil.isTrue(false, "系统繁忙,请稍后重试！");
        }
        OrderFlow orderFlow = new OrderFlow();
        orderFlow.setOrderNo(orderNo);
        orderFlow.setOrderStatus(0);
        orderFlow.setActualMoney(price);
        orderFlow.setMoney(price);
        log.info("订单流水数据" + order);
        orderFlowMapper.insertSelective(orderFlow);
        BuyResult buyResult = new BuyResult();
        buyResult.setOrderNo(orderNo);
        buyResult.setTotalFee(price);
        buyResult.setTotalFeeDesc(price / 100 + "");
        buyResult.setOpenId(user.getWechatOpenid());
        buyResult.setMerchantId(merchantId);
        log.info("订单完成返回结果" + buyResult);
        return buyResult;
    }

    @Override
    public List<Product> getAdminProductList() {
        return productMapper.getAdminProductList();
    }

    @Override
    public List<ProductItemVo> getUserLevelItemsList(String merchantId) {
        return productMapper.getUserLevelItemsList(merchantId);
    }
}
