package com.zwl.service;

import com.zwl.model.Merchant;

/**
 * 商户service
 */
public interface MerchantService {
    Merchant getMerchantByMerchantId(String merchantId);
}
