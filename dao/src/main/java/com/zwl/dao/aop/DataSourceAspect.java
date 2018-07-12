package com.zwl.dao.aop;

import com.zwl.dao.config.DatabaseContextHolder;
import com.zwl.dao.config.DatabaseType;
import com.zwl.dao.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * ${动态处理数据源，根据命名区分}
 * author 二师兄超级帅
 * create 2018-06-29 13:43
 **/
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class DataSourceAspect {



    @Pointcut("execution(* com.zwl.dao.mapper.*.*(..))")//切点
    public void aspect() {

    }


    @Before("aspect()")
    public void before(JoinPoint point) { //在指定切点的方法之前执行
        String className = point.getTarget().getClass().getName();
        String method = point.getSignature().getName();
        String args = StringUtils.join(point.getArgs(), ",");
        log.info("className:{}, method:{}, args:{} ", className, method, args);
        try {
            for (DatabaseType type : DatabaseType.values()) {
                List<String> values = DynamicDataSource.METHOD_TYPE_MAP.get(type);
                for (String key : values) {
                    if (method.startsWith(key)) {
                        log.info(">>{} 方法使用的数据源为:{}<<", method, key);
                        DatabaseContextHolder.setDatabaseType(type);
                        DatabaseType types = DatabaseContextHolder.getDatabaseType();
                        log.info(">>{}方法使用的数据源为:{}<<", method, types);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}