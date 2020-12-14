package com.cloudleaf.demo.model;

public enum Courier {
    FedEx("fedex"),
    UPS("ups"),
    USPS("usps");

    private String code;

    private Courier(String code){
        this.code=code;
    }

    public String getCode(){
        return this.code;
    }

    public static Courier getCourier(String code){
        for(Courier courier : Courier.values()){
            if(courier.getCode().equals(code))
                return courier;
        }
        throw new UnsupportedOperationException(String.format("In valid code :: %s",code));
     }

}
