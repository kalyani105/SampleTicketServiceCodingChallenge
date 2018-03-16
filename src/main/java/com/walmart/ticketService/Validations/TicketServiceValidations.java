package com.walmart.ticketService.Validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketServiceValidations {

    private static final String EMAIL_REGEX = "^\\s*?(.+)@(.+?)\\s*$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * validations on the input customer email address.
     * @param customerEmailAddress
     * @return
     */
    public static boolean validateEmailAddress(String customerEmailAddress){

        Matcher emailMatcher = EMAIL_PATTERN.matcher(customerEmailAddress);
        if (!emailMatcher.matches()) {
            return false;
        }else{
            return  true;
        }
    }
}
