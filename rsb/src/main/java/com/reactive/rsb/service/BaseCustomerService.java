package com.reactive.rsb.service;

import com.reactive.rsb.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BaseCustomerService implements CustomerService {

    private final RowMapper<Customer> rowMapper =
            (rs, i) -> new Customer(rs.getLong("id"), rs.getString("NAME"));

    private final JdbcTemplate jdbcTemplate;

    protected BaseCustomerService(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Collection<Customer> save(String... names) {

        final List<Customer> customers = new ArrayList<>();
        for (final String name : names) {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbcTemplate.update(connection -> {
               final PreparedStatement ps = connection.prepareStatement(
                       "insert into CUSTOMERS (name) values(?)"
                       , Statement.RETURN_GENERATED_KEYS
               );
               ps.setString(1, name);
               return ps;
            });
            final Long keyHolderKey = Objects.requireNonNull(keyHolder.getKey())
                    .longValue();
            final Customer customer = this.findByID(keyHolderKey);
            Assert.notNull(name, "the name given must not be null");
            customers.add(customer);
        }

        return customers;
    }

    @Override
    public Customer findByID(long id) {
        final String sql = "select * from CUSTOMERS where id = ?";
        return this.jdbcTemplate.queryForObject(sql, this.rowMapper, id);
    }

    @Override
    public Collection<Customer> findAll() {
        return this.jdbcTemplate.query("select * from CUSTOMERS", rowMapper);
    }
}
