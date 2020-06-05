package com.reactive.rsb;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactive.rsb.service.*;
import com.reactive.rsb.utils.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

// @SpringBootApplication
public class RsbApplication {

	public static void main(final String[] args) {

		final ApplicationContext ac = SpringUtils.run(RsbApplication.class, "prod");
		final CustomerService cs = ac.getBean(CustomerService.class);
		Demo.workWithCustomerService(RsbApplication.class, cs);

//		final DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
//		final DataSource initializedDataSource = DataSourceUtils.initializeDdl(dataSource);
//		final PlatformTransactionManager dsTxManager = new DataSourceTransactionManager(initializedDataSource);
//		final TransactionTemplate tt = new TransactionTemplate(dsTxManager);
//		final TransactionTemplateCustomerService customerService = new TransactionTemplateCustomerService(
//			initializedDataSource, tt
//		);
//
//		Demo.workWithCustomerService(RsbApplication.class, customerService);

//		// SpringApplication.run(RsbApplication.class, args);
//		DevelopmentOnlyCustomerService customerService = new DevelopmentOnlyCustomerService();
//		Demo.workWithCustomerService(RsbApplication.class, customerService);
	}

}
