package com.walmart.ticketService.Constants;
public class Constants {

    public static final String ERROR_DIFFERENT_COUSTOMER_EMAIL = "Hold and reserve Customer emails are different and this reservation can not be made.";
    public static final String ERROR_HOLD_RESERVATION_NOT_FOUND = "No held reservation found for the given Hold Confirmation Id ";
    public static final String ERROR_HOLD_SEATS_EXPIRED = "Held seats are expired for the customer ";
    public static final String SUCCESSFULL_RESERVATION_MESSAGE1 = "Congratualtions! ";
    public static final String SUCCESSFULL_RESERVATION_MESSAGE2 = " seats have been booked under the Customer ";
    public static final String SUCCESSFULL_RESERVATION_MESSAGE3= ". Confirmation Code :";
    public static final String ERROR_MESSAGE_ENOUGH_SEATS_NOT_AVAILABLE = "Enough seats not available to book";
    public static final String ERROR_MESSAGE_INVALID_EMAIL_ADDRESS = "Invalid email address";
    public static final int DEFAULT_EXPIRY_TIME_INSECONDS = 120;
    public static final int DEFAULT_ROWS_IN_VENUE = 10;
    public static final int DEFAULT_SEATS_PER_ROW = 10;
    public static final String ERROR_DATA_MISSING_PROPS_FILE = "\n Please enter integer values in the properties file";


}
