package com.walmart.ticketService.Service.Impl;

import com.walmart.ticketService.Constants.Constants;
import com.walmart.ticketService.Exception.TicketServiceException;
import com.walmart.ticketService.Helper.TicketServiceHelper;
import com.walmart.ticketService.Model.Order;
import com.walmart.ticketService.Model.Seat;
import com.walmart.ticketService.Model.SeatHold;
import com.walmart.ticketService.Service.TicketService;
import com.walmart.ticketService.Validations.TicketServiceValidations;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class TicketServiceImpl implements TicketService {
    private static final Logger logger = Logger.getLogger(TicketServiceImpl.class);

    private static List<Seat> availableSeats = new ArrayList<>();
    private static Map<Integer, Order> heldSeats = new HashMap<>();
    private static Map<Integer, Order> reservedSeats = new HashMap<>();

    private static Properties properties = new Properties();

    /**
     * loads the properties files
     * @throws IOException
     */
    @Override
    public void loadPropertiesOnInit() throws IOException {
        logger.info("loding properties on init()");
        properties = TicketServiceHelper.getProperties("/TicketServiceConfiguration.properties");
    }

    /**
     * initiates and creates the venue seating chart on application startup
     * @throws NumberFormatException
     */
    @Override
    public void initiateSeatingChart() throws NumberFormatException{
        availableSeats.clear();
        logger.info("initiating seating chart on init()");
        int rowsInTheater = 0;
        int seatsPerRow = 0;
        String rowsInTheaterStr = (String) properties.get("noOfRowsInTheater");
        try {
            if (rowsInTheaterStr != null && !rowsInTheaterStr.isEmpty()) {
                rowsInTheater = Integer.parseInt(rowsInTheaterStr);
            } else {
                rowsInTheater = Constants.DEFAULT_ROWS_IN_VENUE;
            }

            String seatsPerRowStr = (String) properties.get("noOfSeatsPerRow");
            if (seatsPerRowStr != null && !seatsPerRowStr.isEmpty()) {
                seatsPerRow = Integer.parseInt(seatsPerRowStr);
            } else {
                seatsPerRow = Constants.DEFAULT_SEATS_PER_ROW;
            }
        }catch(NumberFormatException e){
            System.out.println(Constants.ERROR_DATA_MISSING_PROPS_FILE);
            throw new NumberFormatException(e.getMessage());
        }

        for (int i = 0; i < rowsInTheater; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                Seat seat = new Seat();
                String seatNo = Integer.toString(i) + ":" + Integer.toString(j);
                seat.setSeatNumber(seatNo);
                availableSeats.add(seat);
            }
        }

    }

    /**
     * The number of seats in the venue that are neither held nor reserved
     *
     * @return the number of tickets available in the venue
     */
    @Override
    public int numSeatsAvailable() {
        logger.info("calculating number of seats available");
        cleanUpExpiredHeldSeats();
        return availableSeats.size();
    }

    /**
     * cancels all the expired held reservations
     */
    private void cleanUpExpiredHeldSeats() {
        logger.info("cleaning up expired seats");
        Iterator it = heldSeats.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry HeldSeats = (Map.Entry) it.next();
            Order order = (Order) HeldSeats.getValue();
            if (order.isExpired()) {
                logger.debug("adding expired seats to the available map");
                availableSeats.addAll(order.getSeats());
                it.remove();
            }
        }
        Collections.sort(availableSeats, Seat.PARTY_PHONE_COMPARATOR);
    }

    /**
     * Find and hold the best available seats for a customer
     *
     * @param numSeats the number of seats to find and hold
     * @param customerEmail unique identifier for the customer
     * @return a SeatHold object identifying the specific seats and related
    information
     */
    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) throws TicketServiceException {
       logger.info("inside hold seats");
        SeatHold seatHold = new SeatHold();
        List<Seat> holdSeatList = new ArrayList<Seat>();
        Order order = new Order();

        if(!TicketServiceValidations.validateEmailAddress(customerEmail)){
            logger.debug("validate email failed");
            logger.error(Constants.ERROR_MESSAGE_INVALID_EMAIL_ADDRESS + customerEmail);
            throw new TicketServiceException(Constants.ERROR_MESSAGE_INVALID_EMAIL_ADDRESS);
        }
        logger.debug("validate email pass");
        String heldSeatsExpiryTimeInSecStr = (String) properties.get("heldSeatsExpiryTimeInSec");

        if(heldSeatsExpiryTimeInSecStr != null && !heldSeatsExpiryTimeInSecStr.isEmpty()) {
            int heldSeatsExpiryTimeInSec = Integer.parseInt(heldSeatsExpiryTimeInSecStr);
            order.setExpirationTime(System.currentTimeMillis() + heldSeatsExpiryTimeInSec * 1000);
        }else{
            int heldSeatsExpiryTimeInSec = Constants.DEFAULT_EXPIRY_TIME_INSECONDS;
            order.setExpirationTime(System.currentTimeMillis() + heldSeatsExpiryTimeInSec * 1000);
        }

        cleanUpExpiredHeldSeats();
        if (numSeats <= availableSeats.size()) {
            for (int i = 0; i < numSeats; i++) {
                if (availableSeats.size() > 0) {
                    Seat nextAvailableSeat = availableSeats.get(0);
                    holdSeatList.add(nextAvailableSeat);
                    availableSeats.remove(nextAvailableSeat);
                }
            }
        } else {
            logger.error(Constants.ERROR_MESSAGE_ENOUGH_SEATS_NOT_AVAILABLE + " for the coustomer " + customerEmail);
            seatHold.setErrorMessage(Constants.ERROR_MESSAGE_ENOUGH_SEATS_NOT_AVAILABLE);
            throw new TicketServiceException(Constants.ERROR_MESSAGE_ENOUGH_SEATS_NOT_AVAILABLE);
        }
        seatHold.setCustomerEmailId(customerEmail);
        seatHold.setNumberofSeatsHeld(numSeats);

        order.setCustomerEmail(customerEmail);
        order.setHoldConfirmationCode(seatHold.getHoldConfirmationCode());
        order.setSeats(holdSeatList);
        // this line of code is just for user information to show what seat numbers were held
        String seatNumberRange = holdSeatList.get(0).getSeatNumber() + "-" +
                holdSeatList.get(holdSeatList.size() - 1).getSeatNumber();

        seatHold.setSeatNumberRange(seatNumberRange);
        heldSeats.put(seatHold.getHoldConfirmationCode(), order);
        return seatHold;
    }

    /**
     * Commit seats held for a specific customer
     *
     * @param seatHoldId the seat hold identifier
     * @param customerEmail the email address of the customer to which the
    seat hold is assigned
     * @return a reservation confirmation code
     */
    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) throws TicketServiceException {
        logger.info("Reserving seats for the Customer " + customerEmail);

        if(!TicketServiceValidations.validateEmailAddress(customerEmail)){
            logger.debug("validate email failed");
            logger.error(Constants.ERROR_MESSAGE_INVALID_EMAIL_ADDRESS + customerEmail);
            throw new TicketServiceException(Constants.ERROR_MESSAGE_INVALID_EMAIL_ADDRESS);
        }
        Order bookingRequestOrder = heldSeats.get(seatHoldId);
        Order bookingConfirmationOrder = new Order();

        if (bookingRequestOrder != null) {
            if (bookingRequestOrder.getCustomerEmail().equals(customerEmail)) {
                {
                    logger.debug("continuing with reservation");
                    if (!bookingRequestOrder.isExpired()) {
                        bookingConfirmationOrder.setCustomerEmail(customerEmail);
                        bookingConfirmationOrder.setSeats(bookingRequestOrder.getSeats());
                        heldSeats.remove(seatHoldId);
                        reservedSeats.put(bookingConfirmationOrder.getReserveConfirmationCode(), bookingConfirmationOrder);
                        return Constants.SUCCESSFULL_RESERVATION_MESSAGE1 + bookingRequestOrder.getSeats().size() +
                                Constants.SUCCESSFULL_RESERVATION_MESSAGE2 + customerEmail + Constants.SUCCESSFULL_RESERVATION_MESSAGE3
                                + bookingConfirmationOrder.getReserveConfirmationCode();
                    } else {
                        logger.error("Held seats for the Customer " + customerEmail + " has expired.");
                        cleanUpExpiredHeldSeats();
                        return Constants.ERROR_HOLD_SEATS_EXPIRED + customerEmail;
                    }
                }
            } else {
                logger.error("Can not continue with the servation as the hold email"
                        + bookingRequestOrder.getCustomerEmail() + "is different from the reserve customer email "
                        + customerEmail);
                throw new TicketServiceException(Constants.ERROR_DIFFERENT_COUSTOMER_EMAIL);
            }
        } else {
            logger.error("Can not continue with the reservation as the Hold confirmation Id" + seatHoldId + " is invalid");
            throw new TicketServiceException(Constants.ERROR_HOLD_RESERVATION_NOT_FOUND + seatHoldId);
        }
    }
}
