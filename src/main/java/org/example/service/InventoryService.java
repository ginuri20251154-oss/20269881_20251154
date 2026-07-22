package org.example.service;

import org.example.model.Inventory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryService {

    private String fileName;

    public InventoryService(String fileName) {
        this.fileName = fileName;
    }

    public List<Inventory> loadInventory() {

        List<Inventory> items = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {
                    // Handle the date containing a comma
                    line = line.replace("Oct 15, 2023", "15-Oct-2023");

                    // Convert all separators into commas
                    line = line.replace("|", ",");
                    line = line.replace(";", ",");

                    String[] data = line.split(",", -1);

                    String partCode = data[0].trim();
                    String partName = data[1].trim();

                    String brand = data[2].trim();

                    if (brand.isEmpty()) {
                        brand = "Unknown";
                    }

                    String priceText = data[3]
                            .replace("Rs.", "")
                            .replace("Rs", "")
                            .trim();

                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(data[4].trim());

                    String category = data[5].trim();
                    String stockDate = data[6].trim();

                    String imageName = "No Image";

                    if (data.length > 7 && !data[7].trim().isEmpty()) {
                        imageName = data[7].trim();
                    }

                    Inventory item = new Inventory(
                            partCode,
                            partName,
                            brand,
                            price,
                            quantity,
                            category,
                            stockDate,
                            imageName
                    );

                    items.add(item);

                } catch (Exception e) {
                    System.out.println("Skipped invalid line: " + line);
                }
            }

        } catch (Exception e) {
            System.out.println("Could not read inventory file.");
        }

        items.sort(
                Comparator.comparing(
                        Inventory::getCategory,
                        String.CASE_INSENSITIVE_ORDER
                ).thenComparing(Inventory::getPartCode)
        );

        return items;
    }

    public int getTotalQuantity(List<Inventory> items) {

        int total = 0;

        for (Inventory item : items) {
            total += item.getQuantity();
        }

        return total;
    }

    public double getTotalValue(List<Inventory> items) {

        double total = 0;

        for (Inventory item : items) {
            total += item.getItemValue();
        }

        return total;
    }
}
