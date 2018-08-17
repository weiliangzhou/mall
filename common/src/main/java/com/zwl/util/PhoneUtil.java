package com.zwl.util;

/**
 * @author 二师兄超级帅
 * @Title: PhoneUtils
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/8/1716:02
 */
public class PhoneUtil {
    public static String replace(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
    }
}
