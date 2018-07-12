package com.zwl.dao.config;

/**
 * ${保存一个线程安全的DatabaseType容器}
 * author 二师兄超级帅
 * create 2018-06-29 13:34
 **/
public class DatabaseContextHolder {

    //用于存放多线程环境下的成员变量
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }
}
