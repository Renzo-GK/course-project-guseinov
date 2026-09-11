package ru.ncfu.meetingroom.control;

public record RoomResponse(Long id, String name, int capacity, String location, String equipment) {}
