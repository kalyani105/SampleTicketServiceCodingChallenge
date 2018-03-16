package com.walmart.ticketService.Validations;

import org.junit.Assert;
import org.junit.Test;

public class TicketServiceValidationsTest {

    TicketServiceValidations ticketServiceValidations = new TicketServiceValidations();

    @Test
    public void testValidateEmailAddressSuccess(){
        boolean validation = ticketServiceValidations.validateEmailAddress("tammanadk@gmail.com");
        Assert.assertTrue("True", validation);
    }

    @Test
    public void testValidateEmailAddressFailure(){
        boolean validationFail = ticketServiceValidations.validateEmailAddress("tammanamail.com");
        Assert.assertNotNull(validationFail);
    }
}
