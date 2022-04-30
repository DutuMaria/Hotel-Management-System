package service;

import entity.booking.Booking;
import entity.hotel.Hotel;
import entity.payment.PaymentStatus;
import entity.room.*;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.UserDocument;
import exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdminService implements ServiceInterface {
    private static AdminService adminService;
    private static Hotel hotel;
    private static AuditService auditService;

    private AdminService(){
        hotel = Hotel.getHotelInstance();
        auditService = AuditService.getAuditService();
    }

    public static AdminService getAdminServiceInstance(){
        if (adminService == null)
            adminService = new AdminService();
        return adminService;
    }

    public void  uniqueUsername(String username) throws InvalidUsernameException {
        Optional<Customer> any = hotel.getCustomerList()
                .stream()
                .filter(customer -> Objects.equals(customer.getUsername(), username))
                .findAny();

        if (any.isPresent()){
            throw new InvalidUsernameException("Username " + username + " is already taken!");
        }
    }
    public void passwordValidator(String password) throws InvalidPasswordException {
        boolean isValid = password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{5,}$");
        if (!isValid) {
            throw new InvalidPasswordException("Invalid password! Password must contain at least one lowercase, one uppercase, one special character, one digit and must have a length of at least 5!");
        }
    }

    public void addRoom() throws IOException {
        auditService.writeAction("addRoom");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Complete the following information in order to add a room!");
        System.out.println("Type a room number: ");
        Integer roomNumber = Integer.parseInt(scanner.nextLine());
        System.out.println("If you want to add a Standard Room => type 1");
        System.out.println("If you want to add a Premium Room => type 2");
        String roomModelNr = scanner.nextLine();
        System.out.println("If room is Single => type 1");
        System.out.println("If room is Double => type 2");
        String roomTypeNr = scanner.nextLine();

        if (Objects.equals(roomModelNr, "1")) {
            if (Objects.equals(roomTypeNr, "1")) {
                StandardRoom standardRoom = new StandardRoom(roomNumber, RoomType.SINGLE);
                hotel.getRoomList().add(standardRoom);
            } else if (Objects.equals(roomTypeNr, "2")){
                StandardRoom standardRoom = new StandardRoom(roomNumber, RoomType.DOUBLE);
                hotel.getRoomList().add(standardRoom);
            } else {
                System.out.println("Wrong input for (Single/Double) room!");
            }
        } else if (Objects.equals(roomModelNr, "2")){
            if (Objects.equals(roomTypeNr, "1")) {
                PremiumRoom premiumRoom = new PremiumRoom(roomNumber, RoomType.SINGLE);
                hotel.getRoomList().add(premiumRoom);
            } else if (Objects.equals(roomTypeNr, "2")){
                PremiumRoom premiumRoom = new PremiumRoom(roomNumber, RoomType.DOUBLE);
                hotel.getRoomList().add(premiumRoom);
            } else {
                System.out.println("Wrong input for (Single/Double) room!");
            }
        } else {
            System.out.println("Wrong input for (Standard/Premium) room!");
        }
    }

    public void addCustomer() throws IOException {
        auditService.writeAction("addCustomer");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Complete the following information in order to register!");
        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.println("If you register with ID => type 1");
        System.out.println("If you register with Passport => type 2");
        String userDocumentNr = scanner.nextLine();
        UserDocument userDocument;

        switch (userDocumentNr){
            case ("1"):
                userDocument = UserDocument.ID;
                break;
            case ("2"):
                userDocument = UserDocument.PASSPORT;
                break;
            default:
                System.out.println("Wrong input for userDocument");
                //userDocument = UserDocument.ID;
                return;
        }

        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Telephone: ");
        String telephone = scanner.nextLine();

        String username;
        while (true) {
            try {
                System.out.println("Username: ");
                username = scanner.nextLine();
                uniqueUsername(username);
                break;
            } catch (InvalidUsernameException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
        }

        String password;
        while (true) {
            try {
                System.out.println("Password: ");
                password = scanner.nextLine();
                passwordValidator(password);
                break;
            } catch (InvalidPasswordException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
        }

        System.out.println("Email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(firstName, lastName, userDocument, address, telephone, username, password, email);
        hotel.getCustomerList().add(customer);
    }

    public void viewAllBookings() throws IOException {
        auditService.writeAction("viewAllBookings");
        System.out.println(hotel.getBookingList());
    }

    public void viewBookingsForAGivenPeriod(LocalDate startDate, LocalDate endDate) throws IOException {
        auditService.writeAction("viewBookingsForAGivenPeriod");
        Consumer<Booking> bookingConsumer = System.out::println; // method reference
        Predicate<Booking> bookingPredicate = booking ->  (startDate.isBefore(booking.getArrival()) || startDate.isEqual(booking.getArrival())) && (endDate.isAfter(booking.getDeparture()) || endDate.isEqual(booking.getDeparture()));
        for (Booking booking : hotel.getBookingList()) {
            if (bookingPredicate.test(booking)){
                bookingConsumer.accept(booking);
            }
        }
    }

    public void viewAllRooms() throws IOException {
        auditService.writeAction("viewAllRooms");
        RoomTypeComparator roomTypeComparator = new RoomTypeComparator();
        hotel.getRoomList().sort(roomTypeComparator);
        System.out.println(hotel.getRoomList());
    }

    public void viewAllPayments() throws IOException {
        auditService.writeAction("viewAllPayments");
        System.out.println(hotel.getPaymentList());
    }

    public void viewUnpaidPayments() throws IOException {
        auditService.writeAction("viewUnpaidPayments");
        Hotel hotel = Hotel.getHotelInstance();
        System.out.println(hotel.getPaymentList()
                .stream()
                .filter(payment -> payment.getPaymentStatus() == PaymentStatus.UNPAID)
                .map(payment -> "Payment id: " + payment.getId().toString() + " => " + payment.getTotalPrice().toString())
                .collect(Collectors.toList()));
    }

    public void viewAllCustomers() throws IOException {
        auditService.writeAction("viewAllCustomers");
        System.out.println(hotel.getCustomerList());
    }

    public void changeRoomStatus(int roomNumber, RoomStatus roomStatus) throws IOException {
        auditService.writeAction("changeRoomStatus");

        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomStatus() != roomStatus){
                room.setRoomStatus(roomStatus);
                System.out.println("Room " + roomNumber + " => status changed to " + roomStatus + "!");
                return;
            }
        }
        System.out.println("Room status is already " + roomStatus + "!");
    }

    public void changeRoomType(int roomNumber, RoomType roomType) throws IOException {
        auditService.writeAction("changeRoomType");

        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomType() != roomType){
                room.setRoomType(roomType);
                System.out.println("Room type successfully changed!");
                return;
            }
        }
        System.out.println("Room type is already " + roomType + "!");
    }

    public void deleteRoom(int roomNumber) throws IOException {
        auditService.writeAction("deleteRoom");
        // TODO: verificare daca aceasta camera are booking-uri asociate => in viitor => schimbi respectivul booking, daca este posibil
        //                                                               => in prezent => nu poti sterge camera
        hotel.getRoomList().removeIf(room -> room.getRoomNumber() == roomNumber);
        System.out.println("Room successfully deleted!");
    }

    public void checkIfRoomExists(int roomNr) throws RoomDoesntExistException {
        Optional<Room> any = hotel.getRoomList().stream()
                .filter(room -> room.getRoomNumber() == roomNr)
                .findAny();
        if (any.isEmpty()){
            throw new RoomDoesntExistException("Room number " + roomNr + " doesn't exist!");
        }
    }

    @Override
    public void logIn() throws IOException {
        auditService.writeAction("logIn");
        Admin admin = Admin.getAdminInstance();
        int nrOfAttempts = 5;
        while (nrOfAttempts > 0){
            System.out.println("\t Username: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            System.out.println("\t Password: ");
            String password = scanner.nextLine();

            if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())){
                showFunctionalities(username);
                return;
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
        while (option != 12){
            Scanner scanner = new Scanner(System.in);

            while (true){
                try {
                    System.out.println("\n\t-------------------- Admin Functionalities ---------------------\n");
                    System.out.println("\t Choose a functionality (1/2/3/4/5/6/7/8/9/10/11/12):");
                    System.out.println("\t 1. View all bookings.");
                    System.out.println("\t 2. View bookings for a given period of time.");
                    System.out.println("\t 3. View all payments.");
                    System.out.println("\t 4. View unpaid payments.");
                    System.out.println("\t 5. View all customers.");
                    System.out.println("\t 6. View all rooms.");
                    System.out.println("\t 7. Add customer.");
                    System.out.println("\t 8. Add room.");
                    System.out.println("\t 9. Delete room.");
                    System.out.println("\t 10. Change room status.");
                    System.out.println("\t 11. Change room type.");
                    System.out.println("\t 12. Exit.\n");
                    option = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Try again!");
                }
            }

            switch (option){
                case (1):
                    viewAllBookings();
                    break;
                case (2):
                    LocalDate startDate, endDate;
                    while (true) {
                        try {
                            System.out.println("\t Enter start date (format - yyyy-mm-dd): ");
                            startDate = LocalDate.parse(scanner.nextLine());
                            break;
                        } catch (DateTimeParseException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    while (true) {
                        try {
                            System.out.println("\t Enter end date (format - yyyy-mm-dd): ");
                            endDate =  LocalDate.parse(scanner.nextLine());
                            break;
                        } catch (DateTimeParseException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    viewBookingsForAGivenPeriod(startDate, endDate);
                    break;
                case (3):
                    viewAllPayments();
                    break;
                case (4):
                    viewUnpaidPayments();
                    break;
                case (5):
                    viewAllCustomers();
                    break;
                case (6):
                    viewAllRooms();
                    break;
                case (7):
                    addCustomer();
                    break;
                case (8):
                    addRoom();
                    break;
                case (9):
                    int roomNr;
                    while (true) {
                        try {
                            System.out.println("\t Enter room number: ");
                            roomNr = Integer.parseInt(scanner.nextLine());
                            checkIfRoomExists(roomNr);
                            break;
                        } catch (RoomDoesntExistException | NumberFormatException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    deleteRoom(roomNr);
                    break;
                case (10):
                    while (true) {
                        try {
                            System.out.println("\t Enter room number: ");
                            roomNr = Integer.parseInt(scanner.nextLine());
                            checkIfRoomExists(roomNr);
                            break;
                        } catch (RoomDoesntExistException | NumberFormatException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    int roomStatus;
                    while (true) {
                        try {
                            System.out.println("\t If you want to make the room UNAVAILABLE => type 0");
                            System.out.println("\t If you want to make the room AVAILABLE => type 1 ");
                            roomStatus = Integer.parseInt(scanner.nextLine());
                            if (roomStatus != 1 && roomStatus != 0) {
                                throw new RoomAvailablilityException("Enter 0 or 1 for room availability!");
                            }
                            break;
                        } catch (RoomAvailablilityException | NumberFormatException  e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    if (roomStatus == 0){
                        changeRoomStatus(roomNr, RoomStatus.UNAVAILABLE);
                    }
                    if (roomStatus == 1){
                        changeRoomStatus(roomNr, RoomStatus.AVAILABLE);
                    }
                    break;
                case (11):
                    while (true) {
                        try {
                            System.out.println("\t Enter room number: ");
                            roomNr = Integer.parseInt(scanner.nextLine());
                            checkIfRoomExists(roomNr);
                            break;
                        } catch (RoomDoesntExistException | NumberFormatException e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    int roomType;
                    while (true) {
                        try {
                            System.out.println("\t If you want to make the room Single => type 1");
                            System.out.println("\t If you want to make the room Double => type 2");
                            roomType = Integer.parseInt(scanner.nextLine());
                            if (roomType != 1 && roomType != 2) {
                                throw new RoomTypeException("Enter 1 or 2 for room type!");
                            }
                            break;
                        } catch (RoomTypeException | NumberFormatException  e){
                            System.out.println(e.getMessage());
                            System.out.println("Try again!");
                        }
                    }

                    if (roomType == 1){
                        changeRoomType(roomNr, RoomType.SINGLE);
                    }
                    if (roomType == 2){
                        changeRoomType(roomNr, RoomType.DOUBLE);
                    }
                    break;
            }
        }
    }
}

