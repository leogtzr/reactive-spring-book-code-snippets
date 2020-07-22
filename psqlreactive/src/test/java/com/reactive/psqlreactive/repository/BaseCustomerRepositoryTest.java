package com.reactive.psqlreactive.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public abstract class BaseCustomerRepositoryTest {

    public abstract SimpleCustomerRepository getRepository();

    @Autowired
    private CustomerDatabaseInitializer initializer;

}
