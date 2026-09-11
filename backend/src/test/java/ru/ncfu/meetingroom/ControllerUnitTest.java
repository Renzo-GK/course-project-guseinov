package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.ncfu.meetingroom.control.*;
import ru.ncfu.meetingroom.entity.*;
import ru.ncfu.meetingroom.mediator.BookingService;
import ru.ncfu.meetingroom.mediator.RoomService;
import ru.ncfu.meetingroom.mediator.UserService;
import ru.ncfu.meetingroom.presentation.AdminRoomController;
import ru.ncfu.meetingroom.presentation.BookingController;
import ru.ncfu.meetingroom.presentation.RoomController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ControllerUnitTest {
    private UsernamePasswordAuthenticationToken auth(String name) {
        return new UsernamePasswordAuthenticationToken(name, "n/a");
    }

    private Booking booking(String username, String roomName) {
        User u = new User(username, "hash", "ROLE_USER");
        MeetingRoom r = new MeetingRoom(roomName, 8, "1 этаж", "Проектор");
        r.setId(2L);
        Booking b = new Booking(u, r, LocalDateTime.of(2026,9,11,10,0), LocalDateTime.of(2026,9,11,11,0));
        b.setId(4L);
        return b;
    }

    @Test
    void roomControllerMapsAllAndOne() {
        RoomService service = mock(RoomService.class);
        RoomController controller = new RoomController(service, new RoomMapper());
        MeetingRoom a = new MeetingRoom("Альфа",8,"1 этаж","Проектор"); a.setId(1L);
        MeetingRoom b = new MeetingRoom("Бета",12,"2 этаж","TV"); b.setId(2L);
        when(service.all()).thenReturn(List.of(a,b));
        when(service.get(2L)).thenReturn(b);
        assertEquals(2, controller.all().size());
        assertEquals("Бета", controller.one(2L).name());
    }

    @Test
    void bookingControllerCoversCreateMineAvailabilityCancel() {
        BookingService service = mock(BookingService.class);
        BookingController controller = new BookingController(service);
        Booking b = booking("alice","Альфа");
        when(service.create(eq("alice"),eq(2L),any(),any())).thenReturn(b);
        when(service.mine("alice")).thenReturn(List.of(b));
        when(service.availability(eq(2L),any(),any())).thenReturn(List.of(b));

        BookingResponse created = controller.create(auth("alice"), new BookingRequest(2L,
                LocalDateTime.of(2026,9,11,10,0), LocalDateTime.of(2026,9,11,11,0)));
        assertEquals("Альфа", created.roomName());
        assertEquals(1, controller.mine(auth("alice")).size());
        assertEquals(1, controller.availability(2L, LocalDate.of(2026,9,11)).size());
        controller.cancel(auth("alice"), 4L);
        verify(service).cancel(4L,"alice");
    }

    @Test
    void adminControllerCoversCrudAndStats() {
        RoomService rooms = mock(RoomService.class);
        BookingService bookings = mock(BookingService.class);
        UserService users = mock(UserService.class);
        AdminRoomController controller = new AdminRoomController(rooms, bookings, users, new RoomMapper());
        MeetingRoom room = new MeetingRoom("Дельта",6,"3 этаж","TV"); room.setId(4L);
        when(rooms.create(any())).thenReturn(room);
        when(rooms.update(eq(4L),any())).thenReturn(room);
        when(rooms.all()).thenReturn(List.of(room));
        when(bookings.all()).thenReturn(List.of());
        when(bookings.countActive()).thenReturn(3L);
        when(bookings.countCancelled()).thenReturn(1L);
        when(users.count()).thenReturn(5L);

        assertEquals("Дельта", controller.create(new RoomRequest("Дельта",6,"3 этаж","TV")).name());
        assertEquals("Дельта", controller.update(4L,new RoomRequest("Дельта",6,"3 этаж","TV")).name());
        controller.delete(4L);
        assertEquals(5, controller.stats().totalUsers());
        controller.bookings();
        verify(rooms).delete(4L);
    }
}
