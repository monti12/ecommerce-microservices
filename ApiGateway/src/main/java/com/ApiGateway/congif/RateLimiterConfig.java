package com.ApiGateway.congif;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {

        return exchange -> {
            String user =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("Authorization");

            if (user == null) {
                user = exchange.getRequest()
                        .getRemoteAddress()
                        .getAddress()
                        .getHostAddress();
            }

            return Mono.just(user);
        };
    }
}