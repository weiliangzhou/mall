package com.zwl.dao.config;

/**
 * ${DESCRIPTION}
 * author 二师兄超级帅
 * create 2018-06-27 10:43
 **/
//@Configuration
//public class MybatisConfig {
//    @Value("${mapper.config-location}")
//    private String mapperLocationPattern;
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource(){return new com.alibaba.druid.pool.DruidDataSource();
//    }
//
//    @Bean(name="sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws  Exception{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource());
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocationPattern));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//}
