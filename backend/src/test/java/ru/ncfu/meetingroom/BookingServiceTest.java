package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.ncfu.meetingroom.entity.*;
import ru.ncfu.meetingroom.foundation.*;
import ru.ncfu.meetingroom.mediator.BookingService;
import java.time.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {
    @Mock BookingRepository bookings;
    @Mock MeetingRoomRepository rooms;
    @Mock UserRepository users;

    @Test
    void rejectsOverlappingBooking() {
        MockitoAnnotations.openMocks(this);
        var service = new BookingService(bookings, rooms, users);
        when(bookings.existsByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatus(anyLong(), any(), any(), anyString()))
            .thenReturn(true);

        assertThrows(IllegalStateException.class, () ->
            service.create("user", 1L,
                LocalDateTime.of(2026,1,1,10,0),
                LocalDateTime.of(2026,1,1,11,0)));
        verify(bookings, never()).save(any());
    }

    @Test
    void rejectsInvalidInterval() {
        MockitoAnnotations.openMocks(this);
        var service = new BookingService(bookings, rooms, users);
        assertThrows(IllegalArgumentException.class, () ->
            service.create("user", 1L,
                LocalDateTime.of(2026,1,1,11,0),
                LocalDateTime.of(2026,1,1,10,0)));
    }
}
