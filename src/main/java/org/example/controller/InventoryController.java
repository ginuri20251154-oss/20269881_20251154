package org.example.controller;

import org.example.model.Inventory;
import org.example.service.InventoryService;

import java.util.List;
import java.util.Scanner;

public class InventoryController {

    private InventoryService inventoryService;
    private DealerController dealerController;
    private POSController posController ;
    private Scanner scanner;

    public InventoryController(
            InventoryService inventoryService,
            DealerController dealerController,
            POSController posController,
            Scanner scanner) {

        this.inventoryService = inventoryService;
        this.dealerController = dealerController;
        this.posController = posController;
        this.scanner = scanner;
    }

    public void start() {

        int choice;
        List<Inventory> items = inventoryService.loadInventory();


        do {
            System.out.println("\n===== INVENTORY MENU =====");
            System.out.println("1. View Inventory");
            System.out.println("2. Search Inventory");
            System.out.println("3. Low Stock Monitoring");
            System.out.println("4. Random Dealer Selection");
            System.out.println("5. Point of Sales");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");


            choice = scanner.nextInt();


            scanner.nextLine();
            if (choice == 1) {
                viewInventory(items);

            }
            else if (choice==2){
                searchInventory(items);
            }
            else if (choice==3){
                displayLowStockItems(items);
            }
            else if (choice==4){
                dealerController.displayRandomDealers();
            }
            else if (choice==5){
                posController.processSale(items);
            }
            else if (choice == 6) {
                System.out.println("Program closed.");}
                else {
                System.out.println("Invalid choice.");
            }

        } while (choice != 6);
    }

    private void viewInventory(List<Inventory> items) {

//        List<Inventory> items =
//                inventoryService.loadInventory();

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
    private void searchInventory(List<Inventory> items) {


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
    private void displayLowStockItems(List<Inventory> items) {

        List<Inventory> lowStockItems =
                inventoryService.getLowStockItems(items);

        if (lowStockItems.isEmpty()) {
            System.out.println("No low stock items found.");
            return;
        }

        System.out.println("\n===== LOW STOCK ITEMS =====");

        for (Inventory item : lowStockItems) {
            System.out.println(item);
        }
    }
    public String addPart(
            List<Inventory> items,
            String partCode,
            String partName,
            String brand,
            double price,
            int quantity,
            String category,
            String stockDate,
            String imageName,
            int lowStockThreshold) {

        if (partCode == null || partCode.trim().isEmpty()) {
            return "Part code is required.";
        }

        if (partName == null || partName.trim().isEmpty()) {
            return "Part name is required.";
        }

        if (brand == null || brand.trim().isEmpty()) {
            return "Brand is required.";
        }

        if (price < 0) {
            return "Price cannot be negative.";
        }

        if (quantity < 0) {
            return "Quantity cannot be negative.";
        }

        if (category == null || category.trim().isEmpty()) {
            return "Category is required.";
        }

        if (lowStockThreshold < 0) {
            return "Low-stock threshold cannot be negative.";
        }

        if (stockDate == null || stockDate.trim().isEmpty()) {
            return "Stock date is required.";
        }

        if (imageName == null || imageName.trim().isEmpty()) {
            imageName = "No Image";
        }

        Inventory newItem = new Inventory(
                partCode.trim(),
                partName.trim(),
                brand.trim(),
                price,
                quantity,
                category.trim(),
                stockDate.trim(),
                imageName.trim(),
                lowStockThreshold
        );

        boolean added = inventoryService.addPart(items, newItem);

        if (added) {
            return "Part added successfully.";
        }

        return "A part with this part code already exists.";
    }
    public String deletePart( List<Inventory> items, String partCode) {

        if (partCode == null || partCode.trim().isEmpty()) {
            return "Part code is required.";
        }
        boolean deleted = inventoryService.deletePart( items, partCode.trim() );
        if (deleted) {
            return "Part deleted successfully.";
        }
        return "Part not found.";
    }
    public String updatePart(
            List<Inventory> items,
            String partCode,
            String partName,
            String brand,
            double price,
            int quantity,
            String category,
            String stockDate,
            String imageName,
            int lowStockThreshold) {

        if (partCode == null || partCode.trim().isEmpty()) {
            return "Part code is required.";
        }

        if (partName == null || partName.trim().isEmpty()) {
            return "Part name is required.";
        }

        if (brand == null || brand.trim().isEmpty()) {
            return "Brand is required.";
        }

        if (price < 0) {
            return "Price cannot be negative.";
        }

        if (quantity < 0) {
            return "Quantity cannot be negative.";
        }

        if (category == null || category.trim().isEmpty()) {
            return "Category is required.";
        }

        if (stockDate == null || stockDate.trim().isEmpty()) {
            return "Stock date is required.";
        }

        if (lowStockThreshold < 0) {
            return "Low-stock threshold cannot be negative.";
        }

        if (imageName == null || imageName.trim().isEmpty()) {
            imageName = "No Image";
        }

        boolean updated = inventoryService.updatePart(
                items,
                partCode.trim(),
                partName.trim(),
                brand.trim(),
                price,
                quantity,
                category.trim(),
                stockDate.trim(),
                imageName.trim(),
                lowStockThreshold
        );

        if (updated) {
            return "Part updated successfully.";
        }

        return "Part not found.";
    }
}