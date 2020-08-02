package com.customer.utils;

import lombok.Data;

@Data
public class CountAndString {

    private final String message;
    private final long count;

    public CountAndString(final long c) {
        this.count = c;
        this.message = "# " + this.count;
    }

}
