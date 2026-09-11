package ru.ncfu.meetingroom.control;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BookingRequest(
    @NotNull Long roomId,
    @NotNull LocalDateTime startAt,
    @NotNull LocalDateTime endAt
) {}
