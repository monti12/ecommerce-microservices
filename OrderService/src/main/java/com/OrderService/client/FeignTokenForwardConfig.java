package com.OrderService.client;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
@Configuration
public class FeignTokenForwardConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return requestTemplate -> {

            ServletRequestAttributes attributes =
                    (ServletRequestAttributes)
                            RequestContextHolder.getRequestAttributes();

            if (attributes == null) {
                return;
            }

            String authorization =
                    attributes.getRequest().getHeader("Authorization");

            if (authorization != null) {
                requestTemplate.header("Authorization", authorization);
            }
        };
    }
}