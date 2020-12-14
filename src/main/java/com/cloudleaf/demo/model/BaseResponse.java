package com.cloudleaf.demo.model;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
