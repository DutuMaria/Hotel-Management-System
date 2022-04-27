package entity.user;

import entity.booking.Booking;
import entity.payment.Payment;
import java.util.HashSet;
import java.util.Set;

public class Customer extends User  {
    private static Integer count = 2;
    private UserDocument userDocument;
    private String address;
    private String telephone;
    private Set<Booking> bookingSet;
    private Set<Payment> paymentSet;

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

    @Override
    public String toString() {
        StringBuilder customer = new StringBuilder("\n\n\t ==> Customer " +
                this.getId() + ": " + this.getFullName() + '\n' +
                "\t\tuserDocument: " + userDocument + '\n' +
                "\t\taddress: " + address + '\n' +
                "\t\ttelephone: " + telephone + '\n' +
                "\t\tusername: " + username + '\n' +
                "\t\tpassword: " + password + '\n' +
                "\t\temail: " + email + '\n');


        if (!bookingSet.isEmpty()) {
            customer.append("\t\tBookings: ");
            for (Booking booking : bookingSet) {
                customer.append(booking).append("\n");
            }
        } else{
            customer.append("\t\tBookings: []\n");
        }

        if (!paymentSet.isEmpty()) {
            customer.append("\n\t\tPayments: ");
            for (Payment payment : paymentSet) {
                customer.append("\n\t\t\t=> ").append(payment);
            }
        } else{
            customer.append("\t\tPayments: []\n");
        }

        return customer.toString();
    }
}
