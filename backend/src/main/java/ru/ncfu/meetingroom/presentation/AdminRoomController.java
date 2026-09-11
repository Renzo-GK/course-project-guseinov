package ru.ncfu.meetingroom.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.control.*;
import ru.ncfu.meetingroom.mediator.BookingService;
import ru.ncfu.meetingroom.mediator.RoomService;
import ru.ncfu.meetingroom.mediator.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoomController {
    private final RoomService rooms;
    private final BookingService bookings;
    private final UserService users;
    private final RoomMapper mapper;

    public AdminRoomController(RoomService rooms, BookingService bookings, UserService users, RoomMapper mapper) {
        this.rooms = rooms;
        this.bookings = bookings;
        this.users = users;
        this.mapper = mapper;
    }

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@Valid @RequestBody RoomRequest request) {
        return mapper.toResponse(rooms.create(request));
    }

    @PutMapping("/rooms/{id}")
    public RoomResponse update(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        return mapper.toResponse(rooms.update(id, request));
    }

    @DeleteMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        rooms.delete(id);
    }

    @GetMapping("/bookings")
    public List<BookingResponse> bookings() {
        return bookings.all().stream().map(BookingResponse::from).toList();
    }

    @GetMapping("/stats")
    public AdminStatsResponse stats() {
        long totalBookings = bookings.all().size();
        return new AdminStatsResponse(
            rooms.all().size(),
            users.count(),
            totalBookings,
            bookings.countActive(),
            bookings.countCancelled()
        );
    }
}
