package com.walmart.ticketService.Model;
import java.util.List;
import java.util.Random;

public class Order {

   List<Seat> seats;
   String customerEmail;
   int holdConfirmationCode;
   int reserveConfirmationCode;
   long expirationTime;

   private static Random randomReserveConfGenerator = new Random(8);

   public Order(){
       this.reserveConfirmationCode = randomReserveConfGenerator.nextInt() & Integer.MAX_VALUE;;
   }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expirationTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getHoldConfirmationCode() {
        return holdConfirmationCode;
    }

    public void setHoldConfirmationCode(int holdConfirmationCode) {
        this.holdConfirmationCode = holdConfirmationCode;
    }

    public int getReserveConfirmationCode() {
        return reserveConfirmationCode;
    }

    public void setReserveConfirmationCode(int reserveConfirmationCode) {
        this.reserveConfirmationCode = reserveConfirmationCode;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
