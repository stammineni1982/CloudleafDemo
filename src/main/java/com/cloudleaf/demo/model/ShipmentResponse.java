package com.cloudleaf.demo.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ShipmentResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Shipment shipment;

    public ShipmentResponse() {
        setStatus(200);
        setMessage("OK");
    }

    public ShipmentResponse(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public String toString() {
        return "ShipmentResponse{" +
                "shipment=" + shipment +
                "} " + super.toString();
    }
}
