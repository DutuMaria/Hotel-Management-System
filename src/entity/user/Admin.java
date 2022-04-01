package entity.user;

import entity.hotel.Hotel;
import entity.room.*;

import java.util.Objects;
import java.util.Scanner;

public class Admin extends User {
    private static Admin admin;
    private static Hotel hotel;

    private Admin() {
        super(1, "Florentina", "Constantinescu","admin", "admin1234", "admin1234@gmail.com");
        hotel = Hotel.getHotelInstance();
    }

    public static Admin getAdminInstance(){
        if(admin == null){
            admin = new Admin();
        }
        return admin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public String getUserType() {
        return "Admin";
    }

    public void addRoom(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Complete the following information in order to add a room!");
        System.out.println("If you want to add a Standard Room => type 1");
        System.out.println("If you want to add a Premium Room => type 2");
        String roomModelNr = scanner.nextLine();
        System.out.println("If room is Single => type 1");
        System.out.println("If room is Double => type 2");
        String roomTypeNr = scanner.nextLine();

        if (Objects.equals(roomModelNr, "1")) {
            if (Objects.equals(roomTypeNr, "1")) {
                StandardRoom standardRoom = new StandardRoom(RoomType.SINGLE);
                hotel.getRoomList().add(standardRoom);
            } else if (Objects.equals(roomTypeNr, "2")){
                StandardRoom standardRoom = new StandardRoom(RoomType.DOUBLE);
                hotel.getRoomList().add(standardRoom);
            } else {
                System.out.println("Wrong input for (Single/Double) room!");
            }
        } else if (Objects.equals(roomModelNr, "2")){
            if (Objects.equals(roomTypeNr, "1")) {
                PremiumRoom premiumRoom = new PremiumRoom(RoomType.SINGLE);
                hotel.getRoomList().add(premiumRoom);
            } else if (Objects.equals(roomTypeNr, "2")){
                PremiumRoom premiumRoom = new PremiumRoom(RoomType.DOUBLE);
                hotel.getRoomList().add(premiumRoom);
            } else {
                System.out.println("Wrong input for (Single/Double) room!");
            }
        } else {
            System.out.println("Wrong input for (Standard/Premium) room!");
        }
    }

    public void addCustomer(){
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

    public void viewAllBookings(){ System.out.println(hotel.getBookingList()); }

    public void viewAllRooms(){
        RoomTypeComparator roomTypeComparator = new RoomTypeComparator();
        hotel.getRoomList().sort(roomTypeComparator);
        System.out.println(hotel.getRoomList());
    }

    public void viewAllPayments(){ System.out.println(hotel.getPaymentList()); }

    public void viewAllCustomers(){
        System.out.println(hotel.getCustomerList());
    }

    public void changeRoomStatus(int roomNumber, RoomStatus roomStatus){
        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomStatus() != roomStatus){
                room.setRoomStatus(roomStatus);
            }
        }
    }

    public void changeRoomType(int roomNumber, RoomType roomType){
        for (Room room :hotel.getRoomList()){
            if (room.getRoomNumber() == roomNumber && room.getRoomType() != roomType){
                room.setRoomType(roomType);
            }
        }
    }

    public void deleteRoom(int roomNumber){
        // TODO: verificare daca aceasta camera are booking-uri asociate => in viitor => schimbi respectivul booking, daca este posibil
        //                                                               => in prezent => nu poti sterge camera
        hotel.getRoomList().removeIf(x-> x.getRoomNumber() == roomNumber);
    }
}

