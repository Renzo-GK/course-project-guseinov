package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import ru.ncfu.meetingroom.control.BookingResponse;
import ru.ncfu.meetingroom.entity.Booking;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.entity.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ResponseMappingTest {
    @Test
    void bookingResponseContainsPublicFields() {
        User user = new User("alice", "hash", "ROLE_USER");
        MeetingRoom room = new MeetingRoom("Альфа", 8, "1 этаж", "Проектор");
        room.setId(5L);
        Booking booking = new Booking(user, room,
                LocalDateTime.of(2026,9,11,10,0),
                LocalDateTime.of(2026,9,11,11,0));
        booking.setId(9L);

        BookingResponse response = BookingResponse.from(booking);

        assertEquals(9L, response.id());
        assertEquals(5L, response.roomId());
        assertEquals("Альфа", response.roomName());
        assertEquals("alice", response.username());
        assertEquals("ACTIVE", response.status());
    }
}
