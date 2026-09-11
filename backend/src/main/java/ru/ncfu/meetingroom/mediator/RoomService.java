package ru.ncfu.meetingroom.mediator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncfu.meetingroom.control.RoomRequest;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.foundation.MeetingRoomRepository;

import java.util.List;

@Service
public class RoomService {
    private final MeetingRoomRepository rooms;
    public RoomService(MeetingRoomRepository rooms){this.rooms=rooms;}
    @Transactional(readOnly=true) public List<MeetingRoom> all(){return rooms.findAll();}
    @Transactional(readOnly=true) public MeetingRoom get(Long id){return rooms.findById(id).orElseThrow();}
    @Transactional public MeetingRoom create(RoomRequest r){return rooms.save(new MeetingRoom(r.name(),r.capacity(),r.location(),r.equipment()));}
    @Transactional public MeetingRoom update(Long id, RoomRequest r){
        MeetingRoom x=get(id); x.setName(r.name()); x.setCapacity(r.capacity()); x.setLocation(r.location()); x.setEquipment(r.equipment()); return x;
    }
    @Transactional public void delete(Long id){rooms.deleteById(id);}
}
