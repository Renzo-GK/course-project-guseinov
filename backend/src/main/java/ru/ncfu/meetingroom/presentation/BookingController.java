package ru.ncfu.meetingroom.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.control.*;
import ru.ncfu.meetingroom.mediator.BookingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(Authentication auth, @Valid @RequestBody BookingRequest request) {
        return BookingResponse.from(service.create(auth.getName(), request.roomId(), request.startAt(), request.endAt()));
    }

    @GetMapping("/mine")
    public List<BookingResponse> mine(Authentication auth) {
        return service.mine(auth.getName()).stream().map(BookingResponse::from).toList();
    }

    @GetMapping("/availability")
    public List<AvailabilityItem> availability(@RequestParam Long roomId, @RequestParam LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return service.availability(roomId, start, end).stream()
            .map(b -> new AvailabilityItem(
                b.getId(),
                b.getRoom().getName(),
                b.getStartAt(),
                b.getEndAt(),
                b.getStatus(),
                b.getUser().getUsername()
            )).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(Authentication auth, @PathVariable Long id) {
        service.cancel(id, auth.getName());
    }
}
