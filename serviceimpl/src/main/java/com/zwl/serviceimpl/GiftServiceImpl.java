package com.zwl.serviceimpl;

import com.zwl.dao.mapper.GiftMapper;
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
}
