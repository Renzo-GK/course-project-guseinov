package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import ru.ncfu.meetingroom.control.RoomMapper;
import ru.ncfu.meetingroom.control.RoomResponse;
import ru.ncfu.meetingroom.entity.MeetingRoom;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {
    @Test
    void mapsRoom() {
        MeetingRoom room = new MeetingRoom("Дельта", 6, "3 этаж", "TV");
        room.setId(4L);
        RoomResponse response = new RoomMapper().toResponse(room);
        assertEquals(4L, response.id());
        assertEquals("Дельта", response.name());
        assertEquals(6, response.capacity());
        assertEquals("3 этаж", response.location());
        assertEquals("TV", response.equipment());
    }
}
