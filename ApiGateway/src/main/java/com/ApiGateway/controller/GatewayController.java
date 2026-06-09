package com.ApiGateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayController {



        private static final Logger log =
                LoggerFactory.getLogger(GatewayController.class);

        @GetMapping("/test")
        public String test() {

            log.info("Gateway Test");

            return "ok";
        }
    }

