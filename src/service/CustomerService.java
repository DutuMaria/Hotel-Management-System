package service;

import entity.booking.Booking;
import entity.hotel.Hotel;
import entity.payment.Payment;
import entity.payment.PaymentMethod;
import entity.payment.PaymentStatus;
import entity.review.Review;
import entity.room.Room;
import entity.room.RoomStatus;
import entity.user.Customer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerService implements ServiceInterface {
    private static CustomerService customerService;
    private static AuditService auditService;

    private static Hotel hotel;

    private static WriteToFileService writeToFileService;

    private CustomerService(){

        auditService = AuditService.getAuditService();
        hotel = Hotel.getHotelInstance();
        writeToFileService = WriteToFileService.getWriteToFileService();
    }

    public static CustomerService getCustomerServiceInstance(){
        if (customerService == null)
            customerService = new CustomerService();
        return customerService;
    }

    public void viewProfile(String username) throws IOException {
        auditService.writeAction("viewProfile");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                System.out.println(c);
                break;
            }
        }
    }

    public void viewHotelServices() throws IOException {
        auditService.writeAction("viewHotelServices");
        System.out.println(hotel.getHotelServices());
    }

    public void viewAvailableRooms() throws IOException {
        auditService.writeAction("viewAvailableRooms");
        System.out.println(hotel.getRoomList()
                .stream()
                .filter(room -> room.getRoomStatus() == RoomStatus.AVAILABLE)
                .collect(Collectors.toList()));
    }


    public void createBooking(String username) throws IOException {
        auditService.writeAction("createBooking");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type your arrival date (format - yyyy-mm-dd): ");
                LocalDate arrivalDate = LocalDate.parse(scanner.nextLine());
                System.out.println("Type your departure date (format - yyyy-mm-dd): ");
                LocalDate deaprtureDate =  LocalDate.parse(scanner.nextLine());

                System.out.println("How many rooms would you like to book?");
                int nrOfRoomsToBook = Integer.parseInt(scanner.nextLine());
                int roomNumber;
//                int roomModel; // Premium/Stnadard
//                int roomType; // Single/Double
                Set<Room> roomSet = new HashSet<>();
                for(int i = 1; i <= nrOfRoomsToBook; i++){
                    System.out.println("Available rooms: ");
                    this.viewAvailableRooms();
                    System.out.println("Type a room number: ");
                    roomNumber = Integer.parseInt(scanner.nextLine());
                    for (Room room : hotel.getRoomList()){
                        if (room.getRoomNumber().equals(roomNumber) && room.getRoomStatus() == RoomStatus.AVAILABLE){
                            roomSet.add(room);
                            room.setRoomStatus(RoomStatus.UNAVAILABLE);
                        }
                    }
                }

                if (roomSet.size() == nrOfRoomsToBook){
                    Booking booking = new Booking(c, roomSet, arrivalDate, deaprtureDate);
                    for (Room room: roomSet){
                        room.setRoomStatus(RoomStatus.UNAVAILABLE);
                    }
                    hotel.getBookingList().add(booking);
                    Payment payment = new Payment(booking.getId(), booking);
                    hotel.getPaymentList().add(payment);
                    c.getBookingSet().add(booking);
                    c.getPaymentSet().add(payment);
                    System.out.println("Your booking has been made successfully!");
                    payBooking(payment.getId(), username);
                } else {
                    for(Room room : roomSet){
                        room.setRoomStatus(RoomStatus.AVAILABLE);
                    }
                    System.out.println("Failed booking!");
                }
//                for(int i = 1; i <= nrOfRoomsToBook; i++){
//                    System.out.println("If you want room" + i + " to be Standard => type 0");
//                    System.out.println("If you want room" + i + " to be Premium => type 1");
//                    roomModel = Integer.parseInt(scanner.nextLine());
//                    System.out.println("If you want room" + i + " to be Single => type 1");
//                    System.out.println("If you want room" + i + " to be Double => type 2");
//                    roomType = Integer.parseInt(scanner.nextLine());
//                }
                break;
            }
        }
    }

    public void payBooking(int id, String username) throws IOException {
        auditService.writeAction("payBooking");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                Scanner scanner = new Scanner(System.in);
                for (Payment payment: c.getPaymentSet()){
                    if (payment.getId().equals(id)){
                        if (payment.getPaymentStatus() == PaymentStatus.UNPAID){
                            System.out.println("Payment Number: " + id);
                            System.out.println("Total: " + payment.getTotalPrice() + "$");
                            System.out.println("Choose payment method: ");
                            System.out.println("\tType 0 for CASH");
                            System.out.println("\tType 1 for CARD");
                            System.out.println("\tType 2 for PAY LATER");
                            int paymentMethod = Integer.parseInt(scanner.nextLine());
                            switch (paymentMethod){
                                case (0):
                                    payment.setPaymentMethod(PaymentMethod.CASH);
                                    payment.setPaymentStatus(PaymentStatus.PAID);
                                    payment.setTime(LocalDateTime.now());
                                    System.out.println("Payment complete!");
                                    return;
                                case(1):
                                    payment.setPaymentMethod(PaymentMethod.CARD);
                                    payment.setPaymentStatus(PaymentStatus.PAID);
                                    payment.setTime(LocalDateTime.now());
                                    System.out.println("Payment complete!");
                                    return;
                                case(2):
                                    return;
                            }
                        } else {
                            System.out.println("Payment already made!");
                            return;
                        }
                    }
                }
                System.out.println("This booking doesn't exist.");
                break;
            }
        }
    }

    public void reviewHotel(String username) throws IOException {
        auditService.writeAction("reviewHotel");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                Scanner scanner = new Scanner(System.in);
                for (Booking booking : hotel.getBookingList()){
                    if (booking.getCustomer() == c){
                        System.out.println("Rate the hotel (type 1/2/3/4/5)");
                        int stars = Integer.parseInt(scanner.nextLine());
                        System.out.println("Rate the service (type 1/2/3/4/5)");
                        int serviceRaiting = Integer.parseInt(scanner.nextLine());
                        System.out.println("Rate the rooms (type 1/2/3/4/5)");
                        int roomsRaiting = Integer.parseInt(scanner.nextLine());
                        System.out.println("Rate the cleanliness (type 1/2/3/4/5)");
                        int cleanlinessRaiting = Integer.parseInt(scanner.nextLine());
                        System.out.println("Rate the sleepQuality (type 1/2/3/4/5)");
                        int sleepQualityRaiting = Integer.parseInt(scanner.nextLine());
                        System.out.println("Write a review");
                        String description  = scanner.nextLine();
                        Review review = new Review(stars, serviceRaiting, roomsRaiting, cleanlinessRaiting, sleepQualityRaiting, description);
                        hotel.getReviewList().add(review);
                        System.out.println("Thank you for reviewing!");
                        writeToFileService.writeReview(review);
                        return;
                    }
                }
                System.out.println("You can't review this hotel unless you have a booking!\n");
                break;
            }
        }
    }

    public void checkOut(int idBooking, String username) throws IOException {
        auditService.writeAction("checkOut");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                boolean wrongId = true;
                for (Booking booking : c.getBookingSet()){
                    if (booking.getId().equals(idBooking)){
                        wrongId = false;
                        for (Payment payment : c.getPaymentSet()){
                            if (payment.getPaymentStatus() == PaymentStatus.PAID){
                                for (Room room:booking.getRoomSet()){
                                    room.setRoomStatus(RoomStatus.AVAILABLE);
                                }
                                System.out.println("Check-out complete!");
                                return;
                            } else {
                                System.out.println("You need to pay this booking first!");
                                payBooking(idBooking, username);
                                checkOut(idBooking, username);
                            }
                        }
                    }
                }
                if (wrongId){
                    System.out.println("This booking doesn't exist!");
                }
                break;
            }
        }
    }

    public void changeUsername(String username) throws IOException {
        auditService.writeAction("changeUsername");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type your password: ");
                String password = scanner.nextLine();
                boolean usernameChanged = false;

                if (password.equals(c.getPassword())){
                    while (!usernameChanged){
                        System.out.println("Type new username: ");
                        String newUsername = scanner.nextLine();
                        if (!newUsername.equals(c.getUsername())){
                            c.setUsername(newUsername);
                            usernameChanged = true;
                            System.out.println("Username has been successfully changed!");
                        } else {
                            System.out.println("New username cannot be the same as your old username.");
                        }
                    }
                } else {
                    System.out.println("Incorrect password! You can't change your username.");
                }
                break;
            }
        }
    }

    public void changePassword(String username) throws IOException {
        auditService.writeAction("changePassword");
        Customer c;

        for (var customer : hotel.getCustomerList()){
            if (customer.getUsername().equals(username)){
                c = customer;
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type current password: ");
                String currentPassword = scanner.nextLine();
                boolean passwordChanged = false;

                if (currentPassword.equals(c.getPassword())){
                    while (!passwordChanged){
                        System.out.println("Type new password: ");
                        String newPassword = scanner.nextLine();
                        if (!newPassword.equals(currentPassword)){
                            c.setPassword(newPassword);
                            passwordChanged = true;
                            System.out.println("Password has been successfully changed!");
                        } else {
                            System.out.println("New password cannot be the same as your old password.");
                        }
                    }
                } else {
                    System.out.println("Incorrect password! You can't change your password.");
                }
                break;
            }
        }
    }

    @Override
    public void logIn() throws IOException {
        auditService.writeAction("logIn");
        int nrOfAttempts = 5;
        while (nrOfAttempts > 0){
            System.out.println("\t Username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            System.out.println("\t Password: ");
            String password = scanner.nextLine();

            for(Customer customer:hotel.getCustomerList()){
                if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())){
                    showFunctionalities(username);
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
    public void showFunctionalities(String username) throws IOException {
        auditService.writeAction("showFunctionalities");
        int option = 0;
        while (option != 9){
            Scanner scanner = new Scanner(System.in);

            while (true){
                try {
                    System.out.println("\n\t-------------------- Customer Functionalities ---------------------\n");
                    System.out.println("\t Choose a functionality (1/2/3/4/5/6/7/8/9):");
                    System.out.println("\t 1. View profile.");
                    System.out.println("\t 2. View hotel services.");
                    System.out.println("\t 3. Create a booking.");
                    System.out.println("\t 4. Pay a booking.");
                    System.out.println("\t 5. Change your username.");
                    System.out.println("\t 6. Change your password.");
                    System.out.println("\t 7. Review Hotel.");
                    System.out.println("\t 8. Check-out.");
                    System.out.println("\t 9. Exit.\n");
                    option = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Try again!");
                }
            }

            switch (option){
                case (1):
                    viewProfile(username);
                    break;
                case (2):
                    viewHotelServices();
                    break;
                case (3):
                    createBooking(username);
                    break;
                case (4):
                    int id;
                    while (true){
                        try {
                            System.out.println("Type a booking id (number): ");
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    payBooking(id, username);
                    break;
                case (5):
                    changeUsername(username);
                    break;
                case (6):
                    changePassword(username);
                    break;
                case (7):
                    reviewHotel(username);
                    break;
                case (8):
                    while (true){
                        try {
                            System.out.println("Type a booking id (number): ");
                            id = Integer.parseInt(scanner.nextLine());
                            break;
                        } catch (NumberFormatException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    checkOut(id, username);
                    break;
            }
        }
    }
}

