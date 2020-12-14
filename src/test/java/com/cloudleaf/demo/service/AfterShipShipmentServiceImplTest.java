package com.cloudleaf.demo.service;

import com.aftership.sdk.AfterShip;
import com.cloudleaf.demo.exception.ShipmentException;
import com.cloudleaf.demo.model.Courier;
import com.cloudleaf.demo.model.Shipment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AfterShipShipmentServiceImplTest {

    @Autowired
    private AfterShip afterShip;

    @Autowired
    @Qualifier("afterShipShipmentService")
    private ShipmentService afterShipShipmentService;

    @Test
    @DisplayName("Test afterShip object is not null")
    public void testAfterShipObjectNotNull() {
        assertNotNull(afterShip);
    }

    @Test
    @DisplayName("Test api to find shipment with valid id")
    public void testFindShipmentByIdWithValidId() throws Exception {
        Shipment shipment = afterShipShipmentService.getShipment("khcuualusutewkim44wht00q");
        assertNotNull(shipment);
        assertEquals("9374889676091297266845", shipment.getTrackingNumber());
        assertEquals("usps", shipment.getCourier().getCode());
    }

    @Test
    @DisplayName("Test api to find shipment with valid non exists id")
    public void testFindShipmentByIdWithValidNotExistsId() throws Exception {
        Exception exception = assertThrows(ShipmentException.class, () -> {
            afterShipShipmentService.getShipment("lhcuualusutewkim44wht00q");
        });

        String expectedMessage = "Tracking does not exist";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test exception message when api to find shipment with invalid id")
    public void testExceptionWhenFindShipmentByIdWithInValidId() throws Exception {
        Exception exception = assertThrows(ShipmentException.class, () -> {
            afterShipShipmentService.getShipment("test123");
        });

        String expectedMessage = "The value of `id` is invalid.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

   /* @Test
    @DisplayName("Test api to create shipment with valid ShipmentRequest")
    public void testCreateShipmentByIdWithValidShipment() throws Exception {
        Shipment request = new Shipment();
        shipment.setTrackingNumber(null);
        shipment.setOriginAddress("Address1_src");
        shipment.setDestinationAddress("Address_dst");
        shipment.setCourier(Courier.USPS);

        Shipment shipment = afterShipShipmentService.createShipment(request);
        assertNotNull(shipment);
    }*/

    @Test
    @DisplayName("Test api to create shipment with missing Tracking Number")
    public void testCreateShipmentByIdWithMissingTrackingNumber() throws Exception {
        Exception exception = assertThrows(ShipmentException.class, () -> {
            Shipment shipment = new Shipment();
            shipment.setTrackingNumber(null);
            shipment.setOriginAddress("Address1_src");
            shipment.setDestinationAddress("Address_dst");
            shipment.setCourier(Courier.USPS);
            afterShipShipmentService.createShipment(shipment);
        });

        String expectedMessage = "Tracking Number value is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Test api to create shipment with missing courier code")
    public void testCreateShipmentByIdWithMissingCourierCode() throws Exception {
        Exception exception = assertThrows(ShipmentException.class, () -> {
            Shipment shipment = new Shipment();
            shipment.setTrackingNumber("1Z8234X81300571974ull");
            shipment.setOriginAddress("Address1_src");
            shipment.setDestinationAddress("Address_dst");
            shipment.setCourier(null);
            afterShipShipmentService.createShipment(shipment);
        });

        String expectedMessage = "Courier Code  value is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    @DisplayName("Test api to create shipment with missing address")
    public void testCreateShipmentByIdWithMissingAddress() throws Exception {
        Exception exception = assertThrows(ShipmentException.class, () -> {
            Shipment shipment = new Shipment();
            shipment.setTrackingNumber("940011169900035246030");
            shipment.setOriginAddress(null);
            shipment.setDestinationAddress("Address_dst");
            shipment.setCourier(Courier.USPS);
            afterShipShipmentService.createShipment(shipment);
        });

        String expectedMessage = "Origin value is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
