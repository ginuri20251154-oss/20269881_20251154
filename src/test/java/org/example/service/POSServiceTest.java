package org.example.service;

import org.example.model.Cart;
import org.example.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class POSServiceTest {

    private POSService posService;
    private List<Inventory> items;

    private Inventory piston;
    private Inventory brakePad;
    private Inventory sparkPlug;

    @BeforeEach
    void setUp() {

        posService = new POSService();

        piston = new Inventory(
                "P001",
                "Bajaj 4-Stroke Piston",
                "Bajaj",
                4500,
                15,
                "Engine",
                "2023-10-12",
                "piston.jpg",
                10
        );

        brakePad = new Inventory(
                "P002",
                "Brake Pad",
                "TVS",
                1250,
                8,
                "Brakes",
                "2023-12-01",
                "brake.png",
                8
        );

        sparkPlug = new Inventory(
                "P003",
                "Spark Plug",
                "NGK",
                850,
                30,
                "Electrical",
                "2024-01-05",
                "spark.jpg",
                15
        );

        items = new ArrayList<>();
        items.add(piston);
        items.add(brakePad);
        items.add(sparkPlug);
    }

    // ---------- findItem ----------

    @Test
    void testFindItemFound() {
        Inventory found = posService.findItem(items, "P002");

        assertNotNull(found);
        assertEquals("Brake Pad", found.getPartName());
    }

    @Test
    void testFindItemIsCaseInsensitive() {
        Inventory found = posService.findItem(items, "p002");

        assertNotNull(found);
        assertEquals("P002", found.getPartCode());
    }

    @Test
    void testFindItemNotFound() {
        Inventory found = posService.findItem(items, "P999");

        assertNull(found);
    }

    // ---------- hasEnoughStock ----------

    @Test
    void testHasEnoughStockSufficient() {
        assertTrue(posService.hasEnoughStock(piston, 10));
    }

    @Test
    void testHasEnoughStockExactMatch() {
        // quantity is exactly equal to available stock
        assertTrue(posService.hasEnoughStock(piston, 15));
    }

    @Test
    void testHasEnoughStockInsufficient() {
        assertFalse(posService.hasEnoughStock(piston, 20));
    }

    @Test
    void testHasEnoughStockZeroQuantity() {
        assertFalse(posService.hasEnoughStock(piston, 0));
    }

    @Test
    void testHasEnoughStockNegativeQuantity() {
        assertFalse(posService.hasEnoughStock(piston, -5));
    }

    // ---------- calculateTotal (bulk discount) ----------

    @Test
    void testCalculateTotalNoBulkDiscountBelowThreshold() {
        // purchaseQuantity < 3, no 5% discount
        double total = posService.calculateTotal(brakePad, 2);

        assertEquals(1250 * 2, total, 0.01);
    }

    @Test
    void testCalculateTotalAppliesBulkDiscountAtThreshold() {
        // purchaseQuantity == 3, discount should apply
        double expected = (1250 * 3) - ((1250 * 3) * 0.05);

        double total = posService.calculateTotal(brakePad, 3);

        assertEquals(expected, total, 0.01);
    }

    @Test
    void testCalculateTotalAppliesBulkDiscountAboveThreshold() {
        double expected = (4500 * 5) - ((4500 * 5) * 0.05);

        double total = posService.calculateTotal(piston, 5);

        assertEquals(expected, total, 0.01);
    }

    // ---------- updateStock ----------

    @Test
    void testUpdateStockReducesQuantity() {
        posService.updateStock(piston, 5);

        assertEquals(10, piston.getQuantity());
    }

    @Test
    void testUpdateStockCanReduceToZero() {
        posService.updateStock(piston, 15);

        assertEquals(0, piston.getQuantity());
    }

    // ---------- synergyDiscount ----------

    @Test
    void testSynergyDiscountTrueWhenEngineAndElectricalPresent() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(piston, 1, 4500));
        cartItems.add(new Cart(sparkPlug, 1, 850));

        assertTrue(posService.synergyDiscount(cartItems));
    }

    @Test
    void testSynergyDiscountFalseWhenOnlyEnginePresent() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(piston, 1, 4500));
        cartItems.add(new Cart(brakePad, 1, 1250));

        assertFalse(posService.synergyDiscount(cartItems));
    }

    @Test
    void testSynergyDiscountFalseWhenNeitherPresent() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(brakePad, 1, 1250));

        assertFalse(posService.synergyDiscount(cartItems));
    }

    // ---------- calculateCartTotal ----------

    @Test
    void testCalculateCartTotalSumsDiscountedSubtotals() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(piston, 1, 4500));
        cartItems.add(new Cart(brakePad, 1, 1250));

        double total = posService.calculateCartTotal(cartItems);

        assertEquals(4500 + 1250, total, 0.01);
    }

    @Test
    void testCalculateCartTotalEmptyCartIsZero() {
        List<Cart> cartItems = new ArrayList<>();

        double total = posService.calculateCartTotal(cartItems);

        assertEquals(0, total, 0.01);
    }

    // ---------- applySynergyDiscount ----------

    @Test
    void testApplySynergyDiscountAppliedWhenEligible() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(piston, 1, 4500));
        cartItems.add(new Cart(sparkPlug, 1, 850));

        double cartTotal = 4500 + 850;
        double expected = cartTotal * 0.90;

        double finalTotal = posService.applySynergyDiscount(cartItems, cartTotal);

        assertEquals(expected, finalTotal, 0.01);
    }

    @Test
    void testApplySynergyDiscountNotAppliedWhenNotEligible() {
        List<Cart> cartItems = new ArrayList<>();
        cartItems.add(new Cart(brakePad, 1, 1250));

        double cartTotal = 1250;

        double finalTotal = posService.applySynergyDiscount(cartItems, cartTotal);

        assertEquals(cartTotal, finalTotal, 0.01);
    }
}