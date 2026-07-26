package org.example.controller;

import org.example.model.Inventory;
import org.example.service.POSService;

import java.util.List;
import java.util.Scanner;

public class POSController {
    private POSService pointOfSaleService;
    private Scanner scanner;

    public POSController(
            POSService pointOfSaleService,
            Scanner scanner) {

        this.pointOfSaleService = pointOfSaleService;
        this.scanner = scanner;
    }

    public void processSale(List<Inventory> items) {

        System.out.print("Enter part code: ");
        String partCode = scanner.nextLine().trim();

        Inventory item =
                pointOfSaleService.findItem(items, partCode);

        if (item == null) {
            System.out.println("Part not found.");
            return;
        }

        System.out.println("Part: " + item.getPartName());
        System.out.println("Price: Rs. " + item.getPrice());
        System.out.println("Available quantity: " + item.getQuantity());

        System.out.print("Enter purchase quantity: ");
        int purchaseQuantity = scanner.nextInt();
        scanner.nextLine();

        if (!pointOfSaleService.hasEnoughStock(
                item,
                purchaseQuantity)) {

            System.out.println("Invalid quantity or insufficient stock.");
            return;
        }

        double total =
                pointOfSaleService.calculateTotal(
                        item,
                        purchaseQuantity
                );

        pointOfSaleService.updateStock(
                item,
                purchaseQuantity
        );

        System.out.printf("Total amount: Rs. %.2f%n", total);
        System.out.println(
                "Remaining quantity: " + item.getQuantity()
        );
    }
}
