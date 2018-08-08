package com.zwl.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadVariable {


    public static final String USER_ID = "userId";
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();


    /**
     * 获得线程中保存的属性.
     *
     * @param attribute 属性名称
     * @return 属性值
     */
    public static Object get(String attribute) {
        Map map = (Map) threadLocal.get();
        if (map == null) {
            return null;
        }
        return map.get(attribute);
    }


    /**
     * report
     * 获得线程中保存的登录用户信息.
     */
    public static String getUserID() {
        Map map = (Map) threadLocal.get();
        if (map == null) {
            return null;
        }
        Object obj = map.get(USER_ID);

        if (obj != null) {
            return (String) obj;
        }
        return null;
    }


    public static void setUserID(String userID) {
        Map map = (Map) threadLocal.get();

        if (map == null) {
            map = new HashMap();
            threadLocal.set(map);
        }
        map.put(USER_ID, userID);
    }

    /**
     * 清除线程中保存的数据
     */
    public static void clearThreadVariable() {
        threadLocal.remove();
    }
}
