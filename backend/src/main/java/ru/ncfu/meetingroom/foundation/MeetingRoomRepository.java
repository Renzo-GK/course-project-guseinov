package ru.ncfu.meetingroom.foundation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.MeetingRoom;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {}
