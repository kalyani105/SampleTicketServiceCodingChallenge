package com.walmart.ticketService.Helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;
import org.junit.Assert;

public final class TicketServiceHelperTest {

    TicketServiceHelper ticketServiceHelper = new TicketServiceHelper();
    @Test
    public void testLoadProperties(){
        Properties properties = new Properties();
        try {
            properties = ticketServiceHelper.getProperties("/TicketServiceConfiguration.properties");
        }catch(IOException e){

        }
        Assert.assertNotNull(properties);
    }



}
