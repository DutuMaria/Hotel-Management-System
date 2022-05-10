package service;

import java.io.IOException;

import static service.AuditService.auditService;

public interface ServiceInterface {
    void logIn() throws IOException;
    void showFunctionalities(String username) throws IOException;
    default void logOut() throws IOException {
        auditService.writeAction("logOut");
        System.out.println("Goodbye!");
    }
}
