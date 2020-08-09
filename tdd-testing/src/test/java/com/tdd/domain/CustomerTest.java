package com.tdd.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void create() {
        final Customer customer = new Customer("123", "foo");
        assertEquals(customer.getId(), "123");
        assertEquals(customer.getId(), "123");
        Assertions.assertThat(customer.getName())
                .isEqualToIgnoringCase("foo")
                ;
    }

}