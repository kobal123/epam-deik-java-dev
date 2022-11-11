package com.epam.training.ticketservice.screening;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime localDateTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
    private final Screening underTest = new Screening("movie", "room", localDateTime);

    @Test
    void testEqualsWithSelf() {
        assertEquals(underTest, underTest);
    }

    @Test
    void testHashCodeWithSelf() {
        assertEquals(underTest.hashCode(), underTest.hashCode());
    }

    @Test
    void testHashCodeWithOtherScreeningWithSameData() {
        Screening otherScreening = new Screening("movie", "room", localDateTime);
        assertEquals(underTest.hashCode(), otherScreening.hashCode());
    }

    @Test
    void testHashCodeWithOtherScreeningsWithDifferentData() {
        Screening screeningId1 = new Screening("movie", "room", LocalDateTime.parse("2002-04-11 20:00", formatter));
        Screening screeningId2 = new Screening("movie2", "room", localDateTime);
        Screening screeningId3 = new Screening("movie", "room3", localDateTime);
        assertNotEquals(underTest.hashCode(), screeningId1.hashCode());
        assertNotEquals(underTest.hashCode(), screeningId2.hashCode());
        assertNotEquals(underTest.hashCode(), screeningId3.hashCode());
    }
}