# Inventory & Point of Sale (POS) Management System

A JavaFX desktop application for managing inventory, tracking dealers, and processing sales transactions with audit logging.

---

## Features

* **Inventory Management:** View, add, update, and manage stock items.
* **Point of Sale (POS):** Process sales orders and manage a live shopping cart.
* **Dealer Management:** Track supplier/dealer details and legacy data.
* **Audit Trail:** Automatic system event logging via `AuditService` (`audit_log.txt`).
* **Graphical User Interface:** Interactive UI built with JavaFX (`Main.fxml`).

---

## Project Structure

```text
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── controller/
│   │   │           │   ├── DealerController.java
│   │   │           │   ├── InventoryController.java
│   │   │           │   ├── MainFXController.java
│   │   │           │   └── POSController.java
│   │   │           ├── InputFiles/
│   │   │           │   ├── dealers_legacy.txt
│   │   │           │   ├── inventory_legacy.txt
│   │   │           │   └── [image assets]
│   │   │           ├── model/
│   │   │           │   ├── Cart.java
│   │   │           │   ├── Dealer.java
│   │   │           │   └── Inventory.java
│   │   │           ├── repository/
│   │   │           │   ├── DealerFileRepository.java
│   │   │           │   └── InventoryFileRepository.java
│   │   │           ├── service/
│   │   │           │   ├── AuditService.java
│   │   │           │   ├── DealerService.java
│   │   │           │   ├── InventoryService.java
│   │   │           │   └── POSService.java
│   │   │           ├── InventoryApplication.java
│   │   │           └── Main.java
│   │   └── resources/
│   │       └── org/
│   │           └── example/
│   │               └── Main.fxml
│   └── test/
│       └── java/
│           ├── InventoryServiceTest.java
│           └── POSServiceTest.java
├── audit_log.txt
├── pom.xml
└── README.m












