package com.cloudleaf.demo.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    /**
     * API to check health of service
     */
    @RequestMapping(path = "/health", produces = "application/json", method = RequestMethod.GET)
    public String health() {
        return "Shipment Service APIs!!!";
    }
}
