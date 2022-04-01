package entity.service;

import entity.hotel.Hotel;
import entity.user.Customer;
import entity.user.User;

import java.util.Scanner;

public class CustomerService implements ServiceInterface{
    private static CustomerService customerService;

    private CustomerService(){}

    public static CustomerService getCustomerServiceInstance(){
        if ( customerService == null)
            customerService = new CustomerService();
        return customerService;
    }

    @Override
    public void logIn() {
        Hotel hotel = Hotel.getHotelInstance();
        int nrOfAttempts = 5;
        while (nrOfAttempts > 0){
            System.out.println("\t Username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            System.out.println("\t Password: ");
            String password = scanner.nextLine();

            for(Customer customer:hotel.getCustomerList()){
                if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())){
                    showFunctionalities(customer);
                    return;
                }
            }

            nrOfAttempts--;
            if (nrOfAttempts == 0){
                System.out.println("Username or Password incorrect! Try again later!");
            } else{
                System.out.println("Username or Password incorrect! Try again! You have " + nrOfAttempts + " attempts left.");
            }
        }
    }

    @Override
    public void showFunctionalities(User user) {
        int option = 0;
        while (option != 8){
            System.out.println("\n\t-------------------- Customer Functionalities ---------------------\n");
            System.out.println("\t Choose a functionality (1/2/3/4/5/6):");
            System.out.println("\t 1. View profile.");
            System.out.println("\t 2. View hotel services.");
            System.out.println("\t 3. Create a booking.");
            System.out.println("\t 4. Pay a booking.");
            System.out.println("\t 5. Change your password.");
            System.out.println("\t 6. Review Hotel.");
            System.out.println("\t 7. Check-out.");
            System.out.println("\t 8. Exit.\n");

            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();

            switch (option){
                case (1):
                    ((Customer)user).viewProfile();
                    break;
                case (2):
                    ((Customer)user).viewHotelServices();
                    break;
                case (3):
                    ((Customer)user).createBooking();
                    break;
                case (4):
                    System.out.println("Type a booking id (number): ");
                    int id = scanner.nextInt();
                    ((Customer)user).payBooking(id);
                    break;
                case (5):
                    ((Customer)user).changePassword();
                    break;
                case (6):
                    ((Customer)user).reviewHotel();
                    break;
                case (7):
                    System.out.println("Type a booking id (number): ");
                    id = scanner.nextInt();
                    ((Customer)user).checkOut(id);
                    break;
            }
        }
    }
}
