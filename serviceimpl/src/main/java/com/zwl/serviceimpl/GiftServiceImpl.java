package com.zwl.serviceimpl;

import com.zwl.dao.mapper.GiftMapper;
import com.zwl.model.exception.BSUtil;
import com.zwl.model.po.Gift;
import com.zwl.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二师兄超级帅
 * @Title: GiftServiceImpl
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/10/3111:34
 */
@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftMapper giftMapper;

    @Override
    public List getGiftList(Integer queryType, String merchantId) {
        return giftMapper.getGiftList(queryType, merchantId);
    }

    @Override
    public Gift getGiftDetailById(Long giftId) {
        return giftMapper.getGiftDetailById(giftId);
    }

    @Override
    public Boolean updateGiftStock(Long giftId, Integer stock, Integer currentStock) {
        if (null == giftId) {
            BSUtil.isTrue(false, "商品编号不能为空");
        }
        if (null == stock || null == currentStock) {
            BSUtil.isTrue(false, "库存数量错误");
        }
        if (stock < currentStock) {
            BSUtil.isTrue(false, "修改库存数量异常");
        }
        int updateLine = giftMapper.updateGiftStock(giftId, stock, currentStock);
        if (updateLine <= 0) {
            BSUtil.isTrue(false, "更新库存出错 请稍后尝试");
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateGiftBuyCount(Long giftId, Integer buyCount) {
        if (null == giftId) {
            BSUtil.isTrue(false, "商品编号不能为空");
        }
        if (null == buyCount) {
            BSUtil.isTrue(false, "无效销量");
        }
        giftMapper.updateGiftBuyCount(giftId, buyCount);
        return Boolean.TRUE;
    }
}
