package org.example.model;

public class Dealer {
    private String dealerCode;
    private String dealerName;
    private String phoneNumber;
    private String location;


    public Dealer(
            String dealerCode,
            String dealerName,
            String phoneNumber,
            String location
            ) {

        this.dealerCode = dealerCode;
        this.dealerName = dealerName;
        this.phoneNumber = phoneNumber;
        this.location = location;

    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return dealerCode + " | "
                + dealerName + " | "
                + phoneNumber + " | "
                + location;
    }
}

