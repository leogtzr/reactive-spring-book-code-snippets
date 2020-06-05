package com.reactive.rsb.service;

import com.reactive.rsb.RsbApplication;
import com.reactive.rsb.domain.Customer;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.Assert;

import java.util.stream.Stream;

@Log4j2
public class Demo {
    public static void workWithCustomerService(final Class<?> label
            , final CustomerService customerService) {
        log.info("====================================");
        log.info(label.getName());
        log.info("====================================");

        Stream.of("A", "B", "C").map(customerService::save)
                .forEach(customer -> log.info("saved " + customer.toString()));

        customerService.findAll().forEach(customer -> {
            final long customerId = customer.getId();
            final Customer byId = customerService.findByID(customerId);
            log.info("found " + byId.toString());
            Assert.notNull(byId, "the resulting customer should not be null");
            Assert.isTrue(byId.equals(customer), "we should be able to query for " + "this result");
        });
    }
}
