package com.walmart.ticketService;

import com.walmart.ticketService.Exception.TicketServiceException;
import com.walmart.ticketService.Model.SeatHold;
import com.walmart.ticketService.Service.Impl.TicketServiceImpl;

import java.io.IOException;
import java.util.Scanner;

public class RunTicketService {
    /**
     * Entry point for the Sample Ticket Service
     * Which can be tested via CMD prompt with options
     * and respective questions to execute the test cases     *
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        boolean continueTestExecution = true;
        TicketServiceImpl ticketService = new TicketServiceImpl();
        //loads required property files
        ticketService.loadPropertiesOnInit();
        try {
            //gets the venue seating chart ready on startup
            ticketService.initiateSeatingChart();
        } catch (NumberFormatException e) {
            continueTestExecution = false;
        }


        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nRunning Sample Ticket Service");
        System.out.println("=============================");


        while (continueTestExecution) {
            System.out.println("\nPlease select from the below option numbers to test Sample Ticket Service");
            String options = "\nOptions: \n1. Available Seats \n2. Request for Hold \n3. Reserve \n4.Exit the Application\n ";
            System.out.println(options);
            String str = sc.next();
            int input = 0;
            try {
                input = Integer.parseInt(str);
            }catch (NumberFormatException e){
                System.out.println("Please enter valid option type");
                System.out.println("*****************************\n");
                break;
            }
            //switch case to handle inputs from the cmd prompt
            switch (input) {
                case 1:
                    System.out.println("No.of Seats Availabe = " + ticketService.numSeatsAvailable());
                    System.out.println("*****************************\n");
                    break;
                case 2:
                    try {
                        System.out.println("How many seats for hold?");
                        String xs = sc.next();
                        int noOfSeats = Integer.parseInt(xs);
                        System.out.println("\nCustomer email?");
                        String holdCustomerEmail = sc.next();
                        try {
                            SeatHold seatHoldResponse = ticketService.findAndHoldSeats(noOfSeats, holdCustomerEmail);
                            System.out.println("\n" + seatHoldResponse.toString());
                            System.out.println("*****************************\n");
                        } catch (TicketServiceException e) {
                            System.out.println("\n" + e.getErrorMsg());
                            System.out.println("*****************************\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Please enter valid input.");
                        System.out.println("*****************************\n");
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Hold confirmation code?");
                        String xs = sc.next();
                        int holdConfirmationCode = Integer.parseInt(xs);
                        System.out.println("Customer email?\n");
                        String reserveCustomerEmail = sc.next();
                        try {
                            String reserveResponse = ticketService.reserveSeats(holdConfirmationCode, reserveCustomerEmail);
                            System.out.println("\n" + reserveResponse.toString());
                            System.out.println("*****************************\n");

                        } catch (TicketServiceException e) {
                            System.out.println("\n" + e.getErrorMsg());
                            System.out.println("*****************************\n");
                        }
                    } catch (Exception e) {
                        System.out.println("\n Please enter valid input.");
                        System.out.println("*****************************\n");
                    }
                    break;
                case 4:
                    continueTestExecution = false;
                    System.out.println("Gracefully Exiting the Application");
                    System.out.println("*****************************\n");
                    break;

                default:
                    System.out.println("Please enter valid option type");
                    System.out.println("*****************************\n");
                    break;
            }
        }


    }
}
