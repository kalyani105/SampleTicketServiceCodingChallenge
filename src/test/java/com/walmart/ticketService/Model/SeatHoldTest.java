package com.walmart.ticketService.Model;

import org.junit.Test;

public class SeatHoldTest {
    SeatHold seatHold = new SeatHold();

    @Test
    public void testVO(){
        seatHold.toString();
        seatHold.setHoldConfirmationCode(1234);
        seatHold.getCustomerEmailId();
        seatHold.getNumberofSeatsHeld();
        seatHold.getErrorMessage();
        seatHold.getSeatNumberRange();

    }
}
