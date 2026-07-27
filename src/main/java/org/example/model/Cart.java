package org.example.model;

public class Cart {
    private Inventory inventory;
    private int purchaseQuantity;
    private double discountedSubtotal;

    public Cart(
            Inventory inventory,
            int purchaseQuantity,
            double discountedSubtotal) {

        this.inventory = inventory;
        this.purchaseQuantity = purchaseQuantity;
        this.discountedSubtotal = discountedSubtotal;
    }

    public Inventory getInventoryItem() {
        return inventory;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public double getDiscountedSubtotal() {
        return discountedSubtotal;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public void setDiscountedSubtotal(double discountedSubtotal) {
        this.discountedSubtotal = discountedSubtotal;
    }
}
