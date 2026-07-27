package org.example.controller;

import org.example.model.Cart;
import org.example.model.Inventory;
import org.example.service.AuditService;
import org.example.service.POSService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class POSController {
    private final POSService posService;
    private final AuditService auditService;
    private final Scanner scanner;

    public POSController(
            POSService posService,
            AuditService auditService,
            Scanner scanner) {

        this.posService = posService;
        this.auditService=auditService;
        this.scanner = scanner;

    }

    public void processSale(List<Inventory> inventoryItems) {

        List<Cart> cartItems = new ArrayList<>();

        System.out.println("\n===== POINT OF SALE =====");

        boolean continueShopping = true;

        while (continueShopping) {

            System.out.print("Enter part code: ");
            String partCode = scanner.nextLine().trim();

            Inventory selectedItem =
                    posService.findItem(
                            inventoryItems,
                            partCode
                    );

            if (selectedItem == null) {
                System.out.println("Part not found.");
                continue;
            }

            System.out.println(
                    "Selected item: "
                            + selectedItem.getPartName()
            );

            System.out.println(
                    "Category: "
                            + selectedItem.getCategory()
            );

            System.out.println(
                    "Available quantity: "
                            + selectedItem.getQuantity()
            );

            int purchaseQuantity;

            try {
                System.out.print("Enter purchase quantity: ");

                purchaseQuantity = Integer.parseInt(
                        scanner.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid whole number."
                );

                continue;
            }

            if (purchaseQuantity <= 0) {

                System.out.println(
                        "Purchase quantity must be greater than zero."
                );

                continue;
            }

            int quantityAlreadyInCart =
                    getQuantityAlreadyInCart(
                            cartItems,
                            selectedItem.getPartCode()
                    );

            int totalRequestedQuantity =
                    quantityAlreadyInCart + purchaseQuantity;

            boolean enoughStock =
                    posService.hasEnoughStock(
                            selectedItem,
                            totalRequestedQuantity
                    );

            if (!enoughStock) {

                System.out.println(
                        "Insufficient stock. You already have "
                                + quantityAlreadyInCart
                                + " unit(s) of this item in the cart."
                );

                continue;
            }

            Cart existingCartItem =
                    findCartItem(
                            cartItems,
                            selectedItem.getPartCode()
                    );

            if (existingCartItem != null) {

                existingCartItem.setPurchaseQuantity(
                        totalRequestedQuantity
                );

                double updatedSubtotal =
                        posService.calculateTotal(
                                selectedItem,
                                totalRequestedQuantity
                        );

                existingCartItem.setDiscountedSubtotal(
                        updatedSubtotal
                );

                System.out.println(
                        "Cart quantity updated successfully."
                );

            } else {


                double discountedSubtotal =
                        posService.calculateTotal(
                                selectedItem,
                                purchaseQuantity
                        );

                Cart newCartItem = new Cart(
                        selectedItem,
                        purchaseQuantity,
                        discountedSubtotal
                );

                cartItems.add(newCartItem);

                System.out.println(
                        "Item added to cart successfully."
                );
            }

            System.out.print(
                    "Would you like to add another item? Yes/No: "
            );

            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("No")
                    || answer.equalsIgnoreCase("N")) {

                continueShopping = false;
            }
        }

        if (cartItems.isEmpty()) {

            System.out.println(
                    "No items were added to the cart."
            );

            return;
        }

        displayCart(cartItems);


        double cartTotal =
                posService.calculateCartTotal(
                        cartItems
                );


        double finalTotal =
                posService.applySynergyDiscount(
                        cartItems,
                        cartTotal
                );

        boolean synergyDiscountApplied =
                posService.synergyDiscount(
                        cartItems
                );

        System.out.printf(
                "%nCart total after item discounts: Rs.%.2f%n",
                cartTotal
        );

        if (synergyDiscountApplied) {

            double synergyDiscountAmount =
                    cartTotal - finalTotal;

            System.out.printf(
                    "Synergy discount (10%%): -Rs.%.2f%n",
                    synergyDiscountAmount
            );

        } else {

            System.out.println(
                    "Synergy discount: Not applicable"
            );
        }

        System.out.printf(
                "Final checkout total: Rs.%.2f%n",
                finalTotal
        );

        System.out.print(
                "Confirm checkout? Yes/No: "
        );

        String confirmation =
                scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Yes")
                && !confirmation.equalsIgnoreCase("Y")) {

            System.out.println(
                    "Checkout cancelled. Stock was not updated."
            );

            return;
        }

        for (Cart cartItem : cartItems) {

            posService.updateStock(
                    cartItem.getInventoryItem(),
                    cartItem.getPurchaseQuantity()
            );
        }
        auditService.writeLog(
                "SALE",
                "Sale completed. Final total: $"
                        + String.format("%.2f", finalTotal)
        );

        System.out.println(
                "Sale completed successfully."
        );

        System.out.printf(
                "Amount paid: Rs.%.2f%n",
                finalTotal
        );
    }
    private void displayCart(List<Cart> cartItems) {

        System.out.println("\n===== CART SUMMARY =====");

        for (Cart cartItem : cartItems) {

            Inventory item =
                    cartItem.getInventoryItem();

            double originalSubtotal =
                    item.getPrice()
                            * cartItem.getPurchaseQuantity();

            double bulkDiscountAmount =
                    originalSubtotal
                            - cartItem.getDiscountedSubtotal();

            System.out.println(
                    "Part Code: "
                            + item.getPartCode()
            );

            System.out.println(
                    "Part Name: "
                            + item.getPartName()
            );

            System.out.println(
                    "Category: "
                            + item.getCategory()
            );

            System.out.println(
                    "Quantity: "
                            + cartItem.getPurchaseQuantity()
            );

            System.out.printf(
                    "Original subtotal: Rs.%.2f%n",
                    originalSubtotal
            );

            if (bulkDiscountAmount > 0) {

                System.out.printf(
                        "Bulk discount (5%%): -Rs.%.2f%n",
                        bulkDiscountAmount
                );

            } else {

                System.out.println(
                        "Bulk discount: Not applicable"
                );
            }

            System.out.printf(
                    "Item total: Rs.%.2f%n",
                    cartItem.getDiscountedSubtotal()
            );

            System.out.println(
                    "--------------------------------"
            );
        }
    }

    private int getQuantityAlreadyInCart(
            List<Cart> cartItems,
            String partCode) {

        for (Cart cartItem : cartItems) {

            String cartPartCode =
                    cartItem.getInventoryItem()
                            .getPartCode();

            if (cartPartCode.equalsIgnoreCase(partCode)) {
                return cartItem.getPurchaseQuantity();
            }
        }

        return 0;
    }

    private Cart findCartItem(
            List<Cart> cartItems,
            String partCode) {

        for (Cart cartItem : cartItems) {

            String cartPartCode =
                    cartItem.getInventoryItem()
                            .getPartCode();

            if (cartPartCode.equalsIgnoreCase(partCode)) {
                return cartItem;
            }
        }

        return null;
    }


}
