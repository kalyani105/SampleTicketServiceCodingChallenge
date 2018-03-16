package com.walmart.ticketService.Exception;
public class TicketServiceException extends Exception {

    private static final long serialVersionUID = -3992158654537116889L;
    private String errorMsg;

    /**
     * Class constructor
     * @param msg
     */
    public TicketServiceException(String msg) {
        this.errorMsg = msg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

}
