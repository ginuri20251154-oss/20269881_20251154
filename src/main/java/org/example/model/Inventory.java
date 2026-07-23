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

    public Inventory(
            String partCode,
            String partName,
            String brand,
            double price,
            int quantity,
            String category,
            String stockDate,
            String imageName) {

        this.partCode = partCode;
        this.partName = partName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.stockDate = stockDate;
        this.imageName = imageName;
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
}