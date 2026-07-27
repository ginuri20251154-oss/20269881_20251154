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

    public String getDealerCode() {

        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return dealerCode + " | "
                + dealerName + " | "
                + phoneNumber + " | "
                + location;
    }
}

