package com.reactive.rsb.service;

import com.reactive.rsb.domain.Customer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Collection;

public class TransactionTemplateCustomerService extends BaseCustomerService {

    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateCustomerService(final DataSource ds, final TransactionTemplate transactionTemplate) {
        super(ds);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Collection<Customer> save(final String... names) {
        return this.transactionTemplate.execute(s -> super.save(names));
    }

    @Override
    public Customer findByID(final long id) {
        return this.transactionTemplate.execute(s -> super.findByID(id));
    }

    @Override
    public Collection<Customer> findAll() {
        return this.transactionTemplate.execute(s -> super.findAll());
    }
}
