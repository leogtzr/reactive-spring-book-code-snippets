package com.reactive.rsb.service;

import com.reactive.rsb.domain.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;

@Service
@Transactional
public class TransactionalCustomerService extends BaseCustomerService {

    public TransactionalCustomerService(DataSource ds) {
        super(ds);
    }

    @Override
    public Collection<Customer> save(final String... names) {
        return super.save(names);
    }

    @Override
    public Customer findByID(long id) {
        return super.findByID(id);
    }

    @Override
    public Collection<Customer> findAll() {
        return super.findAll();
    }
}
