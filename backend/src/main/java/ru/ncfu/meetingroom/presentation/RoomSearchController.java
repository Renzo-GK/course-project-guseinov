package ru.ncfu.meetingroom.presentation;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.control.RoomMapper;
import ru.ncfu.meetingroom.control.RoomResponse;
import ru.ncfu.meetingroom.mediator.RoomSearchService;
import java.util.List;
@RestController @RequestMapping("/api/rooms")
public class RoomSearchController {
    private final RoomSearchService service; private final RoomMapper mapper;
    public RoomSearchController(RoomSearchService service, RoomMapper mapper){this.service=service;this.mapper=mapper;}
    @GetMapping("/search") public List<RoomResponse> search(@RequestParam(required=false) String query,@RequestParam(required=false) Integer minCapacity){return service.search(query,minCapacity).stream().map(mapper::toResponse).toList();}
}
