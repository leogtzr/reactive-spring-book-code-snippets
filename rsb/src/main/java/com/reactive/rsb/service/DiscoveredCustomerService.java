package com.reactive.rsb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Service
public class DiscoveredCustomerService extends TransactionTemplateCustomerService {

    public DiscoveredCustomerService(final DataSource ds, final TransactionTemplate transactionTemplate) {
        super(ds, transactionTemplate);
    }

}
