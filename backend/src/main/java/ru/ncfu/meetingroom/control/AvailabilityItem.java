package ru.ncfu.meetingroom.control;

import java.time.LocalDateTime;

public record AvailabilityItem(
    Long bookingId,
    String roomName,
    LocalDateTime startAt,
    LocalDateTime endAt,
    String status,
    String username
) {}
