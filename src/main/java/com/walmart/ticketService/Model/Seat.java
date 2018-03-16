package com.walmart.ticketService.Model;

import java.util.Comparator;
import java.io.Serializable;

public class Seat implements Serializable{

    String seatNumber;

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    public static  final Comparator<Seat> PARTY_PHONE_COMPARATOR = new Comparator<Seat>() {
        @Override
        public int compare(Seat partyPhoneVO1,
                           Seat partyPhoneVO2) {
            String partyPhoneCd1 = partyPhoneVO1.getSeatNumber();
            String partyPhoneCd2 = partyPhoneVO2.getSeatNumber();

            // Descending order
            return partyPhoneCd1.compareTo(partyPhoneCd2);
        }
    };
}
