package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import org.example.model.Dealer;
import org.example.model.Inventory;
import org.example.service.DealerService;
import org.example.service.InventoryService;

import java.util.List;

public class MainFXController {
    @FXML
    private TableView<Dealer> dealerTable;

    @FXML
    private TableColumn<Dealer, String> dealerNameColumn;

    @FXML
    private TableColumn<Dealer, String> dealerLocationColumn;

    @FXML
    private TableColumn<Dealer, String> dealerPhoneColumn;
    @FXML
    private TableView<Inventory> lowStockTable;

    @FXML
    private TableColumn<Inventory, String> lowStockPartCodeColumn;

    @FXML
    private TableColumn<Inventory, String> lowStockPartNameColumn;

    @FXML
    private TableColumn<Inventory, String> lowStockBrandColumn;

    @FXML
    private TableColumn<Inventory, String> lowStockCategoryColumn;

    @FXML
    private TableColumn<Inventory, Integer> lowStockQuantityColumn;

    @FXML
    private TableColumn<Inventory, Integer> lowStockThresholdColumn;
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
    @FXML
    private TextField searchPartCodeField;

    @FXML
    private TextField searchPartNameField;

    @FXML
    private TextField searchBrandField;

    @FXML
    private TextField searchCategoryField;

    @FXML
    private TextField searchMinPriceField;

    @FXML
    private TextField searchMaxPriceField;

    private final InventoryService inventoryService =
            new InventoryService(
                    "src/main/java/org/example/InputFiles/inventory_legacy.txt"
            );

    private final DealerService dealerService =
            new DealerService(
                    "src/main/java/org/example/InputFiles/dealers_legacy.txt"
            );

    @FXML
    private TableView<Inventory> searchResultsTable;
    @FXML
    private TableColumn<Inventory, String> searchPartCodeColumn;

    @FXML
    private TableColumn<Inventory, String> searchPartNameColumn;

    @FXML
    private TableColumn<Inventory, String> searchBrandColumn;

    @FXML
    private TableColumn<Inventory, String> searchCategoryColumn;

    @FXML
    private TableColumn<Inventory, Double> searchPriceColumn;

