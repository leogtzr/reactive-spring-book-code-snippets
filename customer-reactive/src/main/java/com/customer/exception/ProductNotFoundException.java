package com.customer.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ProductNotFoundException extends RuntimeException {

    private String productId;

    public ProductNotFoundException(final String productId) {
        this.productId = productId;
    }

}
