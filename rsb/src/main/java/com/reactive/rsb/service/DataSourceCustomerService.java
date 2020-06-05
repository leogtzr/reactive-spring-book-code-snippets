package com.reactive.rsb.service;

import javax.sql.DataSource;

public class DataSourceCustomerService extends BaseCustomerService {

    public DataSourceCustomerService(final DataSource ds) {
        super(ds);
    }

}
