package org.example.service;

import org.example.model.Cart;
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

        if (purchaseQuantity >= 3) {
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



    public boolean synergyDiscount(List<Cart> cartItems) {

        boolean hasEnginePart = false;
        boolean hasElectricalPart = false;

        for (Cart cartItem : cartItems) {

            String category = cartItem
                    .getInventoryItem()
                    .getCategory();

            if (category.equalsIgnoreCase("Engine")) {
                hasEnginePart = true;
            }

            if (category.equalsIgnoreCase("Electrical")) {
                hasElectricalPart = true;
            }
        }

        return hasEnginePart && hasElectricalPart;
    }
    public double calculateCartTotal(List<Cart> cartItems) {

        double cartTotal = 0;

        for (Cart cartItem : cartItems) {
            cartTotal += cartItem.getDiscountedSubtotal();
        }

        return cartTotal;
    }
    public double applySynergyDiscount(
            List<Cart> cartItems,
            double cartTotal) {

        if (synergyDiscount(cartItems)) {
            return cartTotal * 0.90;
        }

        return cartTotal;
    }
}
