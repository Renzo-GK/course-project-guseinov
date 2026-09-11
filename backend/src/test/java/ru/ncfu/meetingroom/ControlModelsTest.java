package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import ru.ncfu.meetingroom.control.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ControlModelsTest {
    @Test
    void createsControlRecords() {
        LocalDateTime start=LocalDateTime.of(2026,9,11,10,0);
        LocalDateTime end=LocalDateTime.of(2026,9,11,11,0);
        BookingRequest booking=new BookingRequest(1L,start,end);
        RoomRequest room=new RoomRequest("Альфа",8,"1 этаж","Проектор");
        RegisterRequest register=new RegisterRequest("alice","secret1");
        AuthMeResponse me=new AuthMeResponse("alice","ROLE_USER");
        AvailabilityItem item=new AvailabilityItem(1L,"Альфа",start,end,"ACTIVE","alice");
        RoomResponse roomResponse=new RoomResponse(2L,"Бета",12,"2 этаж","TV");
        AdminStatsResponse stats=new AdminStatsResponse(3,5,7,6,1);

        assertAll(
            () -> assertEquals(1L,booking.roomId()),
            () -> assertEquals("Альфа",room.name()),
            () -> assertEquals("alice",register.username()),
            () -> assertEquals("ROLE_USER",me.role()),
            () -> assertEquals("alice",item.username()),
            () -> assertEquals(12,roomResponse.capacity()),
            () -> assertEquals(7,stats.totalBookings())
        );
    }
}
