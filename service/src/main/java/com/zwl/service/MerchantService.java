package com.zwl.service;

import com.zwl.model.po.Merchant;

/**
 * 商户service
 */
public interface MerchantService {
    Merchant getMerchantByMerchantId(String merchantId);
}
