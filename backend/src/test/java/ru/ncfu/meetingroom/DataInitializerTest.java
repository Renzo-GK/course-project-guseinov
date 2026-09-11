package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.foundation.DataInitializer;
import ru.ncfu.meetingroom.foundation.MeetingRoomRepository;
import ru.ncfu.meetingroom.foundation.UserRepository;
import ru.ncfu.meetingroom.foundation.WorkspaceRepository;
import ru.ncfu.meetingroom.foundation.EquipmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataInitializerTest {
    @Test
    void createsDemoUsersAndRoomsWhenAbsent() throws Exception {
        UserRepository users=mock(UserRepository.class);
        MeetingRoomRepository rooms=mock(MeetingRoomRepository.class);
        WorkspaceRepository workspaces=mock(WorkspaceRepository.class);
        EquipmentRepository equipment=mock(EquipmentRepository.class);
        when(users.findByUsername(any())).thenReturn(Optional.empty());
        when(users.count()).thenReturn(2L);
        when(rooms.findAll()).thenReturn(new ArrayList<>());
        when(rooms.count()).thenReturn(3L);
        when(workspaces.count()).thenReturn(0L);
        when(equipment.count()).thenReturn(0L);
        when(workspaces.save(any())).thenAnswer(i->i.getArgument(0));
        when(equipment.save(any())).thenAnswer(i->i.getArgument(0));
        when(users.save(any())).thenAnswer(i->i.getArgument(0));
        when(rooms.save(any(MeetingRoom.class))).thenAnswer(i->i.getArgument(0));

        new DataInitializer().init(users,rooms,new BCryptPasswordEncoder(),workspaces,equipment).run();

        verify(users,times(2)).save(any());
        verify(rooms,times(3)).save(any(MeetingRoom.class));
        verify(workspaces,times(2)).save(any());
        verify(equipment,times(3)).save(any());
    }
}
