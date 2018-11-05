package com.zwl.util;

/**
 * 获取字符串长度
 */
public class CheseUtil {
    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value  指定的字符串
     * @param length 一个中文2个字符长度
     * @return 字符串的长度
     */
    public static boolean checkLength(String value, int length) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        if (valueLength > length) {
            return false;
        } else {
            return true;
        }
    }
}
