package com.walmart.ticketService.Constants;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ConstantsTest {
    Constants constants = new Constants();
    @Test
    public void testConstants() {
        assertTrue(constants.SUCCESSFULL_RESERVATION_MESSAGE1 == "Congratualtions! ");
        assertTrue(constants.ERROR_DIFFERENT_COUSTOMER_EMAIL == "Hold and reserve " +
                "Customer emails are different and this reservation can not be made.");
        assertTrue(constants.ERROR_HOLD_RESERVATION_NOT_FOUND == "No held reservation found " +
                "for the given Hold Confirmation Id ");
        assertTrue(constants.ERROR_HOLD_SEATS_EXPIRED == "Held seats are expired for the customer ");
        assertTrue(constants.SUCCESSFULL_RESERVATION_MESSAGE2 == " seats have been booked under the Customer ");
        assertTrue(constants.SUCCESSFULL_RESERVATION_MESSAGE3 == ". Confirmation Code :");
        assertTrue(constants.ERROR_MESSAGE_ENOUGH_SEATS_NOT_AVAILABLE == "Enough seats not available to book");
        assertTrue(constants.ERROR_MESSAGE_INVALID_EMAIL_ADDRESS == "Invalid email address");
        assertTrue(constants.ERROR_HOLD_SEATS_EXPIRED == "Held seats are expired for the customer ");
        assertTrue(constants.DEFAULT_EXPIRY_TIME_INSECONDS == 120);
        assertTrue(constants.DEFAULT_ROWS_IN_VENUE == 10);
        assertTrue(constants.DEFAULT_SEATS_PER_ROW == 10);
        assertTrue(constants.ERROR_DATA_MISSING_PROPS_FILE == "\n Please enter integer values in the properties file");


    }
}
