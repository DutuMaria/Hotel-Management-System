package entity.hotel;

import entity.booking.Booking;
import entity.payment.Payment;
import entity.room.Room;
import entity.user.Customer;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Hotel {
    private static Hotel hotel;
    private String name;
    private Integer floors;
    private Integer rooms;
    private List<Room> roomList;
    private List<Customer> customerList;
    private List<Booking> bookingList;
    private List<Payment> paymentList;
    private EnumSet<HotelServices> hotelServices;

    private Hotel() {
        name = "Infinity Hotel";
        rooms = 100;
        floors = 10;
        hotelServices = EnumSet.allOf(HotelServices.class);
        roomList = new ArrayList<>();
        customerList = new ArrayList<>();
        bookingList = new ArrayList<>();
        paymentList = new ArrayList<>();
    }

    public static Hotel getHotelInstance() {
        if (hotel == null)
            hotel = new Hotel();
        return hotel; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public EnumSet<HotelServices> getHotelServices() {
        return hotelServices;
    }

    public void setHotelServices(EnumSet<HotelServices> hotelServices) { this.hotelServices = hotelServices; }

    public static void showHotel(){ System.out.println("Hotel: " + hotel.getName());}


}

