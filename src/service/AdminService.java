package service;

import entity.hotel.Hotel;
import entity.room.*;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.UserDocument;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

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
        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(firstName, lastName, userDocument, address, telephone, username, password, email);
        hotel.getCustomerList().add(customer);
    }

    public void viewAllBookings() throws IOException {
        auditService.writeAction("viewAllBookings");
        System.out.println(hotel.getBookingList());
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

    public void viewAllCustomers() throws IOException {
        auditService.writeAction("viewAllCustomers");
        System.out.println(hotel.getCustomerList());
    }

    public void changeRoomStatus(int roomNumber, RoomStatus roomStatus) throws IOException {
        auditService.writeAction("changeRoomStatus");

        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomStatus() != roomStatus){
                room.setRoomStatus(roomStatus);
                break;
            }
        }
    }

    public void changeRoomType(int roomNumber, RoomType roomType) throws IOException {
        auditService.writeAction("changeRoomType");

        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomType() != roomType){
                room.setRoomType(roomType);
                break;
            }
        }
    }

    public void deleteRoom(int roomNumber) throws IOException {
        auditService.writeAction("deleteRoom");
        // TODO: verificare daca aceasta camera are booking-uri asociate => in viitor => schimbi respectivul booking, daca este posibil
        //                                                               => in prezent => nu poti sterge camera
        hotel.getRoomList().removeIf(x-> x.getRoomNumber() == roomNumber);
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
        auditService.writeAction("menu");
        int option = 0;
        while (option != 10){
            System.out.println("\n\t-------------------- Admin Functionalities ---------------------\n");
            System.out.println("\t Choose a functionality (1/2/3/4/5/6/7/8/9/10):");
            System.out.println("\t 1. View all bookings.");
            System.out.println("\t 2. View all payments.");
            System.out.println("\t 3. View all customers.");
            System.out.println("\t 4. View all rooms.");
            System.out.println("\t 5. Add customer.");
            System.out.println("\t 6. Add room.");
            System.out.println("\t 7. Delete room.");
            System.out.println("\t 8. Change room status.");
            System.out.println("\t 9. Change room type.");
            System.out.println("\t 10. Exit.\n");

            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();

            switch (option){
                case (1):
                    viewAllBookings();
                    break;
                case (2):
                    viewAllPayments();
                    break;
                case (3):
                    viewAllCustomers();
                    break;
                case (4):
                    viewAllRooms();
                    break;
                case (5):
                    addCustomer();
                    break;
                case (6):
                    addRoom();
                    break;
                case (7):
                    int roomNr;
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    deleteRoom(roomNr);
                    break;
                case (8):
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    int roomStatus;
                    System.out.println("\t If you want to make the room UNAVAILABLE => type 0");
                    System.out.println("\t If you want to make the room AVAILABLE => type 1 ");
                    roomStatus = scanner.nextInt();
                    if (roomStatus == 0){
                        changeRoomStatus(roomNr, RoomStatus.UNAVAILABLE);
                    }
                    if (roomStatus == 1){
                        changeRoomStatus(roomNr, RoomStatus.AVAILABLE);
                    }
                    break;
                case (9):
                    System.out.println("\t Enter room number: ");
                    roomNr = scanner.nextInt();
                    int roomType;
                    System.out.println("\t If you want to make the room Single => type 1");
                    System.out.println("\t If you want to make the room Double => type 2");
                    roomType = scanner.nextInt();
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

