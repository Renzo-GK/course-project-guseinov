package ru.ncfu.meetingroom.control;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RoomRequest(
    @NotBlank String name,
    @Min(1) int capacity,
    String location,
    String equipment
) {}
