package com.views.view;

import com.views.repository.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class CustomerViewController {

    private final CustomerRepository customerRepository;

    public CustomerViewController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/c/customers.php")
    public String customersView(final Model model) {
        final var modelMap = Map.of(
            "customers", this.customerRepository.findAll()
            , "type", "@Controller"
        );
        model.addAllAttributes(modelMap);

        // The return value is a string, the
        // view name to be resolved using
        // a view resolver.
        return "customers";
    }

}
