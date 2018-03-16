package com.walmart.ticketService.Model;

import org.junit.Test;

public class OrderTest {

    Order orderVO = new Order();

    @Test
    public void testVO(){
        orderVO.setReserveConfirmationCode(123);
        orderVO.setHoldConfirmationCode(123);
        orderVO.getExpirationTime();
        orderVO.getHoldConfirmationCode();

    }
}
