package ru.ncfu.meetingroom.presentation;

import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.control.RoomMapper;
import ru.ncfu.meetingroom.control.RoomResponse;
import ru.ncfu.meetingroom.mediator.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService rooms;
    private final RoomMapper mapper;

    public RoomController(RoomService rooms, RoomMapper mapper) {
        this.rooms = rooms;
        this.mapper = mapper;
    }

    @GetMapping
    public List<RoomResponse> all() {
        return rooms.all().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RoomResponse one(@PathVariable Long id) {
        return mapper.toResponse(rooms.get(id));
    }
}
