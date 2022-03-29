package entity.booking;

import entity.room.Room;
import entity.user.Customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class Booking {
    private static Integer count = 0;
    private final Integer id;
    private Customer customer;
    private Set<Room> roomSet;
    private LocalDate arrival;
    private LocalDate departure;
    private Double payment;

    public Booking(Customer customer, Set<Room> roomSet, LocalDate arrival, LocalDate departure) {
        count++;
        this.id = count;
        this.customer = customer;
        this.roomSet = roomSet;
        this.arrival = arrival;
        this.departure = departure;
        this.payment = this.calculatePayment();
    }

    public Integer getId() {
        return id;
    }

    public Set<Room> getRoomSet() {
        return roomSet;
    }

    public void setRoomSet(Set<Room> roomSet) {
        this.roomSet = roomSet;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    private Double calculatePayment(){
        long daysBetween = ChronoUnit.DAYS.between(arrival, departure);
        double total = 0d;
        for (Room room:this.roomSet) {
            total += room.getPrice() * daysBetween;
        }
        return total;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
//                ", customer=" + customer +
                ", roomSet=" + roomSet +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", payment=" + payment +
                '}';
    }
}

