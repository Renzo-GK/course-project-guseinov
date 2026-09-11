package ru.ncfu.meetingroom.control;

import org.springframework.stereotype.Component;
import ru.ncfu.meetingroom.entity.MeetingRoom;

@Component
public class RoomMapper {
    public RoomResponse toResponse(MeetingRoom room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity(), room.getLocation(), room.getEquipment());
    }
}