    @FXML
    private TableColumn<Inventory, Integer> searchQuantityColumn;
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
        searchPartCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("partCode")
        );

        searchPartNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("partName")
        );

        searchBrandColumn.setCellValueFactory(
                new PropertyValueFactory<>("brand")
        );

        searchCategoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        searchPriceColumn.setCellValueFactory(
                new PropertyValueFactory<>("price")
        );

        searchQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );
        lowStockPartCodeColumn.setCellValueFactory(
                new PropertyValueFactory<>("partCode")
        );

        lowStockPartNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("partName")
        );

        lowStockBrandColumn.setCellValueFactory(
                new PropertyValueFactory<>("brand")
        );

        lowStockCategoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category")
        );

        lowStockQuantityColumn.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        lowStockThresholdColumn.setCellValueFactory(
                new PropertyValueFactory<>("lowStockThreshold")
        );
        dealerNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("dealerName")
        );

        dealerLocationColumn.setCellValueFactory(
                new PropertyValueFactory<>("location")
        );

        dealerPhoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber")
        );
    }

    private void loadInventoryData() {

        List<Inventory> items =
                inventoryService.loadInventory();

        inventoryData =
                FXCollections.observableArrayList(items);

        inventoryTable.setItems(inventoryData);
        searchResultsTable.setItems(inventoryData);
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
    @FXML
    private void handleDeletePart() {

        Inventory selectedPart =
                inventoryTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedPart == null) {
            showError(
                    "Please select a part from the table."
            );
            return;
        }

        Alert confirmationAlert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmationAlert.setTitle("Delete Part");
        confirmationAlert.setHeaderText(
                "Confirm Part Deletion"
        );

        confirmationAlert.setContentText(
                "Are you sure you want to delete part "
                        + selectedPart.getPartCode()
                        + " - "
                        + selectedPart.getPartName()
                        + "?"
        );

        confirmationAlert
                .showAndWait()
                .ifPresent(buttonType -> {

                    if (buttonType == ButtonType.OK) {

                        inventoryData.remove(
                                selectedPart
                        );

                        inventoryTable.refresh();

                        updateInventorySummary();

                        showSuccess(
                                "Part deleted successfully."
                        );
                    }
                });
    }
    @FXML
    private void handleUpdatePart() {

        Inventory selectedPart =
                inventoryTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedPart == null) {
            showError(
                    "Please select a part from the table."
            );
            return;
        }

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle("Update Part");
        dialog.setHeaderText(
                "Update the selected part"
        );

        ButtonType updateButtonType =
                new ButtonType(
                        "Update",
                        ButtonBar.ButtonData.OK_DONE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        updateButtonType,
                        ButtonType.CANCEL
                );

        TextField partNameField =
                new TextField(
                        selectedPart.getPartName()
                );

        TextField brandField =
                new TextField(
                        selectedPart.getBrand()
                );

        TextField categoryField =
                new TextField(
                        selectedPart.getCategory()
                );

        TextField priceField =
                new TextField(
                        String.valueOf(
                                selectedPart.getPrice()
                        )
                );

        TextField quantityField =
                new TextField(
                        String.valueOf(
                                selectedPart.getQuantity()
                        )
                );

        TextField thresholdField =
                new TextField(
                        String.valueOf(
                                selectedPart.getThreshold()
                        )
                );

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(
                new Label("Part Code:"),
                0,
                0
        );

        grid.add(
                new Label(
                        selectedPart.getPartCode()
                ),
                1,
                0
        );

        grid.add(
                new Label("Part Name:"),
                0,
                1
        );

        grid.add(
                partNameField,
                1,
                1
        );

        grid.add(
                new Label("Brand:"),
                0,
                2
        );

        grid.add(
                brandField,
                1,
                2
        );

        grid.add(
                new Label("Category:"),
                0,
                3
        );

        grid.add(
                categoryField,
                1,
                3
        );

        grid.add(
                new Label("Price:"),
                0,
                4
        );

        grid.add(
                priceField,
                1,
                4
        );

        grid.add(
                new Label("Quantity:"),
                0,
                5
        );

        grid.add(
                quantityField,
                1,
                5
        );

        grid.add(
                new Label("Low Stock Threshold:"),
                0,
                6
        );

        grid.add(
                thresholdField,
                1,
                6
        );

        dialog.getDialogPane()
                .setContent(grid);

        dialog.showAndWait()
                .ifPresent(buttonType -> {

                    if (buttonType == updateButtonType) {

                        String partName =
                                partNameField
                                        .getText()
                                        .trim();

                        String brand =
                                brandField
                                        .getText()
                                        .trim();

                        String category =
                                categoryField
                                        .getText()
                                        .trim();

                        String priceText =
                                priceField
                                        .getText()
                                        .trim();

                        String quantityText =
                                quantityField
                                        .getText()
                                        .trim();

                        String thresholdText =
                                thresholdField
                                        .getText()
                                        .trim();

                        if (partName.isEmpty()
                                || brand.isEmpty()
                                || category.isEmpty()
                                || priceText.isEmpty()
                                || quantityText.isEmpty()
                                || thresholdText.isEmpty()) {

                            showError(
                                    "Please complete all fields."
                            );

                            return;
                        }

                        try {

                            double price =
                                    Double.parseDouble(
                                            priceText
                                    );

                            int quantity =
                                    Integer.parseInt(
                                            quantityText
                                    );

                            int threshold =
                                    Integer.parseInt(
                                            thresholdText
                                    );

                            if (price < 0
                                    || quantity < 0
                                    || threshold < 0) {

                                showError(
                                        "Price, quantity, and threshold cannot be negative."
                                );

                                return;
                            }

                            selectedPart.setPartName(
                                    partName
                            );

                            selectedPart.setBrand(
                                    brand
                            );

                            selectedPart.setCategory(
                                    category
                            );

                            selectedPart.setPrice(
                                    price
                            );

                            selectedPart.setQuantity(
                                    quantity
                            );

                            selectedPart.setThreshold(
                                    threshold
                            );

                            inventoryTable.refresh();

                            updateInventorySummary();

                            showSuccess(
                                    "Part updated successfully."
                            );

                        } catch (NumberFormatException e) {

                            showError(
                                    "Price must be a valid number. Quantity and threshold must be whole numbers."
                            );
                        }
                    }
                });
    }
    @FXML
    private void handleSearchInventory() {

        String partCode =
                searchPartCodeField
                        .getText()
                        .trim()
                        .toLowerCase();

        String partName =
                searchPartNameField
                        .getText()
                        .trim()
                        .toLowerCase();

        String brand =
                searchBrandField
                        .getText()
                        .trim()
                        .toLowerCase();

        String category =
                searchCategoryField
                        .getText()
                        .trim()
                        .toLowerCase();

        String minimumPriceText =
                searchMinPriceField
                        .getText()
                        .trim();

        String maximumPriceText =
                searchMaxPriceField
                        .getText()
                        .trim();

        double minimumPrice = 0;
        double maximumPrice = Double.MAX_VALUE;

        try {

            if (!minimumPriceText.isEmpty()) {
                minimumPrice =
                        Double.parseDouble(minimumPriceText);
            }

            if (!maximumPriceText.isEmpty()) {
                maximumPrice =
                        Double.parseDouble(maximumPriceText);
            }

            if (minimumPrice < 0 || maximumPrice < 0) {
                showError("Price cannot be negative.");
                return;
            }

            if (minimumPrice > maximumPrice) {
                showError(
                        "Minimum price cannot be greater than maximum price."
                );
                return;
            }

        } catch (NumberFormatException exception) {

            showError(
                    "Minimum and maximum price must be valid numbers."
            );

            return;
        }

        ObservableList<Inventory> searchResults =
                FXCollections.observableArrayList();

        for (Inventory item : inventoryData) {

            boolean matchesPartCode =
                    partCode.isEmpty()
                            || item.getPartCode()
                            .toLowerCase()
                            .contains(partCode);

            boolean matchesPartName =
                    partName.isEmpty()
                            || item.getPartName()
                            .toLowerCase()
                            .contains(partName);

            boolean matchesBrand =
                    brand.isEmpty()
                            || item.getBrand()
                            .toLowerCase()
                            .contains(brand);

            boolean matchesCategory =
                    category.isEmpty()
                            || item.getCategory()
                            .toLowerCase()
                            .contains(category);

            boolean matchesPrice =
                    item.getPrice() >= minimumPrice
                            && item.getPrice() <= maximumPrice;

            if (matchesPartCode
                    && matchesPartName
                    && matchesBrand
                    && matchesCategory
                    && matchesPrice) {

                searchResults.add(item);
            }
        }

        searchResultsTable.setItems(searchResults);

        if (searchResults.isEmpty()) {
            showError("No matching inventory items were found.");
        }
    }
    @FXML
    private void handleResetSearch() {

        searchPartCodeField.clear();
        searchPartNameField.clear();
        searchBrandField.clear();
        searchCategoryField.clear();
        searchMinPriceField.clear();
        searchMaxPriceField.clear();

        searchResultsTable.setItems(inventoryData);
    }
    @FXML
    private void handleRefreshLowStock() {

        ObservableList<Inventory> lowStockItems =
                FXCollections.observableArrayList();

        for (Inventory item : inventoryData) {

            if (item.getQuantity()
                    < item.getThreshold()) {

                lowStockItems.add(item);
            }
        }

        lowStockTable.setItems(lowStockItems);

        if (lowStockItems.isEmpty()) {
            showSuccess(
                    "There are no low-stock items."
            );

        }
    }
    @FXML
    private void handleSelectDealers() {

        List<Dealer> selectedDealers =
                dealerService.selectRandomDealers();

        ObservableList<Dealer> dealerData =
                FXCollections.observableArrayList(
                        selectedDealers
                );

        dealerTable.setItems(dealerData);

        if (dealerData.isEmpty()) {
            showError(
                    "No dealers were available."
            );
        }
    }
}