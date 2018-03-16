package com.walmart.ticketService.Service;

import com.walmart.ticketService.Exception.TicketServiceException;
import com.walmart.ticketService.Model.SeatHold;

import java.io.IOException;

public interface TicketService {

    /**
     * loads the properties files
     * @throws IOException
     */
    void loadPropertiesOnInit() throws IOException;

    /**
     * initiates and creates the venue seating chart on application startup
     * @throws NumberFormatException
     */
    void initiateSeatingChart() throws NumberFormatException;

    /**
     * The number of seats in the venue that are neither held nor reserved
     *
     * @return the number of tickets available in the venue
     */
    int numSeatsAvailable();

    /**
     * Find and hold the best available seats for a customer
     *
     * @param numSeats the number of seats to find and hold
     * @param customerEmail unique identifier for the customer
     * @return a SeatHold object identifying the specific seats and related
    information
     */
    SeatHold findAndHoldSeats(int numSeats, String customerEmail) throws TicketServiceException;
    /**
     * Commit seats held for a specific customer
     *
     * @param seatHoldId the seat hold identifier
     * @param customerEmail the email address of the customer to which the
    seat hold is assigned
     * @return a reservation confirmation code
     */
    String reserveSeats(int seatHoldId, String customerEmail) throws TicketServiceException;
}
