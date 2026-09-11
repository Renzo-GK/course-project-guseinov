package ru.ncfu.meetingroom.control;

public record AdminStatsResponse(
    long totalRooms,
    long totalUsers,
    long totalBookings,
    long activeBookings,
    long cancelledBookings
) {}
