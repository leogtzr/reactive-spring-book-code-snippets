package com.reactive.psqlreactive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class Config {

//    @Bean
//    public H2ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url("mem:testdb;DB_CLOSE_DELAY=-1;")
//                        .username("sa")
//                        .build()
//        );
//    }

}
