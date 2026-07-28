package org.example.service;

import org.example.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventoryService;
    private List<Inventory> items;

    @BeforeEach
    void setUp() {

        inventoryService = new InventoryService("inventory_legacy.txt");

        items = new ArrayList<>();

        items.add(new Inventory(
                "P001",
                "Bajaj 4-Stroke Piston",
                "Bajaj",
                4500,
                15,
                "Engine",
                "2023-10-12",
                "piston.jpg",
                10
        ));

        items.add(new Inventory(
                "P002",
                "Brake Pad",
                "TVS",
                1250,
                8,
                "Brakes",
                "2023-12-01",
                "brake.png",
                8
        ));

        items.add(new Inventory(
                "P003",
                "Tyre",
                "Unknown",
                6500,
                24,
                "Bodywork",
                "2023-11-01",
                "tyre.png",
                5
        ));
    }

    @Test
    void testTotalQuantity() {
        int total = inventoryService.getTotalQuantity(items);
        assertEquals(47, total);
    }

    @Test
    void testTotalInventoryValue() {
        double expected =
                (4500 * 15)
                        + (1250 * 8)
                        + (6500 * 24);

        assertEquals(
                expected,
                inventoryService.getTotalValue(items),
                0.01
        );
    }

    @Test
    void testAddPartSuccessfully() {
        Inventory newItem = new Inventory(
                "P004",
                "Spark Plug",
                "NGK",
                850,
                30,
                "Electrical",
                "2024-01-05",
                "spark.jpg",
                15
        );

        boolean added = inventoryService.addPart(items, newItem);

        assertTrue(added);
        assertEquals(4, items.size());
    }

    @Test
    void testDuplicatePartRejected() {
        Inventory duplicate = new Inventory(
                "P001",
                "Different Name",
                "Different",
                100,
                5,
                "Engine",
                "",
                "",
                5
        );

        boolean added = inventoryService.addPart(items, duplicate);

        assertFalse(added);
        assertEquals(3, items.size());
    }

    @Test
    void testDeleteExistingPart() {
        boolean deleted = inventoryService.deletePart(items, "P002");

        assertTrue(deleted);
        assertEquals(2, items.size());
    }

    @Test
    void testDeleteNonExistingPart() {
        boolean deleted = inventoryService.deletePart(items, "P999");

        assertFalse(deleted);
        assertEquals(3, items.size());
    }

    @Test
    void testUpdatePartSuccessfully() {
        boolean updated = inventoryService.updatePart(
                items,
                "P003",
                "Updated Tyre",
                "Michelin",
                7000,
                20,
                "Bodywork",
                "2024-01-01",
                "new.png",
                6
        );

        assertTrue(updated);

        Inventory item = items.get(2);

        assertEquals("Updated Tyre", item.getPartName());
        assertEquals("Michelin", item.getBrand());
        assertEquals(7000, item.getPrice());
        assertEquals(20, item.getQuantity());
        assertEquals(6, item.getThreshold());
    }

    @Test
    void testUpdatePartFailsWhenNotFound() {
        boolean updated = inventoryService.updatePart(
                items,
                "P999",
                "Test",
                "Test",
                100,
                1,
                "Engine",
                "",
                "",
                5
        );

        assertFalse(updated);
    }

    @Test
    void testLowStockItems() {
        items.get(0).setQuantity(5);

        List<Inventory> lowStock = inventoryService.getLowStockItems(items);

        assertEquals(1, lowStock.size());
        assertEquals("P001", lowStock.get(0).getPartCode());
    }

    @Test
    void testUpdateLowStockThreshold() {
        boolean updated = inventoryService.updateLowStockThreshold(
                items,
                "P003",
                30
        );

        assertTrue(updated);
        assertEquals(30, items.get(2).getThreshold());
    }

    @Test
    void testUpdateThresholdInvalidValue() {
        boolean updated = inventoryService.updateLowStockThreshold(
                items,
                "P003",
                -5
        );

        assertFalse(updated);
    }

    @Test
    void testUpdateThresholdUnknownPart() {
        boolean updated = inventoryService.updateLowStockThreshold(
                items,
                "P999",
                5
        );

        assertFalse(updated);
    }

    @Test
    void testSearchByPartName() {
        List<Inventory> results = inventoryService.searchInventory(
                "Piston",
                "",
                ""
        );

        assertFalse(results.isEmpty());
    }

    @Test
    void testSearchByBrand() {
        List<Inventory> results = inventoryService.searchInventory(
                "",
                "NGK",
                ""
        );

        assertEquals(1, results.size());
    }

    @Test
    void testSearchByCategory() {
        List<Inventory> results = inventoryService.searchInventory(
                "",
                "",
                "Engine"
        );

        assertEquals(3, results.size());
    }

    @Test
    void testCombinedSearchFilters() {
        List<Inventory> results = inventoryService.searchInventory(
                "Piston",
                "Bajaj",
                "Engine"
        );

        assertEquals(1, results.size());
    }

    @Test
    void testLoadInventory() {
        List<Inventory> loaded = inventoryService.loadInventory();

        assertFalse(loaded.isEmpty());
    }

    @Test
    void testInventorySorting() {
        List<Inventory> loaded = inventoryService.loadInventory();

        for (int i = 0; i < loaded.size() - 1; i++) {
            Inventory first = loaded.get(i);
            Inventory second = loaded.get(i + 1);

            if (first.getCategory().equalsIgnoreCase(second.getCategory())) {
                assertTrue(
                        first.getPartCode()
                                .compareToIgnoreCase(second.getPartCode()) <= 0
                );
            }
        }
    }

    @Test
    void testSaveInventory() {
        assertDoesNotThrow(() ->
                inventoryService.saveInventory(items)
        );
    }
}