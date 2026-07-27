package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
    @FXML
    private void handleAddPart() {

        Dialog<ButtonType> dialog = new Dialog<>();

        dialog.setTitle("Add Part");
        dialog.setHeaderText("Enter the new part details");

        ButtonType addButtonType =
                new ButtonType(
                        "Add",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        addButtonType,
                        ButtonType.CANCEL
                );

        TextField partCodeField = new TextField();
        TextField partNameField = new TextField();
        TextField brandField = new TextField();
        TextField categoryField = new TextField();
        TextField priceField = new TextField();
        TextField quantityField = new TextField();
        TextField thresholdField = new TextField();
        DatePicker stockDatePicker = new DatePicker();

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Part Code:"), 0, 0);
        grid.add(partCodeField, 1, 0);

        grid.add(new Label("Part Name:"), 0, 1);
        grid.add(partNameField, 1, 1);

        grid.add(new Label("Brand:"), 0, 2);
        grid.add(brandField, 1, 2);

        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryField, 1, 3);

        grid.add(new Label("Price:"), 0, 4);
        grid.add(priceField, 1, 4);

        grid.add(new Label("Quantity:"), 0, 5);
        grid.add(quantityField, 1, 5);

        grid.add(new Label("Stock Date:"), 0, 6);
        grid.add(stockDatePicker, 1, 6);

        grid.add(new Label("Low Stock Threshold:"), 0, 7);
        grid.add(thresholdField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(buttonType -> {


            if (buttonType == addButtonType) {

                String partCode = partCodeField.getText().trim();
                String partName = partNameField.getText().trim();
                String brand = brandField.getText().trim();
                String category = categoryField.getText().trim();
                String priceText = priceField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String thresholdText = thresholdField.getText().trim();

                if (partCode.isEmpty()
                        || partName.isEmpty()
                        || brand.isEmpty()
                        || category.isEmpty()
                        || priceText.isEmpty()
                        || quantityText.isEmpty()
                        || thresholdText.isEmpty()
                        || stockDatePicker.getValue() == null) {

                    showError("Please complete all fields.");
                    return;
                }

                try {

                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    int threshold = Integer.parseInt(thresholdText);

                    if (price < 0 || quantity < 0 || threshold < 0) {

                        showError(
                                "Price, quantity, and threshold cannot be negative."
                        );

                        return;
                    }

                    String stockDate =
                            stockDatePicker.getValue().toString();

                    Inventory newPart = new Inventory(
                            partCode,
                            partName,
                            brand,
                            price,
                            quantity,
                            category,
                            stockDate,
                            "",
                            threshold
                    );

                    inventoryData.add(newPart);

                    inventoryTable.refresh();

                    updateInventorySummary();

                    showSuccess("Part added successfully.");

                } catch (NumberFormatException e) {

                    showError(
                            "Price must be a valid number. "
                                    + "Quantity and threshold must be whole numbers."
                    );
                }
            }
        });
    }
    private void showError(String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccess(String message) {

        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}