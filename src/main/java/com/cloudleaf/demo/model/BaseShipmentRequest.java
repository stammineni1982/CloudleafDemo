package com.cloudleaf.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Model representing a create shipment request details
 */
@Component
@Validated
@ApiModel(description = "Model representing a create shipment request details.")
public class BaseShipmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message="Tracking Number can not be null")
    @JsonProperty("trackingNumber")
    @ApiModelProperty(notes = "Tracking Number. Eg: USPS: 9374889676091297266845, UPS:1Z445FY81320608602 or FedEx: 772015445935",
            example = "9374889676091297266845", required = true, position = 0)
    private String trackingNumber;

    @NotNull(message="Courier Code can not be null")
    @JsonProperty("courierCode")
    @ApiModelProperty(notes = "Courier Code. Only allow: FedEx, UPS, USPS.",
            example = "USPS", required = true, position = 1)
    private Courier courier;

    @NotNull(message="Origin can not be null")
    @JsonProperty("origin")
    @ApiModelProperty(notes = "Origin Address",
            example = "990 S Hwy 395 S, Hermiston, OR 97838", required = true, position = 2)
    private String originAddress;

    @NotNull(message="Destination can not be null")
    @JsonProperty("destination")
    @ApiModelProperty(notes = "Destination Address",
            example = "3505 Factoria Blvd SE, Bellevue, WA 9800", required = true, position = 3)
    private String destinationAddress;


    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public String toString() {
        return "BaseShipmentRequest{" +
                "originAddress='" + originAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", courier=" + courier +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}
