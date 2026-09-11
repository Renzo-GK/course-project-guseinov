package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ncfu.meetingroom.entity.Booking;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.foundation.BookingRepository;
import ru.ncfu.meetingroom.foundation.MeetingRoomRepository;
import ru.ncfu.meetingroom.foundation.UserRepository;
import ru.ncfu.meetingroom.mediator.BookingService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceExtendedTest {
    private final BookingRepository bookings = mock(BookingRepository.class);
    private final MeetingRoomRepository rooms = mock(MeetingRoomRepository.class);
    private final UserRepository users = mock(UserRepository.class);
    private final BookingService service = new BookingService(bookings, rooms, users);

    @Test
    void createsBookingForValidInterval() {
        User user = new User("user", "x", "ROLE_USER");
        MeetingRoom room = new MeetingRoom("Альфа", 8, "1 этаж", "Проектор");
        when(bookings.existsByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatus(anyLong(), any(), any(), eq("ACTIVE"))).thenReturn(false);
        when(users.findByUsername("user")).thenReturn(Optional.of(user));
        when(rooms.findById(1L)).thenReturn(Optional.of(room));
        Booking saved = new Booking(user, room, LocalDateTime.of(2026,9,11,10,0), LocalDateTime.of(2026,9,11,11,0));
        saved.setId(1L);
        when(bookings.save(any(Booking.class))).thenReturn(saved);
        when(bookings.findWithRelationsById(1L)).thenReturn(Optional.of(saved));

        Booking result = service.create("user",1L,
            LocalDateTime.of(2026,9,11,10,0),
            LocalDateTime.of(2026,9,11,11,0));

        assertEquals("Альфа", result.getRoom().getName());
        assertEquals("ACTIVE", result.getStatus());
        verify(bookings).save(any(Booking.class));
    }

    @Test
    void cancelChangesStatusToCancelled() {
        User user = new User("user", "x", "ROLE_USER");
        MeetingRoom room = new MeetingRoom("Альфа", 8, "1 этаж", "Проектор");
        Booking booking = new Booking(user, room,
            LocalDateTime.of(2026,9,11,10,0),
            LocalDateTime.of(2026,9,11,11,0));
        booking.setId(1L);
        when(bookings.findWithRelationsById(1L)).thenReturn(Optional.of(booking));

        service.cancel(1L, "user");

        assertEquals("CANCELLED", booking.getStatus());
    }

    @Test
    void cancelRejectsAnotherUser() {
        User user = new User("owner", "x", "ROLE_USER");
        MeetingRoom room = new MeetingRoom("Альфа", 8, "1 этаж", "Проектор");
        Booking booking = new Booking(user, room,
            LocalDateTime.of(2026,9,11,10,0),
            LocalDateTime.of(2026,9,11,11,0));
        when(bookings.findWithRelationsById(1L)).thenReturn(Optional.of(booking));

        assertThrows(SecurityException.class, () -> service.cancel(1L, "other"));
    }
}
