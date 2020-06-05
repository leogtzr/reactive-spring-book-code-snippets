package com.reactive.rsb.context;

import com.reactive.rsb.service.DataSourceUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
public class DataSourceConfiguration {

    @Configuration
    @Profile("prod")
    @PropertySource("application-prod.properties")
    public static class ProductionConfiguration {
        @Bean
        public DataSource productionDataSource(
                @Value("${spring.datasource.url}") String url,
                @Value("${spring.datasource.username}") String username,
                @Value("${spring.datasource.password}") String password,
                @Value("${spring.datasource.driver-class-name}") Class<Driver> driverClass) {

            final DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
            dataSource.setDriverClassName(driverClass.getName());
            return dataSource;
        }
    }

    @Configuration
    @Profile("default")
    @PropertySource("application-default.properties")
    public static class DevelopmentConfiguration {
        @Bean
        public DataSource developmentDataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        }
    }

    @Bean
    public DataSourcePostProcessor dataSourcePostProcessor() {
        return new DataSourcePostProcessor();
    }

    private static class DataSourcePostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
            if (bean instanceof DataSource) {
                DataSourceUtils.initializeDdl(DataSource.class.cast(bean));
            }
            return bean;
        }
    }

}
