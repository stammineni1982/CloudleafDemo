package com.cloudleaf.demo.service;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.model.checkpoint.Checkpoint;
import com.aftership.sdk.model.tracking.NewTracking;
import com.aftership.sdk.model.tracking.Tracking;
import com.cloudleaf.demo.exception.ShipmentException;
import com.cloudleaf.demo.model.Address;
import com.cloudleaf.demo.model.Courier;
import com.cloudleaf.demo.model.Shipment;
import com.cloudleaf.demo.model.ShipmentHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service("afterShipShipmentService")
public class AfterShipShipmentServiceImpl implements ShipmentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AfterShip afterShip;

    @Override
    public Shipment createShipment(Shipment shipment) throws ShipmentException {
        try {
            logger.info("createShipment start {}", shipment);
            //-- Validate shipment
            validateShipment(shipment);

            // -- Use AfterShip SDK API to generate new tracking and persists
            NewTracking newTracking = getNewTrackingFromShipment(shipment);
            logger.debug("createShipment newTracking {}", newTracking);
            Tracking tracking = afterShip.getTrackingEndpoint().createTracking(newTracking);
            shipment = getShipmentFromTracking(tracking);
            logger.info("shipment :: {}", shipment);
        } catch (RequestException e) {
            logger.error("RequestException while createShipment :: {}", e.getMessage());
            throw new ShipmentException(501, String.format("RequestException while createShipment :: %s", e.getMessage()));
        } catch (ApiException e) {
            logger.error("ApiException while createShipment :: {}", e.getMessage());
            if (e !=null && e.getCode() !=null && e.getCode().intValue() == 4003)
                throw new ShipmentException(401, String.format("ApiException while createShipment :: %s %s", shipment.getTrackingNumber(), " Already exists."));
            else
                throw new ShipmentException(501, String.format("ApiException while createShipment :: %s", e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception while createShipment :: {}", e.getMessage());
            throw new ShipmentException(501, String.format("Exception while createShipment :: %s", e.getMessage()));
        }
        return shipment;
    }

    @Override
    public Shipment getShipment(String shipmentId) throws ShipmentException {
        Shipment shipment = null;
        try {
            logger.info("getShipment :: start {}", shipmentId);

            Tracking tracking = afterShip.getTrackingEndpoint().getTracking(shipmentId, null);
            logger.info("getShipment afterShip :: tracking {}", tracking);

            shipment = getShipmentFromTracking(tracking);

            logger.info("getShipment :: shipment {}", shipment);

        } catch (RequestException e) {
            logger.error("RequestException while getShipment :: {}", e.getMessage());
            throw new ShipmentException(501, String.format("Exception while getShipment :: %s", e.getMessage()));
        } catch (ApiException e) {
            logger.error("ApiException while getShipment :: {}", e.getMessage());
            throw new ShipmentException(404, String.format("ApiException while getShipment :: %s", e.getMessage()));
        } catch (Exception e) {
            logger.error("Exception while getShipment :: {}", e.getMessage());
            throw new ShipmentException(501, String.format("Exception while getShipment :: %s", e.getMessage()));
        }
        return shipment;
    }

    /**
     * private API to generate NewTracking object from shipment request
     *
     * @param shipment
     * @return NewTracking
     */
    private NewTracking getNewTrackingFromShipment(Shipment shipment) throws ShipmentException {
        NewTracking newTracking = new NewTracking();
        newTracking.setTrackingNumber(shipment.getTrackingNumber());
        newTracking.setSlug(shipment.getCourier().getCode().split(","));

        //-- Custom fields
        HashMap<String, String> map = new HashMap<>();
        map.put("origin_address", shipment.getOriginAddress());
        map.put("destination_address", shipment.getDestinationAddress());
        newTracking.setCustomFields(map);

        return newTracking;
    }

    /**
     * API to get shipment from Tracking object from AfterShip API
     *
     * @param tracking
     * @return
     */
    private Shipment getShipmentFromTracking(Tracking tracking) {
        Shipment shipment = new Shipment();
        shipment.setId(tracking.getId());
        shipment.setCourier(Courier.getCourier(tracking.getSlug()));
        shipment.setTrackingNumber(tracking.getTrackingNumber());
        shipment.setOriginAddress(tracking.getCustomFields().get("origin_address"));
        shipment.setDestinationAddress(tracking.getCustomFields().get("destination_address"));
        shipment.setCurrentStatus(tracking.getTag());
        shipment.setCreated(tracking.getCreatedAt());

        List<ShipmentHistory> list = new LinkedList<>();
        //-- Loop to find latest status and audit history
        for (Checkpoint checkpoint : tracking.getCheckpoints()) {
            list.add(new ShipmentHistory(
                    checkpoint.getTag()
                    , new Address(checkpoint.getLocation(), checkpoint.getCity(), checkpoint.getState(), checkpoint.getCountryName()
                    , checkpoint.getZip())
                    , checkpoint.getMessage()
                    , checkpoint.getCreatedAt()
            ));

            //-- Assume checkpoints are order update latest tag and date
            shipment.setCurrentStatus(checkpoint.getTag());
            shipment.setUpdated(checkpoint.getCreatedAt());
            shipment.setComments(checkpoint.getMessage());
        }
        ;

        shipment.setHistory(list);

        return shipment;
    }

    /**
     * Private function to validate input shipment data
     *
     * @param shipment
     * @throws ShipmentException
     */
    private void validateShipment(Shipment shipment) throws ShipmentException {
        if (shipment == null)
            throw new ShipmentException(400, "Invalid input. Object is null");
        if (shipment.getTrackingNumber() == null)
            throw new ShipmentException(400, "Invalid input. Tracking Number value is null.");
        if (shipment.getCourier() == null)
            throw new ShipmentException(400, "Invalid input. Courier Code  value is null.");
        if (shipment.getDestinationAddress() == null)
            throw new ShipmentException(400, "Invalid input. Destination value is null.");
        if (shipment.getOriginAddress() == null)
            throw new ShipmentException(400, "Invalid input. Origin value is null.");
    }
}
