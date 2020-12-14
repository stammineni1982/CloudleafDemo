package com.cloudleaf.demo.api;

import com.cloudleaf.demo.exception.ShipmentException;
import com.cloudleaf.demo.model.BaseShipmentRequest;
import com.cloudleaf.demo.model.Shipment;
import com.cloudleaf.demo.model.ShipmentResponse;
import com.cloudleaf.demo.service.ShipmentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Endpoints for Creating, Retrieving of Shipments.", tags = {"1. Shipment-API"})
@RestController
public class ShipmentController implements ShipmentApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("afterShipShipmentService")
    private ShipmentService afterShipShipmentService;

    @ApiOperation(value = "Create Shipment", notes = "API to create shipment by passing BaseShipmentRequest", tags = {"1. Shipment-API"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ShipmentResponse.class),
            @ApiResponse(code = 201, message = "Shipment created", response = ShipmentResponse.class),
            @ApiResponse(code = 400, message = "Invalid input", response = ShipmentResponse.class),
            @ApiResponse(code = 401, message = "Shipment already exists", response = ShipmentResponse.class),
            @ApiResponse(code = 501, message = "Shipment create internal error", response = ShipmentResponse.class) })
    @Override
    public ShipmentResponse createShipment(@ApiParam("Create new Shipment") BaseShipmentRequest baseShipment) {
        ShipmentResponse response = new ShipmentResponse();
        Shipment shipment = new Shipment();
        try {
            logger.info("createShipment :: baseShipment :: {}", baseShipment);

            //-- Service all to create Shipment
            shipment.setBaseShipmentRequest(baseShipment);
            shipment = afterShipShipmentService.createShipment(shipment);
            response.setShipment(shipment);

            logger.info("createShipment :: Shipment :: {}", shipment);

        } catch (ShipmentException se) {
            response.setStatus(se.getErrorCode());
            response.setMessage(se.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "Get Shipment by shipment Id", notes = "API to get shipment by passing Id", tags = {"1. Shipment-API"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ShipmentResponse.class),
            @ApiResponse(code = 400, message = "Invalid input", response = ShipmentResponse.class),
            @ApiResponse(code = 501, message = "Shipment get internal error", response = ShipmentResponse.class) })
    @Override
    public ShipmentResponse getShipment(@ApiParam("Id of the shipment to find. Cannot be empty") String shipmentId) {
        ShipmentResponse response = new ShipmentResponse();
        try {
            Shipment shipment = afterShipShipmentService.getShipment(shipmentId);
            response.setShipment(shipment);
        } catch (ShipmentException se) {
            response.setStatus(se.getErrorCode());
            response.setMessage(se.getMessage());
        }
        return response;
    }

}
