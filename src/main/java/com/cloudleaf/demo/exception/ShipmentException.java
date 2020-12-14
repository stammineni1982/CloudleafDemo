package com.cloudleaf.demo.exception;

/**
 * ShipmentException used with custom exception codes
 */
public class ShipmentException extends Exception {
    private Integer errorCode;

    public ShipmentException(int errorCode, String msg){
        super(msg);
        this.errorCode=errorCode;
    }

    public ShipmentException(int errorCode, String msg, Exception e){
        super(msg,e);
        this.errorCode=errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
