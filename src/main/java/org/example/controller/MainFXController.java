package org.example.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.example.model.Cart;
import org.example.model.Dealer;
import org.example.model.Inventory;
import org.example.service.DealerService;
import org.example.service.InventoryService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainFXController {
    @FXML
    private TextField posPartCodeField;

    @FXML
    private TextField posQuantityField;
    @FXML
    private TableView<Cart> cartTable;
    @FXML
    private TableColumn<Cart, String> cartPartCodeColumn;

    @FXML
    private TableColumn<Cart, String> cartPartNameColumn;

    @FXML
    private TableColumn<Cart, Integer> cartQuantityColumn;

    @FXML
    private TableColumn<Cart, Double> cartPriceColumn;

    @FXML
    private TableColumn<Cart, Double> cartSubtotalColumn;
    @FXML
    private Label cartTotalLabel;
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
    private TableColumn<Inventory, String> imageColumn;

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

    @FXML
    private TableColumn<Inventory, String> searchImageColumn;
    private ObservableList<Inventory> inventoryData;

    @FXML
    public void initialize() {

        configureTableColumns();

        loadInventoryData();
    }
    private ObservableList<Cart> cartData = FXCollections.observableArrayList();

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
                        "Threshold"
                )
        );

        imageColumn.setCellValueFactory(
                new PropertyValueFactory<>("imageName")
        );
        imageColumn.setCellFactory(column -> createImageCell());

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

        searchImageColumn.setCellValueFactory(
                new PropertyValueFactory<>("imageName")
        );
        searchImageColumn.setCellFactory(column -> createImageCell());

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
                new PropertyValueFactory<>("Threshold")
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
        cartPartCodeColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(
                                cellData.getValue()
                                        .getInventoryItem()
                                        .getPartCode()
                        )
        );

        cartPartNameColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(
                                cellData.getValue()
                                        .getInventoryItem()
                                        .getPartName()
                        )
        );

        cartQuantityColumn.setCellValueFactory(
                cellData ->
                        new SimpleIntegerProperty(
                                cellData.getValue()
                                        .getPurchaseQuantity()
                        ).asObject()
        );

        cartPriceColumn.setCellValueFactory(
                cellData ->
                        new SimpleDoubleProperty(
                                cellData.getValue()
                                        .getInventoryItem()
                                        .getPrice()
                        ).asObject()
        );

        cartSubtotalColumn.setCellValueFactory(
                cellData ->
                        new SimpleDoubleProperty(
                                cellData.getValue()
                                        .getDiscountedSubtotal()
                        ).asObject()
        );
        cartTable.setItems(cartData);
    }

    private TableCell<Inventory, String> createImageCell() {
        return new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageName, boolean empty) {
                super.updateItem(imageName, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                String fileName = imageName;

                if (fileName == null || fileName.isBlank()
                        || fileName.equalsIgnoreCase("No Image")) {
                    fileName = "no image.jpg";
                }

                File file = new File(fileName);

                if (!file.exists()) {
                    file = new File(
                            "src/main/java/org/example/InputFiles/" + fileName
                    );
                }

                if (!file.exists()) {
                    file = new File(
                            "src/main/java/org/example/InputFiles/no image.jpg"
                    );
                }

                if (file.exists()) {
                    imageView.setImage(new Image(file.toURI().toString()));
                    imageView.setFitWidth(60);
                    imageView.setFitHeight(60);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                } else {
                    setGraphic(null);
                }
            }
        };
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

        Label imageLabel = new Label("No image selected");
        Button chooseImageButton = new Button("Choose Image");

        chooseImageButton.setOnAction(e -> {
            File file = new FileChooser().showOpenDialog(dialog.getOwner());
            if (file != null) {
                imageLabel.setText(file.getAbsolutePath());
            }
        });

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

        grid.add(new Label("Image:"), 0, 8);
        grid.add(chooseImageButton, 1, 8);
        grid.add(imageLabel, 2, 8);

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

                List<String> errors = new ArrayList<>();

                if (partCode.isEmpty()) {
                    errors.add("Part code is required.");
                } else if (!isValidPartCode(partCode)) {
                    errors.add(
                            "Invalid part code. Expected format: one letter followed by "
                                    + "3 digits, e.g. P001."
                    );
                }

                if (partName.isEmpty()) {
                    errors.add("Part name is required.");
                }

                if (brand.isEmpty()) {
                    errors.add("Brand is required.");
                }

                if (category.isEmpty()) {
                    errors.add("Category is required.");
                } else if (!category.matches("^[A-Za-z ]+$")) {
                    errors.add("Category must contain letters only (no numbers or symbols).");
                }

                Double price = null;
                if (priceText.isEmpty()) {
                    errors.add("Price is required.");
                } else {
                    try {
                        price = Double.parseDouble(priceText);
                        if (price < 0) {
                            errors.add("Price cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("Price must be a valid number, e.g. 850.00.");
                    }
                }

                Integer quantity = null;
                if (quantityText.isEmpty()) {
                    errors.add("Quantity is required.");
                } else {
                    try {
                        quantity = Integer.parseInt(quantityText);
                        if (quantity < 0) {
                            errors.add("Quantity cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("Quantity must be a whole number, e.g. 12.");
                    }
                }

                Integer threshold = null;
                if (thresholdText.isEmpty()) {
                    errors.add("Low stock threshold is required.");
                } else {
                    try {
                        threshold = Integer.parseInt(thresholdText);
                        if (threshold < 0) {
                            errors.add("Low stock threshold cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        errors.add("Low stock threshold must be a whole number, e.g. 5.");
                    }
                }

                if (stockDatePicker.getValue() == null) {
                    errors.add("Stock date is required.");
                } else if (stockDatePicker.getValue().isAfter(LocalDate.now())) {
                    errors.add("Stock date cannot be in the future.");
                } else if (stockDatePicker.getValue().isBefore(LocalDate.of(2000, 1, 1))) {
                    errors.add("Stock date cannot be before the year 2000.");
                }

                if (!errors.isEmpty()) {
                    showErrors(errors);
                    return;
                }

                String stockDate = stockDatePicker.getValue().toString();

                String imageName =
                        imageLabel.getText().equals("No image selected")
                                ? ""
                                : imageLabel.getText();

                Inventory newPart = new Inventory(
                        partCode,
                        partName,
                        brand,
                        price,
                        quantity,
                        category,
                        stockDate,
                        imageName,
                        threshold
                );

                boolean added = inventoryService.addPart(inventoryData, newPart);

                if (!added) {
                    showError("A part with this part code already exists.");
                    return;
                }

                inventoryTable.refresh();
                updateInventorySummary();
                showSuccess("Part added successfully.");
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
    private void showErrors(List<String> errors) {

        StringBuilder message = new StringBuilder();

        for (String error : errors) {
            message.append("\u2022 ").append(error).append("\n");
        }

        showError(message.toString().trim());
    }
    private void showSuccess(String message) {

        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidPartCode(String partCode) {
        return partCode.matches("^[A-Za-z]\\d{3}$");
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

                        List<String> errors = new ArrayList<>();

                        if (partName.isEmpty()) {
                            errors.add("Part name is required.");
                        }

                        if (brand.isEmpty()) {
                            errors.add("Brand is required.");
                        }

                        if (category.isEmpty()) {
                            errors.add("Category is required.");
                        } else if (!category.matches("^[A-Za-z ]+$")) {
                            errors.add("Category must contain letters only (no numbers or symbols).");
                        }

                        Double price = null;
                        if (priceText.isEmpty()) {
                            errors.add("Price is required.");
                        } else {
                            try {
                                price = Double.parseDouble(priceText);
                                if (price < 0) {
                                    errors.add("Price cannot be negative.");
                                }
                            } catch (NumberFormatException e) {
                                errors.add("Price must be a valid number, e.g. 850.00.");
                            }
                        }

                        Integer quantity = null;
                        if (quantityText.isEmpty()) {
                            errors.add("Quantity is required.");
                        } else {
                            try {
                                quantity = Integer.parseInt(quantityText);
                                if (quantity < 0) {
                                    errors.add("Quantity cannot be negative.");
                                }
                            } catch (NumberFormatException e) {
                                errors.add("Quantity must be a whole number, e.g. 12.");
                            }
                        }

                        Integer threshold = null;
                        if (thresholdText.isEmpty()) {
                            errors.add("Low stock threshold is required.");
                        } else {
                            try {
                                threshold = Integer.parseInt(thresholdText);
                                if (threshold < 0) {
                                    errors.add("Low stock threshold cannot be negative.");
                                }
                            } catch (NumberFormatException e) {
                                errors.add("Low stock threshold must be a whole number, e.g. 5.");
                            }
                        }

                        if (!errors.isEmpty()) {
                            showErrors(errors);
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
    @FXML
    private void handleAddToCart() {

        String partCode =
                posPartCodeField
                        .getText()
                        .trim();

        String quantityText =
                posQuantityField
                        .getText()
                        .trim();

        if (partCode.isEmpty() || quantityText.isEmpty()) {
            showError(
                    "Please enter both part code and quantity."
            );
            return;
        }

        int purchaseQuantity;

        try {
            purchaseQuantity =
                    Integer.parseInt(quantityText);

            if (purchaseQuantity <= 0) {
                showError(
                        "Quantity must be greater than zero."
                );
                return;
            }

        } catch (NumberFormatException exception) {
            showError(
                    "Quantity must be a whole number."
            );
            return;
        }

        Inventory selectedItem = null;

        for (Inventory item : inventoryData) {

            if (item.getPartCode()
                    .equalsIgnoreCase(partCode)) {

                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            showError(
                    "No inventory item was found for part code: "
                            + partCode
            );
            return;
        }

        if (purchaseQuantity
                > selectedItem.getQuantity()) {

            showError(
                    "Insufficient stock. Available quantity: "
                            + selectedItem.getQuantity()
            );
            return;
        }

        double subtotal =
                selectedItem.getPrice()
                        * purchaseQuantity;

        if (purchaseQuantity >= 3) {
            subtotal = subtotal * 0.95;
        }

        Cart cartItem =
                new Cart(
                        selectedItem,
                        purchaseQuantity,
                        subtotal
                );

        Cart existingCartItem = null;

        for (Cart item : cartData) {

            if (item.getInventoryItem()
                    .getPartCode()
                    .equalsIgnoreCase(partCode)) {

                existingCartItem = item;
                break;
            }
        }

        if (existingCartItem != null) {

            int updatedQuantity =
                    existingCartItem.getPurchaseQuantity()
                            + purchaseQuantity;

            if (updatedQuantity > selectedItem.getQuantity()) {

                showError(
                        "Insufficient stock. Available quantity: "
                                + selectedItem.getQuantity()
                );
                return;
            }

            double updatedSubtotal =
                    selectedItem.getPrice()
                            * updatedQuantity;

            if (updatedQuantity >= 3) {
                updatedSubtotal =
                        updatedSubtotal * 0.95;
            }

            existingCartItem.setPurchaseQuantity(
                    updatedQuantity
            );

            existingCartItem.setDiscountedSubtotal(
                    updatedSubtotal
            );

            cartTable.refresh();

        } else {

            cartData.add(cartItem);
        }

        updateCartTotal();

        posPartCodeField.clear();
        posQuantityField.clear();
    }
    private void updateCartTotal() {

        double total = 0;

        for (Cart cartItem : cartData) {
            total += cartItem.getDiscountedSubtotal();
        }

        cartTotalLabel.setText(
                String.format("$%.2f", total)
        );
    }
    @FXML
    private void handleRemoveCartItem() {

        Cart selectedCartItem =
                cartTable
                        .getSelectionModel()
                        .getSelectedItem();

        if (selectedCartItem == null) {
            showError(
                    "Please select an item from the cart."
            );
            return;
        }

        cartData.remove(selectedCartItem);

        updateCartTotal();

        showSuccess(
                "Item removed from cart."
        );
    }
    @FXML
    private void handleClearCart() {

        if (cartData.isEmpty()) {
            showError("The cart is already empty.");
            return;
        }

        Alert confirmationAlert =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmationAlert.setTitle("Clear Cart");
        confirmationAlert.setHeaderText("Confirm Cart Clearance");
        confirmationAlert.setContentText(
                "Are you sure you want to remove all items from the cart?"
        );

        confirmationAlert.showAndWait().ifPresent(buttonType -> {

            if (buttonType == ButtonType.OK) {

                cartData.clear();

                updateCartTotal();

                showSuccess("Cart cleared successfully.");
            }
        });
    }
    @FXML
    private void handleCheckout() {

        if (cartData.isEmpty()) {
            showError("The cart is empty.");
            return;
        }

        double originalCartTotal = 0;
        double totalAfterBulkDiscount = 0;

        for (Cart cartItem : cartData) {

            Inventory inventoryItem =
                    cartItem.getInventoryItem();

            double originalSubtotal =
                    inventoryItem.getPrice()
                            * cartItem.getPurchaseQuantity();

            originalCartTotal += originalSubtotal;

            totalAfterBulkDiscount +=
                    cartItem.getDiscountedSubtotal();
        }

        double bulkDiscountAmount =
                originalCartTotal
                        - totalAfterBulkDiscount;

        boolean hasEngineItem = false;
        boolean hasElectricalItem = false;

        for (Cart cartItem : cartData) {

            String category =
                    cartItem.getInventoryItem()
                            .getCategory();

            if (category.equalsIgnoreCase("Engine")) {
                hasEngineItem = true;
            }

            if (category.equalsIgnoreCase("Electrical")) {
                hasElectricalItem = true;
            }
        }

        boolean synergyDiscountApplied =
                hasEngineItem && hasElectricalItem;

        double synergyDiscountAmount = 0;

        if (synergyDiscountApplied) {
            synergyDiscountAmount =
                    totalAfterBulkDiscount * 0.10;
        }

        double finalTotal =
                totalAfterBulkDiscount
                        - synergyDiscountAmount;

        Alert confirmationAlert =
                new Alert(Alert.AlertType.CONFIRMATION);

        confirmationAlert.setTitle("Checkout");
        confirmationAlert.setHeaderText(
                "Confirm Purchase"
        );

        confirmationAlert.setContentText(
                String.format(
                        "Original Total: $%.2f%n"
                                + "Bulk Discount: -$%.2f%n"
                                + "Total After Bulk Discount: $%.2f%n"
                                + "Synergy Discount: -$%.2f%n"
                                + "Final Total: $%.2f%n%n"
                                + "Do you want to complete the purchase?",
                        originalCartTotal,
                        bulkDiscountAmount,
                        totalAfterBulkDiscount,
                        synergyDiscountAmount,
                        finalTotal
                )
        );

        confirmationAlert
                .showAndWait()
                .ifPresent(buttonType -> {

                    if (buttonType == ButtonType.OK) {
                        completeCheckout(finalTotal);
                    }
                });
    }
    private void completeCheckout(double finalTotal) {

        for (Cart cartItem : cartData) {

            Inventory inventoryItem =
                    cartItem.getInventoryItem();

            int updatedQuantity =
                    inventoryItem.getQuantity()
                            - cartItem.getPurchaseQuantity();

            inventoryItem.setQuantity(
                    updatedQuantity
            );
        }

        inventoryTable.refresh();
        searchResultsTable.refresh();

        loadLowStockItems();

        cartData.clear();

        updateCartTotal();

        updateInventorySummary();

        showSuccess(
                String.format(
                        "Checkout completed successfully.%n"
                                + "Final Total: $%.2f",
                        finalTotal
                )
        );
    }
    private void loadLowStockItems() {

        ObservableList<Inventory> lowStockItems = FXCollections.observableArrayList();
        for (Inventory item : inventoryData) {
            if (item.getQuantity() < item.getThreshold()) {
                lowStockItems.add(item);
            }
        }
        lowStockTable.setItems(lowStockItems);
    }
}