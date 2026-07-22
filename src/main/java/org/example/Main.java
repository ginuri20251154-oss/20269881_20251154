package org.example;

import org.example.controller.InventoryController;
import org.example.service.InventoryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        InventoryService inventoryService =
                new InventoryService("src/main/java/org/example/InputFiles/inventory_legacy.txt");

        Scanner scanner = new Scanner(System.in);

        InventoryController inventoryController =
                new InventoryController(
                        inventoryService,
                        scanner
                );

        inventoryController.start();

        scanner.close();
    }
}

