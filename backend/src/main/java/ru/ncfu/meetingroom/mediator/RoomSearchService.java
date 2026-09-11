package ru.ncfu.meetingroom.mediator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.foundation.MeetingRoomRepository;
import java.util.List;
@Service
public class RoomSearchService {
    private final MeetingRoomRepository rooms;
    public RoomSearchService(MeetingRoomRepository rooms){this.rooms=rooms;}
    @Transactional(readOnly=true)
    public List<MeetingRoom> search(String query, Integer minCapacity){
        String q=query==null?"":query.trim().toLowerCase();
        return rooms.findAll().stream().filter(r ->
            (q.isBlank() || (r.getName()+" "+r.getLocation()+" "+r.getEquipment()).toLowerCase().contains(q)) &&
            (minCapacity==null || r.getCapacity()>=minCapacity)
        ).toList();
    }
}
