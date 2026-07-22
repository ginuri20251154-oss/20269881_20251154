package org.example.model;

public class Dealer {
    String dealerID;
    String dealerName;
    String dealerContact;
    String dealerLocation;
    public Dealer(String dealerID, String dealerName, String dealerContact, String dealerLocation){
        setDealerID(dealerID);
        setDealerName(dealerName);
        setDealerContact(dealerContact);
        setDealerLocation(dealerLocation);
    }

    public String getDealerID() {

        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerContact() {
        return dealerContact;
    }

    public void setDealerContact(String dealerContact) {
        this.dealerContact = dealerContact;
    }

    public String getDealerLocation() {
        return dealerLocation;
    }

    public void setDealerLocation(String dealerLocation) {
        this.dealerLocation = dealerLocation;
    }
}

