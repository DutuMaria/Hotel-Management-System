package entity.user;

import entity.booking.Booking;
import entity.hotel.Hotel;
import entity.payment.Payment;
import entity.payment.PaymentMethod;
import entity.payment.PaymentStatus;
import entity.room.Room;
import entity.room.RoomStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Customer extends User  {
    private static Integer count = 2;
    private UserDocument userDocument;
    private String address;
    private String telephone;
    Set<Booking> bookingSet;
    Set<Payment> paymentSet;

    public Customer(String firstName, String lastName, UserDocument userDocument, String address, String telephone, String username, String password, String email) {
        super(count, firstName, lastName, username, password, email);
        this.userDocument = userDocument;
        this.address = address;
        this.telephone = telephone;
        bookingSet = new HashSet<>();
        paymentSet = new HashSet<>();
        count++;
    }

    public UserDocument getUserDocument() {
        return userDocument;
    }

    public void setUserDocument(UserDocument userDocument) {
        this.userDocument = userDocument;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Set<Booking> getBookingSet() {
        return bookingSet;
    }

    public void setBookingSet(Set<Booking> bookingSet) {
        this.bookingSet = bookingSet;
    }

    public Set<Payment> getPaymentSet() {
        return paymentSet;
    }

    public void setPaymentSet(Set<Payment> paymentSet) {
        this.paymentSet = paymentSet;
    }


    @Override
    public String getUserType() {
        return "Customer";
    }

    public void viewProfile(){
        System.out.println(this);
    }

    public void viewHotelServices(){
        Hotel hotel = Hotel.getHotelInstance();
        System.out.println(hotel.getHotelServices());
    }

    public void viewAvailableRooms(){
        Hotel hotel = Hotel.getHotelInstance();
        System.out.println(hotel.getRoomList().stream().filter(x -> x.getRoomStatus() == RoomStatus.AVAILABLE).toList());
    }


    public void createBooking(){
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = Hotel.getHotelInstance();

        System.out.println("Type your arrival date (format - yyyy-mm-dd): ");
        LocalDate arrivalDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Type your departure date (format - yyyy-mm-dd): ");
        LocalDate deaprtureDate =  LocalDate.parse(scanner.nextLine());

        System.out.println("How many rooms would you like to book?");
        int nrOfRoomsToBook = Integer.parseInt(scanner.nextLine());
        int roomNumber;
//        int roomModel; // Premium/Stnadard
//        int roomType; // Single/Double
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
            Booking booking = new Booking(this, roomSet, arrivalDate, deaprtureDate);
            for (Room room: roomSet){
                room.setRoomStatus(RoomStatus.UNAVAILABLE);
            }
            hotel.getBookingList().add(booking);
            Payment payment = new Payment(booking.getId(), booking.getPayment(), booking);
            hotel.getPaymentList().add(payment);
            this.bookingSet.add(booking);
            this.paymentSet.add(payment);
            System.out.println("Your booking has been made successfully!");
            payBooking(payment.getId());
        } else {
            for(Room room : roomSet){
                room.setRoomStatus(RoomStatus.AVAILABLE);
            }
            System.out.println("Failed booking!");
        }
//        for(int i = 1; i <= nrOfRoomsToBook; i++){
//            System.out.println("If you want room" + i + " to be Standard => type 0");
//            System.out.println("If you want room" + i + " to be Premium => type 1");
//            roomModel = Integer.parseInt(scanner.nextLine());
//            System.out.println("If you want room" + i + " to be Single => type 1");
//            System.out.println("If you want room" + i + " to be Double => type 2");
//            roomType = Integer.parseInt(scanner.nextLine());
//        }
    }

    public void payBooking(int id){
        Scanner scanner = new Scanner(System.in);
        for (Payment payment: this.paymentSet){
            if (payment.getId().equals(id)){
                if (payment.getPaymentStatus() == PaymentStatus.NOT_PAID){
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
    }

    public void checkOut(int idBooking){
        boolean wrongId = true;
        for(Booking booking:this.bookingSet){
            if (booking.getId().equals(idBooking)){
                wrongId = false;
                for (Payment payment:this.paymentSet){
                    if (payment.getPaymentStatus() == PaymentStatus.PAID){
                        for (Room room:booking.getRoomSet()){
                            room.setRoomStatus(RoomStatus.AVAILABLE);
                        }
                        System.out.println("Check-out complete!");
                        return;
                    } else{
                        System.out.println("You need to pay this booking first!");
                        this.payBooking(idBooking);
                        checkOut(idBooking);
                    }
                }
            }
        }
        if (wrongId){
            System.out.println("This booking doesn't exist!");
        }
    };

    public void changePassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type current password: ");
        String currentPassword = scanner.nextLine();

        if (currentPassword.equals(this.getPassword())){
            System.out.println("Type new password: ");
            String newPassword = scanner.nextLine();
            this.setPassword(newPassword);
            System.out.println("Password changed.");
        } else {
            System.out.println("Incorrect password.");
        }
    }

    @Override
    public String toString() {
        StringBuilder customer = new StringBuilder("\n\t ==> Customer " +
                this.getId() + ": " + this.getFullName() + '\n' +
                "\t\tuserDocument: " + userDocument + '\n' +
                "\t\taddress: " + address + '\n' +
                "\t\ttelephone: " + telephone + '\n' +
                "\t\tusername: " + username + '\n' +
                "\t\tpassword: " + password + '\n' +
                "\t\temail: " + email + '\n');


        if (!bookingSet.isEmpty()) {
            customer.append("\t\tBookingSet: ");
            for (Booking booking : bookingSet) {
                customer.append("\n\t\t\t=> ").append(booking);
            }
        } else{
            customer.append("\t\tBookings: []\n");
        }

        if (!paymentSet.isEmpty()) {
            customer.append("\n\t\tPaymentSet: ");
            for (Payment payment : paymentSet) {
                customer.append("\n\t\t\t=> ").append(payment);
            }
        } else{
            customer.append("\t\tPayments: []\n");
        }

        return customer.toString();
    }
}
