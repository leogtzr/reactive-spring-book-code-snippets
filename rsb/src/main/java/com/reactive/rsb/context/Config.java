package com.reactive.rsb.context;

import com.reactive.rsb.service.TransactionTemplateCustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceConfiguration.class)
public class Config {

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public TransactionTemplateCustomerService customerService(final DataSource ds, final TransactionTemplate tt) {
        return new TransactionTemplateCustomerService(ds, tt);
    }

    @Bean
    public TransactionTemplate transactionTemplate(final PlatformTransactionManager tm) {
        return new TransactionTemplate(tm);
    }

}
