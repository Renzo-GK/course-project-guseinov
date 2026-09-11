package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import ru.ncfu.meetingroom.presentation.ApiExceptionHandler;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {
    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void mapsBadRequest() {
        assertEquals("bad", handler.badRequest(new IllegalArgumentException("bad")).get("error"));
    }

    @Test
    void mapsConflict() {
        assertEquals("conflict", handler.conflict(new IllegalStateException("conflict")).get("error"));
    }

    @Test
    void mapsForbidden() {
        assertEquals("forbidden", handler.forbidden(new SecurityException("forbidden")).get("error"));
    }
}
