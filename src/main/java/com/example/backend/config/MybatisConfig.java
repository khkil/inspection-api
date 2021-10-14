package com.example.backend.config;

import com.example.backend.common.beans.RefreshableSqlSessionFactoryBean;
import com.example.backend.util.enumerator.QuestionType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan(basePackages = "com.example.backend")

public class MybatisConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public RefreshableSqlSessionFactoryBean RefreshableSqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        RefreshableSqlSessionFactoryBean factoryBean = new RefreshableSqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/*.xml"));
        factoryBean.setTypeHandlers(new TypeHandler[] {
                new QuestionType.TypeHandler()
        });

        return factoryBean;
    }
   /* @Bean
    public SqlSessionFactory refreshableSqlSessionFactoryBean(DataSource dataSource) throws Exception {
        RefreshableSqlSessionFactoryBean refreshableSqlSessionFactoryBean = new RefreshableSqlSessionFactoryBean();
        refreshableSqlSessionFactoryBean.setDataSource(dataSource);
        refreshableSqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        refreshableSqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:config/mybatis-config.xml"));
        refreshableSqlSessionFactoryBean.setTypeHandlers(new TypeHandler[] {
            new Role.TypeHandler()
        });
        return refreshableSqlSessionFactoryBean.getObject();
    }*/
}
