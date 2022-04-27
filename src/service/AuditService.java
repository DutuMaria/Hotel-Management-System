package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    public static AuditService auditService;

    private AuditService() {}

    public static AuditService getAuditService() {
        if (auditService == null)
            auditService = new AuditService();
        return auditService;
    }

    public void writeAction(String action) throws IOException {
        try (FileWriter fileWriter = new FileWriter("src/csv/Audit.csv", true)) {
            File file = new File("src/csv/Audit.csv");

            if (file.length() == 0) {
                fileWriter.append("NumeActiune").append(",").append("Timestamp").append("\n");
            }

            LocalDateTime date = LocalDateTime.now();
            fileWriter.append(action).append(",").append(String.valueOf(date)).append("\n");
            fileWriter.flush();

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("\n\tException: " + e.getMessage());
        }
    }
}
