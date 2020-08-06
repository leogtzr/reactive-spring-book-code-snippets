package com.endpoints.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class HttpSecurityConfiguration {

    // 1
    @Bean
    public MapReactiveUserDetailsService authentication() {
        final var leo = User.withDefaultPasswordEncoder()
                .username("leo")
                .roles("USER")
                .password("admin")
                .build()
                ;
        return new MapReactiveUserDetailsService(leo);
    }

    // 2
    @Bean
    public SecurityWebFilterChain authorization(final ServerHttpSecurity http) {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(spec -> spec.pathMatchers("/greet/authenticated").authenticated()
                        .anyExchange().permitAll()
                ).build();
    }

}
