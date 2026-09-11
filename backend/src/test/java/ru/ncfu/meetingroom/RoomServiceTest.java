package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import ru.ncfu.meetingroom.control.RoomRequest;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.foundation.MeetingRoomRepository;
import ru.ncfu.meetingroom.mediator.RoomService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {
    private final MeetingRoomRepository rooms = mock(MeetingRoomRepository.class);
    private final RoomService service = new RoomService(rooms);

    @Test
    void createsRoom() {
        RoomRequest request = new RoomRequest("Дельта", 6, "3 этаж", "TV");
        when(rooms.save(any(MeetingRoom.class))).thenAnswer(inv -> inv.getArgument(0));

        MeetingRoom result = service.create(request);

        assertEquals("Дельта", result.getName());
        assertEquals(6, result.getCapacity());
    }

    @Test
    void updatesRoom() {
        MeetingRoom existing = new MeetingRoom("Альфа", 8, "1 этаж", "Проектор");
        when(rooms.findById(1L)).thenReturn(Optional.of(existing));

        RoomRequest request = new RoomRequest("Альфа+", 10, "2 этаж", "TV");
        MeetingRoom result = service.update(1L, request);

        assertEquals("Альфа+", result.getName());
        assertEquals(10, result.getCapacity());
        assertEquals("2 этаж", result.getLocation());
        assertEquals("TV", result.getEquipment());
    }
}
