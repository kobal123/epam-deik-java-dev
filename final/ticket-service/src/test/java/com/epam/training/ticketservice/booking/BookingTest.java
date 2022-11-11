package com.epam.training.ticketservice.booking;

import com.epam.training.ticketservice.screening.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime localDateTime2 = LocalDateTime.now().plusHours(3);

    Screening screening = new Screening("Movie", "Room", localDateTime);
    Screening screening2 = new Screening("Movie2", "Room2", localDateTime2);

    Booking bookingOne = new Booking("username", screening);
    Booking bookingTwo = new Booking("username", screening);
    Booking bookingThree = new Booking("username2", screening2);


    @Test
    void testHashCodeShouldReturnTrueWhenComparingWithSameObject() {
        assertEquals(bookingOne.hashCode(), bookingOne.hashCode());
    }

    @Test
    void testHashCodeShouldReturnTrueWhenComparingWithOtherObjectWithSameAttributes() {
        assertEquals(bookingOne.hashCode(), bookingTwo.hashCode());
    }



    @Test
    void testEqualsShouldReturnTrueWhenComparingWithOtherSameObject() {
        assertEquals(bookingOne,bookingTwo);
    }

    @Test
    void testEqualsShouldReturnTrueWhenComparingWithOtherObjectWithSameAttributes() {
        assertEquals(bookingOne, bookingTwo);
    }
}