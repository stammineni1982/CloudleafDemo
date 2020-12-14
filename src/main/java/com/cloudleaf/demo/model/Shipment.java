package com.cloudleaf.demo.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Component
public class Shipment extends BaseShipmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String currentStatus;
    private Date created;
    private Date updated;
    private String comments;
    private List<ShipmentHistory> history;

    public void setBaseShipmentRequest(BaseShipmentRequest baseShipmentRequest){
        this.setCourier(baseShipmentRequest.getCourier());
        this.setDestinationAddress(baseShipmentRequest.getDestinationAddress());
        this.setOriginAddress(baseShipmentRequest.getOriginAddress());
        this.setTrackingNumber(baseShipmentRequest.getTrackingNumber());
        this.setCourier(baseShipmentRequest.getCourier());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<ShipmentHistory> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id='" + id + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", comments='" + comments + '\'' +
                ", history=" + history +
                "} " + super.toString();
    }

    public void setHistory(List<ShipmentHistory> history) {
        this.history = history;
    }


}
