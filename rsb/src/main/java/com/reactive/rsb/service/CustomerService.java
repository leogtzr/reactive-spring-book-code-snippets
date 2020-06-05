package com.reactive.rsb.service;

import com.reactive.rsb.domain.Customer;

import java.util.Collection;

public interface CustomerService {

    Collection<Customer> save(String ... names);
    Customer findByID(long id);
    Collection<Customer> findAll();

}
