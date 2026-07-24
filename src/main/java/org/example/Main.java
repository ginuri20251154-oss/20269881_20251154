package org.example;

import org.example.controller.DealerController;
import org.example.controller.InventoryController;
import org.example.service.DealerService;
import org.example.service.InventoryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InventoryService inventoryService =
                new InventoryService("src/main/java/org/example/InputFiles/inventory_legacy.txt");
        DealerService dealerService =
                new DealerService(
                        "src/main/java/org/example/InputFiles/dealers_legacy.txt"
                );

        DealerController dealerController = new DealerController(dealerService);
        Scanner scanner = new Scanner(System.in);


        InventoryController inventoryController =
                new InventoryController(
                        inventoryService,
                        dealerController,
                        scanner
                );

        inventoryController.start();


        dealerController.displayRandomDealers();
        scanner.close();

    }
}

