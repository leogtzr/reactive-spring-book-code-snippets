package com.views.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Customer {

    private String id;
    private String name;

    public Customer(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(final String name) {
        this.name = name;
    }

}
