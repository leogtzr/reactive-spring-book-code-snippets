package com.reactive.rsb.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class BootifulCustomerService extends TransactionalCustomerService {

    public BootifulCustomerService(final DataSource ds) {
        super(ds);
    }

}
