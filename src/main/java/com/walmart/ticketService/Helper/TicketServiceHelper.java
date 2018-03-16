package com.walmart.ticketService.Helper;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TicketServiceHelper {
    /**
     * helper method to read properties from the file
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String filePath) throws IOException{

        Properties properties = new Properties();
        properties.load(TicketServiceHelper.class.getResourceAsStream(filePath));
        return properties;
    }
}
