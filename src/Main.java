import entity.booking.Booking;
import entity.hotel.Hotel;
import entity.payment.Payment;
import entity.payment.PaymentMethod;
import entity.payment.PaymentStatus;
import entity.review.Review;
import entity.room.*;
import service.AdminService;
import service.CustomerService;
import entity.user.Admin;
import entity.user.Customer;
import entity.user.UserDocument;
import service.ReadFromFileService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchFieldException {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = Hotel.getHotelInstance();
        Admin admin = Admin.getAdminInstance();
        AdminService adminService = AdminService.getAdminServiceInstance();
        CustomerService customerService = CustomerService.getCustomerServiceInstance();
        ReadFromFileService readFromFileService = ReadFromFileService.getReadFromFileService();

        // Incarcare customers
        hotel.getCustomerList().addAll(readFromFileService.readUsers());
        // Incarcare rooms (StandardRooms + PremiumRooms)
        List<StandardRoom> standardRooms = readFromFileService.readRooms("src/csv/StandardRooms.csv", StandardRoom.class);
        List<PremiumRoom> premiumRooms = readFromFileService.readRooms("src/csv/PremiumRooms.csv", PremiumRoom.class);
        hotel.getRoomList().addAll(Stream.concat(standardRooms.stream(), premiumRooms.stream()).toList());
        // Incarcare reviews
        hotel.getReviewList().addAll(readFromFileService.readReviews());

//        Date hardcodate
        Customer customer1 = new Customer("Mara", "Pop", UserDocument.ID, "Romania", "0735654755", "maraPop", "maraPop@123", "marapop@gmail.com");
        Customer customer2 = new Customer("Mihai", "Stoica", UserDocument.PASSPORT, "Romania", "0732251755", "mihaiStoica", "mihaiStoica@123", "mihaiStoica@gmail.com");
        Customer customer3 = new Customer("Andreea", "Ilie", UserDocument.ID, "Romania", "0755454721", "andreeaIlie", "andreeaIlie@123", "andreeaIlie@gmail.com");
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        hotel.getCustomerList().addAll(customerList);

        StandardRoom room1 = new StandardRoom(301, RoomType.SINGLE);
        StandardRoom room2 = new StandardRoom(302, RoomType.SINGLE);
        StandardRoom room3 = new StandardRoom(303, RoomType.SINGLE);
        StandardRoom room4 = new StandardRoom(304, RoomType.DOUBLE);
        StandardRoom room5 = new StandardRoom(305, RoomType.DOUBLE);
        StandardRoom room6 = new StandardRoom(306, RoomType.DOUBLE);
        PremiumRoom room7 = new PremiumRoom(307, RoomType.SINGLE);
        PremiumRoom room8 = new PremiumRoom(308, RoomType.SINGLE);
        PremiumRoom room9 = new PremiumRoom(309, RoomType.DOUBLE);
        PremiumRoom room10 = new PremiumRoom(310, RoomType.DOUBLE);
        List<Room> roomList = new ArrayList<>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);
        roomList.add(room6);
        roomList.add(room7);
        roomList.add(room8);
        roomList.add(room9);
        roomList.add(room10);
        hotel.getRoomList().addAll(roomList);

        Set<Room> roomSet1 = new HashSet<>();
        roomSet1.add(room1);
        roomSet1.add(room10);
        Booking booking1 = new Booking(customer1, roomSet1, LocalDate.of(2022, 4, 15), LocalDate.of(2022, 4, 18));
        for (Room room : roomSet1){
            room.setRoomStatus(RoomStatus.UNAVAILABLE);
        }
        hotel.getBookingList().add(booking1);
        customer1.getBookingSet().add(booking1);

        Payment payment1 = new Payment(1,booking1);
        payment1.setPaymentMethod(PaymentMethod.CARD);
        payment1.setPaymentStatus(PaymentStatus.PAID);
        payment1.setTime(LocalDateTime.now());
        hotel.getPaymentList().add(payment1);
        customer1.getPaymentSet().add(payment1);

        Set<Room> roomSet2 = new HashSet<>();
        roomSet2.add(room2);
        Booking booking2 = new Booking(customer1, roomSet2, LocalDate.of(2022, 4, 16), LocalDate.of(2022, 4, 17));
        room2.setRoomStatus(RoomStatus.UNAVAILABLE);
        hotel.getBookingList().add(booking2);
        customer1.getBookingSet().add(booking2);
        Payment payment2 = new Payment(2, booking2);
        hotel.getPaymentList().add(payment2);
        customer1.getPaymentSet().add(payment2);

        Set<Room> roomSet3 = new HashSet<>();
        roomSet3.add(room3);
        Booking booking3 = new Booking(customer2, roomSet3, LocalDate.of(2022, 5, 10), LocalDate.of(2022, 5, 12));
        room3.setRoomStatus(RoomStatus.UNAVAILABLE);
        hotel.getBookingList().add(booking3);
        customer2.getBookingSet().add(booking3);

        Payment payment3 = new Payment(3,booking3);
        payment3.setPaymentMethod(PaymentMethod.CARD);
        payment3.setPaymentStatus(PaymentStatus.PAID);
        payment3.setTime(LocalDateTime.now());
        Customer customerY = booking3.getCustomer();
        customerY.getPaymentSet().add(payment3);
        hotel.getPaymentList().add(payment3);

        // Menu

        while(true) {
            int option;
            while (true){
                try {
                    System.out.println("\n\t -------------------- LOGIN --------------------");
                    System.out.println("\t Choose your account type (1/2/3/4):");
                    System.out.println("\t 1. Admin");
                    System.out.println("\t 2. Customer");
                    System.out.println("\t 3. I don't have an account. I want to register.");
                    System.out.println("\t 4. Exit!");
                    option = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                    System.out.println("Try again!");
                }
            }

            if (option == 1){
                adminService.logIn();
                adminService.logOut();
            } else if (option == 2){
                customerService.logIn();
                customerService.logOut();
            } else if (option == 3){
                //Register
                adminService.addCustomer();
                customerService.logIn();
                customerService.logOut();
            } else if (option == 4) {
                break;
            } else {
                System.out.println("Failed to LogIn! Try again!");
            }
        }
    }
}

