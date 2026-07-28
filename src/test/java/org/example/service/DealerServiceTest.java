package org.example.service;

import org.example.model.Dealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealerServiceTest {

    @TempDir
    Path tempDir;

    private DealerService dealerService;
    private Path dealerFile;

    @BeforeEach
    void setUp() throws IOException {
        dealerFile = tempDir.resolve("dealers.txt");
        dealerService = new DealerService(dealerFile.toString());
    }

    private void writeLines(String... lines) throws IOException {
        Files.write(dealerFile, List.of(lines));
    }

    @Test
    void testLoadDealersParsesCommaDelimited() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "D002,Kandy Auto,0812233445,Kandy"
        );

        List<Dealer> dealers = dealerService.loadDealers();

        assertEquals(2, dealers.size());
        assertEquals("D001", dealers.get(0).getDealerCode());
        assertEquals("Colombo Motors", dealers.get(0).getDealerName());
        assertEquals("0112233445", dealers.get(0).getPhoneNumber());
        assertEquals("Colombo", dealers.get(0).getLocation());
    }

    @Test
    void testLoadDealersParsesPipeAndSemicolonDelimiters() throws IOException {
        writeLines(
                "D003|Galle Wheels|0912233445|Galle",
                "D004;Jaffna Spares;0212233445;Jaffna"
        );

        List<Dealer> dealers = dealerService.loadDealers();

        assertEquals(2, dealers.size());
        assertEquals("Galle Wheels", dealers.get(0).getDealerName());
        assertEquals("Jaffna", dealers.get(1).getLocation());
    }

    @Test
    void testLoadDealersSkipsMalformedLines() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "BadLineWithoutEnoughFields",
                "D002,Kandy Auto,0812233445,Kandy"
        );

        List<Dealer> dealers = dealerService.loadDealers();

        // malformed line should be skipped, not crash the load
        assertEquals(2, dealers.size());
    }

    @Test
    void testLoadDealersReturnsEmptyListWhenFileMissing() {
        DealerService missingFileService =
                new DealerService(tempDir.resolve("does_not_exist.txt").toString());

        List<Dealer> dealers = missingFileService.loadDealers();

        assertNotNull(dealers);
        assertTrue(dealers.isEmpty());
    }

    @Test
    void testSelectRandomDealersReturnsEmptyWhenFewerThanFour() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "D002,Kandy Auto,0812233445,Kandy",
                "D003,Galle Wheels,0912233445,Galle"
        );

        List<Dealer> dealers = dealerService.selectRandomDealers();

        assertTrue(dealers.isEmpty());
    }

    @Test
    void testSelectRandomDealersReturnsExactlyFour() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "D002,Kandy Auto,0812233445,Kandy",
                "D003,Galle Wheels,0912233445,Galle",
                "D004,Jaffna Spares,0212233445,Jaffna",
                "D005,Negombo Garage,0312233445,Negombo",
                "D006,Matara Autos,0412233445,Matara"
        );

        List<Dealer> dealers = dealerService.selectRandomDealers();

        assertEquals(4, dealers.size());
    }

    @Test
    void testSelectRandomDealersReturnsNoDuplicates() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "D002,Kandy Auto,0812233445,Kandy",
                "D003,Galle Wheels,0912233445,Galle",
                "D004,Jaffna Spares,0212233445,Jaffna",
                "D005,Negombo Garage,0312233445,Negombo"
        );

        List<Dealer> dealers = dealerService.selectRandomDealers();

        long distinctCodes = dealers.stream()
                .map(Dealer::getDealerCode)
                .distinct()
                .count();

        assertEquals(dealers.size(), distinctCodes);
    }

    @Test
    void testSelectRandomDealersIsSortedByLocation() throws IOException {
        writeLines(
                "D001,Colombo Motors,0112233445,Colombo",
                "D002,Kandy Auto,0812233445,Kandy",
                "D003,Galle Wheels,0912233445,Galle",
                "D004,Jaffna Spares,0212233445,Jaffna",
                "D005,Negombo Garage,0312233445,Negombo",
                "D006,Matara Autos,0412233445,Matara"
        );

        List<Dealer> dealers = dealerService.selectRandomDealers();

        for (int i = 0; i < dealers.size() - 1; i++) {
            String currentLocation = dealers.get(i).getLocation().trim();
            String nextLocation = dealers.get(i + 1).getLocation().trim();

            assertTrue(
                    currentLocation.compareToIgnoreCase(nextLocation) <= 0,
                    "Dealers are not sorted by location: "
                            + currentLocation + " should come before/equal " + nextLocation
            );
        }
    }
}