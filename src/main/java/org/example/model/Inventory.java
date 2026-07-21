package org.example.model;

import java.time.LocalDate;

public class Inventory {
    String itemCode;
    String partName;
    String brandName;
    Double price;
    int quantity;
    String category;
    LocalDate lastUpdated;
    String imageName;
    public Inventory( String itemCode, String partName, String brandName, Double price, int quantity, String category, LocalDate lastUpdate, String imageName) {

        setItemCode(itemCode);
        setPartName(partName);
        setBrandName(brandName);
        setPrice(price);
        setQuantity(quantity);
        setCategory(category);
        setLastUpdated(lastUpdated);
        setImageName(imageName);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
