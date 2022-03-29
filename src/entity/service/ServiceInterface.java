package entity.service;


import entity.user.User;

public interface ServiceInterface {
    void logIn();
    void showFunctionalities(User user);
    default void logOut(){
        System.out.println("Goodbye!");
    }
}
