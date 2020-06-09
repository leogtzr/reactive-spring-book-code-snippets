package com.reactive.rsb.controller;

import com.reactive.rsb.domain.Customer;
import com.reactive.rsb.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class BootifulRestController {

    private final CustomerService customerService;

    public BootifulRestController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public Collection<Customer> get() {
        return this.customerService.findAll();
    }

}
