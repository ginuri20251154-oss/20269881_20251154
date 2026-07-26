package org.example.service;

import org.example.model.Inventory;

import java.util.List;

public class POSService {
    public Inventory findItem(
            List<Inventory> items,
            String partCode) {

        for (Inventory item : items) {

            if (item.getPartCode()
                    .equalsIgnoreCase(partCode)) {

                return item;
            }
        }

        return null;
    }

    public boolean hasEnoughStock(
            Inventory item,
            int purchaseQuantity) {

        return purchaseQuantity > 0
                && purchaseQuantity <= item.getQuantity();
    }

    public double calculateTotal(
            Inventory item,
            int purchaseQuantity) {

        double total =
                item.getPrice() * purchaseQuantity;

        if (purchaseQuantity >= 5) {
            total = total - (total * 0.05);
        }

        return total;
    }

    public void updateStock(
            Inventory item,
            int purchaseQuantity) {

        int newQuantity =
                item.getQuantity() - purchaseQuantity;

        item.setQuantity(newQuantity);
    }
}
