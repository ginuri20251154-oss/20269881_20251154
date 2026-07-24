package org.example.service;

import org.example.model.Dealer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerService {
    private String fileName;

    public DealerService(String fileName) {
        this.fileName = fileName;
    }

    public List<Dealer> loadDealers() {

        List<Dealer> dealers = new ArrayList<>();

        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(fileName));

            String line;

            while ((line = reader.readLine()) != null) {

                try {
                    line = line.replace("|", ",");
                    line = line.replace(";", ",");

                    String[] data = line.split(",", -1);

                    Dealer dealer = new Dealer(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim()
                    );

                    dealers.add(dealer);

                } catch (Exception e) {
                    System.out.println("Skipped dealer: " + line);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not read dealer file.");
        }

        return dealers;
    }

    public List<Dealer> selectRandomDealers() {

        List<Dealer> allDealers = loadDealers();
        List<Dealer> selectedDealers = new ArrayList<>();

        if (allDealers.size() < 4) {
            System.out.println("At least four dealers are required.");
            return selectedDealers;
        }

        Random random = new Random();

        while (selectedDealers.size() < 4) {

            int randomIndex = random.nextInt(allDealers.size());

            Dealer selectedDealer = allDealers.get(randomIndex);

            if (!selectedDealers.contains(selectedDealer)) {
                selectedDealers.add(selectedDealer);
            }
        }

        sortDealersByLocation(selectedDealers);

        return selectedDealers;
    }

    private void sortDealersByLocation(List<Dealer> dealers) {

        for (int i = 0; i < dealers.size() - 1; i++) {

            for (int j = 0; j < dealers.size() - i - 1; j++) {

                Dealer first = dealers.get(j);
                Dealer second = dealers.get(j + 1);

                if (first.getLocation().trim()
                        .compareToIgnoreCase(
                                second.getLocation().trim()
                        ) > 0) {

                    Dealer temp = dealers.get(j);
                    dealers.set(j, dealers.get(j + 1));
                    dealers.set(j + 1, temp);
                }
            }
        }
    }
}