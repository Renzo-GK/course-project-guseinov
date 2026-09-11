package ru.ncfu.meetingroom.control;

import ru.ncfu.meetingroom.entity.Booking;
import java.time.LocalDateTime;

public record BookingResponse(Long id, Long roomId, String roomName, String username, LocalDateTime startAt, LocalDateTime endAt, String status) {
    public static BookingResponse from(Booking b){
        return new BookingResponse(b.getId(), b.getRoom().getId(), b.getRoom().getName(), b.getUser().getUsername(), b.getStartAt(), b.getEndAt(), b.getStatus());
    }
}
