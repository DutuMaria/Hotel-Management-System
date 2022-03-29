package entity.payment;

import entity.booking.Booking;

import java.time.LocalDateTime;

public class Payment {
    private final Integer id;
    private Double totalPrice;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private Booking booking;
    private LocalDateTime time;

    public Payment(Integer id, Double totalPrice, Booking booking) {
        this.id = booking.getId();
        this.totalPrice = booking.getPayment();
//        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.paymentStatus = PaymentStatus.NOT_PAID;
//        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
//                ", booking=" + booking +
                ", time=" + time +
                '}';
    }
}

