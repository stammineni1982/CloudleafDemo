package com.cloudleaf.demo.model;

import java.io.Serializable;
import java.util.Date;

public class ShipmentHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private Address address;
    private String comments;
    private Date created;

    public ShipmentHistory(String status, Address address, String comments, Date created) {
        this.status = status;
        this.address = address;
        this.comments = comments;
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ShipmentHistory{" +
                "status='" + status + '\'' +
                ", address=" + address +
                ", comments='" + comments + '\'' +
                ", created=" + created +
                '}';
    }
}
