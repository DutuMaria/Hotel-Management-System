package service;

public interface ServiceInterface {
    void logIn();
    void showFunctionalities(String username);
    default void logOut(){
        System.out.println("Goodbye!");
    }
}
