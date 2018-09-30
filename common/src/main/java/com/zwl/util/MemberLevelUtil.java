package com.zwl.util;

/**
 * @author 二师兄超级帅
 * @Title: MemberLevelUtil
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/9/3010:33
 */
public class MemberLevelUtil {

    public static String getMemberLevelStr(Integer memberLevel) {
        switch (memberLevel) {
            case 99:
                return "校长";
            case 6:
                return "院长";
            case 5:
                return "班长";
            case 4:
                return "VIP学员";
            case 1:
                return "学员";
            case 0:
                return "会员";
        }
        return "";
    }
}
