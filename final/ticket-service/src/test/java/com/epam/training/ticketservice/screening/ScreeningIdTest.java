package com.epam.training.ticketservice.screening;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningIdTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime localDateTime = LocalDateTime.parse("2002-04-11 14:00", formatter);
    private final ScreeningId underTest = new ScreeningId("movie", "room", localDateTime);

    @Test
    void testEqualsWithSelf() {
        assertEquals(underTest, underTest);
    }

    @Test
    void testHashCodeWithSelf() {
        assertEquals(underTest.hashCode(), underTest.hashCode());
    }

    @Test
    void testHashCodeWithOtherScreeningIdWithSameData() {
        ScreeningId otherScreeningId = new ScreeningId("movie", "room", localDateTime);
        assertEquals(underTest.hashCode(), otherScreeningId.hashCode());
    }

    @Test
    void testHashCodeWithOtherScreeningIdsWithDifferentData() {
        ScreeningId screeningId1 = new ScreeningId("movie", "room", LocalDateTime.parse("2002-04-11 20:00", formatter));
        ScreeningId screeningId2 = new ScreeningId("movie2", "room", localDateTime);
        ScreeningId screeningId3 = new ScreeningId("movie", "room3", localDateTime);
        assertNotEquals(underTest.hashCode(), screeningId1.hashCode());
        assertNotEquals(underTest.hashCode(), screeningId2.hashCode());
        assertNotEquals(underTest.hashCode(), screeningId3.hashCode());
    }
}