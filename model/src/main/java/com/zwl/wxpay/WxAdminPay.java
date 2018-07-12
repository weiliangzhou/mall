package com.zwl.wxpay;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二师兄超级帅
 * @Title: WxAdminPay
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/7/1016:06
 */
@Data
public class WxAdminPay implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商户账号appid
     */
    public String mch_appid;
    /**
     * 微信支付商户号
     */
    public String mchid;
    /**
     * 随机串
     */
    public String nonce_str;
    /**
     * 商户订单号
     */
    public String partner_trade_no;
    /**
     * 用户id
     */
    public String openid;
    /**
     * 是否校验用户姓名 NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
     */
    public String check_name;
    /**
     * FORCE_CHECK：强校验真实姓名
     */
    public String re_user_name;
    /**
     * 金额 单位：分
     */
    public Integer amount;
    /**
     * 企业付款描述信息
     */
    public String desc;
    /**
     * ip地址
     */
    public String spbill_create_ip;
    /**
     * 签名
     */
    public String sign;
    public String paternerKey;


    private WxAdminPay() {
    }

    public static WxAdminPay New() {
        return new WxAdminPay();
    }

    public Map<String, String> build() {
        Map<String, String> map = new HashMap<>();
        map.put("mch_appid", this.getMch_appid());
        map.put("mchid", this.getMchid());
        map.put("nonce_str", this.getNonce_str());
        map.put("partner_trade_no", this.getPartner_trade_no());
        map.put("openid", this.getOpenid());
//        NO_CHECK：不校验真实姓名
//        FORCE_CHECK：强校验真实姓名
        map.put("check_name", "FORCE_CHECK");
        map.put("re_user_name", this.getRe_user_name());
        map.put("amount", this.getAmount() + "");//企业付款金额，单位为分
        map.put("desc", this.getDesc());
        map.put("spbill_create_ip", this.getSpbill_create_ip());
        map.put("sign", PaymentKit.createSign(map, this.getPaternerKey()));
        return map;
    }


    public String getMch_appid() {
        return mch_appid;
    }

    public WxAdminPay setMch_appid(String mch_appid) {
        if (StrKit.isBlank(mch_appid)) {
            throw new IllegalArgumentException("mch_appid 值不能为空");
        } else {
            this.mch_appid = mch_appid;
            return this;
        }
    }

    public String getMchid() {
        return mchid;
    }

    public WxAdminPay setMchid(String mchid) {
        if (StrKit.isBlank(mchid)) {
            throw new IllegalArgumentException("mchid 值不能为空");
        } else {
            this.mchid = mchid;
            return this;
        }
    }

    public String getNonce_str() {
        this.nonce_str = String.valueOf(System.currentTimeMillis());
        return nonce_str;
    }

    public WxAdminPay setNonce_str(String nonce_str) {
        if (StrKit.isBlank(nonce_str)) {
            throw new IllegalArgumentException("nonce_str 值不能为空");
        } else {
            this.nonce_str = nonce_str;
            return this;
        }
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public WxAdminPay setPartner_trade_no(String partner_trade_no) {
        if (StrKit.isBlank(partner_trade_no)) {
            throw new IllegalArgumentException("partner_trade_no 值不能为空");
        } else {
            this.partner_trade_no = partner_trade_no;
            return this;
        }
    }

    public String getOpenid() {
        return openid;
    }

    public WxAdminPay setOpenid(String openid) {
        if (StrKit.isBlank(openid)) {
            throw new IllegalArgumentException("openid 值不能为空");
        } else {
            this.openid = openid;
            return this;
        }
    }


    public String getRe_user_name() {
        return re_user_name;
    }

    public WxAdminPay setRe_user_name(String re_user_name) {
        if (StrKit.isBlank(re_user_name)) {
            throw new IllegalArgumentException("re_user_name 值不能为空");
        } else {
            this.re_user_name = re_user_name;
            return this;
        }
    }

    public Integer getAmount() {
        return amount;
    }

    public WxAdminPay setAmount(Integer amount) {
        if (null == amount) {
            throw new IllegalArgumentException("amount 值不能为空");
        } else {
            this.amount = amount;
            return this;
        }
    }

    public String getDesc() {
        return desc;
    }

    public WxAdminPay setDesc(String desc) {
        if (StrKit.isBlank(desc)) {
            throw new IllegalArgumentException("desc 值不能为空");
        } else {
            this.desc = desc;
            return this;
        }
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public WxAdminPay setSpbill_create_ip(String spbill_create_ip) {
        if (StrKit.isBlank(spbill_create_ip)) {
            throw new IllegalArgumentException("spbill_create_ip 值不能为空");
        } else {
            this.spbill_create_ip = spbill_create_ip;
            return this;
        }
    }

    public String getPaternerKey() {
        if (StrKit.isBlank(this.paternerKey)) {
            throw new IllegalArgumentException("paternerKey 未被赋值");
        } else {
            return this.paternerKey;
        }
    }

    public WxAdminPay setPaternerKey(String paternerKey) {
        if (StrKit.isBlank(paternerKey)) {
            throw new IllegalArgumentException("paternerKey 值不能为空");
        } else {
            this.paternerKey = paternerKey;
            return this;
        }
    }


}
