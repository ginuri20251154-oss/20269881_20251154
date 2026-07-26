package org.example.model;

import java.math.BigDecimal;

public class Inventory{


    private String partCode;
    private String partName;
    private String brand;
    private double price;
    private int quantity;

    private String category;
    private String stockDate;
    private String imageName;
    private int threshold;


    public Inventory(
            String partCode,
            String partName,
            String brand,
            double price,
            int quantity,

            String category,
            String stockDate,
            String imageName,
            int threshold) {

        this.partCode = partCode;
        this.partName = partName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.stockDate = stockDate;
        this.imageName = imageName;
        this.threshold=threshold;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }
    public String getPartCode() {
        return partCode;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public double getItemValue() {
        return price * quantity;
    }
    public String getPartName() { return partName; }

    public String getBrand() { return brand; }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return partCode + " | "
                + partName + " | "
                + brand + " | Rs. "
                + price + " | Quantity: "
                + quantity + " | "
                + stockDate + " | "
                + imageName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}