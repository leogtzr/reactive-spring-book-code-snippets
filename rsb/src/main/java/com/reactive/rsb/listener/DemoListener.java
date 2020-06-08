package com.reactive.rsb.listener;

import com.reactive.rsb.service.CustomerService;
import com.reactive.rsb.service.Demo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DemoListener {

    private final CustomerService customerService;

    public DemoListener(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
In this case, our bean listens for the ApplicationReadyEvent that tells us when the
application is just about ready to start processing requests. This event gets called as late as possible
in the startup sequence as possible.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void exercise() {
        Demo.workWithCustomerService(getClass(), this.customerService);
    }
}
