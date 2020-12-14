package com.cloudleaf.demo.config;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.AftershipOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class ApplicationConfig {

    @Value("${aftership.api.key}")
    private String afterShipApiKey;

    @Bean
    public AfterShip afterShip(){
        return new AfterShip(afterShipApiKey, new AftershipOption());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Shipment Service API",
                "Shipment is a service to create or track shipment for couriers FedEx, UPS, USPS",
                "V1",
                "urn:tos",
                new Contact("Sandeep Tammineni", "https://www.cloudleaf.com", "san.tammineni@gmail.com"),
                "NA",
                "NA",
                Collections.emptyList()
        );
    }
}
