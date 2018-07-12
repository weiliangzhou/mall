package com.zwl.exception;

/**
 * Created by 二师兄超级帅 on 2018/7/8.
 */
public class BSUtil {
    public static void isTrue(boolean expression, String error) {
        if (!expression) {
            throw new BusinessException(error);
        }
    }
}
