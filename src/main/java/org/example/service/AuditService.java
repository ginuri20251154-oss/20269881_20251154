package org.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {

    private static final String AUDIT_FILE =
            "audit_log.txt";

    public void writeLog(String action,
                         String details) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd HH:mm:ss"
                );

        String timestamp =
                LocalDateTime.now().format(formatter);

        String logEntry =
                timestamp
                        + " | "
                        + action
                        + " | "
                        + details;

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(
                                     AUDIT_FILE,
                                     true
                             )
                     )) {

            writer.write(logEntry);
            writer.newLine();

        } catch (IOException e) {

            System.out.println(
                    "Unable to write to audit log: "
                            + e.getMessage()
            );
        }
    }
}
