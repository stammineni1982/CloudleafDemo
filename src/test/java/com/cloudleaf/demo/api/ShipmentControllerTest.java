package com.cloudleaf.demo.api;

import com.cloudleaf.demo.model.ShipmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShipmentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getShipment() throws Exception {

        ResponseEntity<ShipmentResponse> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/shipment/khcuualusutewkim44wht00q").toString(), ShipmentResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
