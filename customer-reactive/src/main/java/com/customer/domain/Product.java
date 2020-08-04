package com.customer.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Product {

    private String id;

    public Product(final String id) {
        this.id = id;
    }

}
