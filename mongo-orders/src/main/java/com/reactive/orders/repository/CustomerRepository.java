package com.reactive.orders.repository;

import com.reactive.orders.domain.Customer;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, String> {

    /*
   Some data sources can tell the clients what’s changed; they can tell the client about new data that
matches a predicate or query. Apache Geode and Oracle Coherence are both types of distributed data
grids. They support continuous queries. Continuous queries invert the traditional polling arrangement
between the client and the data source. A client registers a continuous query with the data grid, and
the data grid asserts any new data in the grid against the query. If any new data matches the query, the
data grid notifies the subscribed clients.
MongoDB supports something like continuous queries but gives it the equally as descriptive name
tailable queries. It’s analogous to using the tail -f command on the command line to follow the output
to a file.
     */
    @Tailable
    /*
        The @Tailable annotation tells Spring Data not to close the client cursor when executing the query
derived from the finder method.
     */
    Flux<Customer> findByName(String name);

}
