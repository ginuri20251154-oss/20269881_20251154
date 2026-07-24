package org.example.controller;

import org.example.model.Dealer;
import org.example.service.DealerService;

import java.util.List;

public class DealerController {
    private DealerService dealerService;

    public DealerController(DealerService dealerService) {
        this.dealerService = dealerService;
    }

    public void displayRandomDealers() {

        List<Dealer> dealers =
                dealerService.selectRandomDealers();

        if (dealers.isEmpty()) {
            System.out.println("No dealers found.");
            return;
        }

        System.out.println("\n===== RANDOM DEALERS =====");

        for (Dealer dealer : dealers) {
            System.out.println(dealer);
        }
    }
}
