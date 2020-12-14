package com.cloudleaf.demo.api;


import com.cloudleaf.demo.model.BaseShipmentRequest;
import com.cloudleaf.demo.model.Shipment;
import com.cloudleaf.demo.model.ShipmentResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public interface ShipmentApi {

    /**
     * API to create shipment
     *
     * @param shipment
     * @return Shipment
     */
    @RequestMapping(path = "/shipment", produces = "application/json", method = RequestMethod.POST)
    public ShipmentResponse createShipment(@Valid @RequestBody BaseShipmentRequest baseShipment);

    /**
     * API to get shipment details with latest shipment status and tracking history.
     *
     * @param id
     * @return Shipment
     */
    @RequestMapping(path = "/shipment/{shipmentId}", produces = "application/json", method = RequestMethod.GET)
    public ShipmentResponse getShipment(@Valid @PathVariable String shipmentId) ;


}
