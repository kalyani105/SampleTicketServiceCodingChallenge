package com.walmart.ticketService.Model;

import java.util.Random;

public class SeatHold {

    String customerEmailId;
    int holdConfirmationCode;
    int numberOfSeatsHeld;
    String errorMessage;

    String seatNumberRange;

    private static Random rand = new Random(8);
    public SeatHold(){
        this.holdConfirmationCode  =  rand.nextInt() & Integer.MAX_VALUE;
    }
    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public int getHoldConfirmationCode() {
        return holdConfirmationCode;
    }

    public void setHoldConfirmationCode(int holdConfirmationCode) {
        this.holdConfirmationCode = holdConfirmationCode;
    }

    public int getNumberofSeatsHeld() {
        return numberOfSeatsHeld;
    }

    public void setNumberofSeatsHeld(int numberofSeatsHeld) {
        this.numberOfSeatsHeld = numberofSeatsHeld;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSeatNumberRange() {
        return seatNumberRange;
    }

    public void setSeatNumberRange(String seatNumberRange) {
        this.seatNumberRange = seatNumberRange;
    }

    @Override
    public String toString(){
        return String.format("Hold Confirmation Code : " + this.holdConfirmationCode + "\n" +
                " Number Of Seats Held : " + this.numberOfSeatsHeld +"\n" +
                " Coustomer Email: " + this.customerEmailId) + "\n" +
                "Seat Nuber Range: " + this.seatNumberRange;
    }
}

