package com.cloudleaf.demo.service;

import com.cloudleaf.demo.exception.ShipmentException;
import com.cloudleaf.demo.model.Shipment;
import org.springframework.stereotype.Service;

@Service
public interface ShipmentService {

    /**
     * Create shipment
     * @param shipment
     * @return Shipment
     * @throws ShipmentException
     */
    public Shipment createShipment(Shipment shipment) throws ShipmentException;

    /**
     * Get shipment
     * @param shipmentId
     * @return Shipment
     * @throws ShipmentException
     */
    public Shipment getShipment(String shipmentId) throws ShipmentException;

}
