package org.example.controller;

import org.example.model.Inventory;
import org.example.service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class InventoryController {

    private InventoryService inventoryService;
    private DealerController dealerController;
    private Scanner scanner;

    public InventoryController(
            InventoryService inventoryService,
            DealerController dealerController,
            Scanner scanner) {

        this.inventoryService = inventoryService;
        this.dealerController = dealerController;
        this.scanner = scanner;
    }

    public void start() {

        int choice;

        do {
            System.out.println("\n===== INVENTORY MENU =====");
            System.out.println("1. View Inventory");
            System.out.println("2. Search Inventory");
            System.out.println("3. Random Dealer Selection");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            if (choice == 1) {
                viewInventory();

            }
            else if (choice==2){
                searchInventory();
            }
            else if (choice==3){
                dealerController.displayRandomDealers();
            }
            else if (choice == 4) {
                System.out.println("Program closed.");}
                else {
                System.out.println("Invalid choice.");
            }

        } while (choice != 4);
    }

    private void viewInventory() {

        List<Inventory> items =
                inventoryService.loadInventory();

        if (items.isEmpty()) {
            System.out.println("No inventory found.");
            return;
        }

        String currentCategory = "";

        for (Inventory item : items) {

            if (!item.getCategory()
                    .equalsIgnoreCase(currentCategory)) {

                currentCategory = item.getCategory();

                System.out.println(
                        "\n--- " + currentCategory.toUpperCase() + " ---"
                );
            }

            System.out.println(item);
        }

        int totalQuantity =
                inventoryService.getTotalQuantity(items);

        double totalValue =
                inventoryService.getTotalValue(items);

        System.out.println("\nTotal Item Quantity: " + totalQuantity);
        System.out.printf(
                "Total Inventory Value: Rs. %.2f%n",
                totalValue
        );
    }
    private void searchInventory() {


        scanner.nextLine();

        System.out.print("Enter part name or press Enter to skip: ");
        String partName = scanner.nextLine().trim();

        System.out.print("Enter brand or press Enter to skip: ");
        String brand = scanner.nextLine().trim();

        System.out.print("Enter category or press Enter to skip: ");
        String category = scanner.nextLine().trim();

        List<Inventory> results =
                inventoryService.searchInventory(
                        partName,
                        brand,
                        category
                );

        if (results.isEmpty()) {

            System.out.println("No matching inventory found.");
            return;
        }

        System.out.println("\n===== SEARCH RESULTS =====");

        for (Inventory item : results) {
            System.out.println(item);
        }
    }
}