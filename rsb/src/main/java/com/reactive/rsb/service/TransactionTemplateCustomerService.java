package com.reactive.rsb.service;

import com.reactive.rsb.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Collection;

@Service
// Enable transaction demarcation automatically in each method.
// It is also possible to annotate each method individually instead of the whole class.
@Transactional
public class TransactionTemplateCustomerService extends BaseCustomerService {

    private final TransactionTemplate transactionTemplate;

    public TransactionTemplateCustomerService(final DataSource ds, final TransactionTemplate transactionTemplate) {
        super(ds);
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Collection<Customer> save(final String... names) {
        return super.save(names);
        // return this.transactionTemplate.execute(s -> super.save(names));
    }

    @Override
    public Customer findByID(final long id) {
        return super.findByID(id);
        // return this.transactionTemplate.execute(s -> super.findByID(id));
    }

    @Override
    public Collection<Customer> findAll() {
        return super.findAll();
        // return this.transactionTemplate.execute(s -> super.findAll());
    }
}
