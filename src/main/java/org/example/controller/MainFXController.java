package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Inventory;
import org.example.service.InventoryService;

import java.util.List;

public class MainFXController {

    @FXML
    private TableView<Inventory> inventoryTable;

    @FXML
    private TableColumn<Inventory, String> partCodeColumn;

    @FXML
    private TableColumn<Inventory, String> partNameColumn;

    @FXML
    private TableColumn<Inventory, String> brandColumn;

    @FXML
    private TableColumn<Inventory, String> categoryColumn;

    @FXML
    private TableColumn<Inventory, Double> priceColumn;

    @FXML
    private TableColumn<Inventory, Integer> quantityColumn;

    @FXML
    private TableColumn<Inventory, String> stockDateColumn;

    @FXML
    private TableColumn<Inventory, Integer> thresholdColumn;

    @FXML
    private Button addPartButton;

    @FXML
    private Button updatePartButton;

    @FXML
    private Button deletePartButton;
    @FXML private Label totalPartsLabel;

    @FXML private Label totalValueLabel;
    private final InventoryService inventoryService =
            new InventoryService(
                    "src/main/java/org/example/InputFiles/inventory_legacy.txt"
            );

    private ObservableList<Inventory> inventoryData;

    @FXML
    public void initialize() {

        configureTableColumns();

        loadInventoryData();
    }

    private void configureTableColumns() {

        partCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("partCode")
        );

        partNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("partName")
        );

        brandColumn.setCellValueFactory(
                new PropertyValueFactory<>("brand")
        );

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );

        quantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        stockDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("stockDate")
        );

        thresholdColumn.setCellValueFactory(
                new PropertyValueFactory<>(
                        "lowStockThreshold"
                )
        );
    }

    private void loadInventoryData() {

        List<Inventory> items =
                inventoryService.loadInventory();

        inventoryData =
                FXCollections.observableArrayList(items);

        inventoryTable.setItems(inventoryData);

        updateInventorySummary();
        System.out.println(
                "Inventory loaded into table: "
                        + inventoryData.size()
                        + " items"
        );

    }
    private void updateInventorySummary() {

        int totalParts = inventoryData.size();
        double totalValue = 0.0;
        for (Inventory item : inventoryData) {
            totalValue += item.getPrice() * item.getQuantity();
        }
        totalPartsLabel.setText( String.valueOf(totalParts) );
        totalValueLabel.setText( String.format("Rs.%.2f", totalValue) );
    }
}