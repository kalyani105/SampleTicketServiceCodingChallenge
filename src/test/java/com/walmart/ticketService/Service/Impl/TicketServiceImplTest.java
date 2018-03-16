package com.walmart.ticketService.Service.Impl;

import com.walmart.ticketService.Exception.TicketServiceException;
import com.walmart.ticketService.Model.SeatHold;
import com.walmart.ticketService.Service.Impl.TicketServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TicketServiceImplTest {
    public TicketServiceImpl ticketService = new TicketServiceImpl();

    @Before
    public void setUp() throws Exception {
        ticketService.loadPropertiesOnInit();
        ticketService.initiateSeatingChart();
    }

    @Test
    public void testGetAvailableSeats() {

        int size = ticketService.numSeatsAvailable();
        Assert.assertEquals(size, 100);
    }

    @Test
    public void testFindAndHoldSeatsSuccess() {
        try {
            SeatHold seatHold = ticketService.findAndHoldSeats(10, "test@gmail.com");
            Assert.assertNotNull(seatHold);

            try {
                String confirmationNumber = ticketService.reserveSeats(seatHold.getHoldConfirmationCode(), "test");
                Assert.assertNotNull(confirmationNumber);
            } catch (TicketServiceException e) {
                Assert.assertEquals(e.getErrorMsg(), "Invalid email address");

            }

            try {
                String confirmationNumber = ticketService.reserveSeats(seatHold.getHoldConfirmationCode(), "testtwo@gmail.com");
                Assert.assertNotNull(confirmationNumber);
            } catch (TicketServiceException e) {
                Assert.assertEquals(e.getErrorMsg(), "Hold and reserve Customer emails are different and this reservation can not be made.");

            }


            String confirmationNumber = ticketService.reserveSeats(seatHold.getHoldConfirmationCode(),
                    "test@gmail.com");
            Assert.assertNotNull(confirmationNumber);


        } catch (TicketServiceException e) {
            Assert.fail();
        }
    }


    @Test
    public void testFindAndHoldSeatsCleanHoldSeats() {
        try {
            SeatHold seatHold = ticketService.findAndHoldSeats(10, "test@gmail.com");
            Assert.assertNotNull(seatHold);

        } catch (TicketServiceException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFindAndHoldSeatsEmailValidationFailure() {
        try {
            SeatHold seatHold = ticketService.findAndHoldSeats(10, "test");
            Assert.assertNotNull(seatHold);
        } catch (TicketServiceException e) {
            Assert.assertEquals(e.getErrorMsg(), "Invalid email address");
        }
    }

    @Test
    public void testFindAndHoldSeatsEnoughSeatsNotAvailable() {
        try {
            SeatHold seatHold = ticketService.findAndHoldSeats(123, "test@gmail.com");
            Assert.assertNotNull(seatHold);
        } catch (TicketServiceException e) {
            Assert.assertEquals(e.getErrorMsg(), "Enough seats not available to book");
        }

    }

    @Test
    public void testReserveSeatsHoldIdNotAvailable() {
        try {
            String reserveId = ticketService.reserveSeats(12345, "test@gmail.com");
        } catch (TicketServiceException e) {
            Assert.assertEquals(e.getErrorMsg(), "No held reservation found for the given Hold Confirmation Id " + 12345);
        }
    }


}
