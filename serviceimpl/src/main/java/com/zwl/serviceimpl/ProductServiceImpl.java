package com.zwl.serviceimpl;

import com.zwl.dao.mapper.OrderFlowMapper;
import com.zwl.dao.mapper.OrderMapper;
import com.zwl.dao.mapper.ProductMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.*;
import com.zwl.model.vo.BuyResult;
import com.zwl.service.ProductService;
import com.zwl.service.UserInfoService;
import com.zwl.service.WxUserService;
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
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderFlowMapper orderFlowMapper;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private UserInfoService userInfoService;

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
    public BuyResult  buy(Product product) {
//        String productId, String merchantId, String userId
        //查询产品信息
        //生成订单(订单号使用 年月日时分秒+mch_no+userId（自增的Id）)
        //生成订单操作日志流水表

        SimpleDateFormat sdf_yMdHm = new SimpleDateFormat("yyyyMMddHHmm");
        String merchantId = product.getMerchantId();
        String userId = product.getUserId();
        //获取userId的自增Id
        User user = wxUserService.getUserByUserId(userId);
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
        Integer alreadyLevel = wxUserService.getMemberLevel(userId);

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
        orderFlowMapper.insertSelective(orderFlow);
        BuyResult buyResult=new BuyResult();
        buyResult.setOrderNo(orderNo);
        buyResult.setTotalFee(price);
        buyResult.setTotalFeeDesc(price/100+"");
        buyResult.setOpenId(user.getWechatOpenid());
        return buyResult;
    }
}
