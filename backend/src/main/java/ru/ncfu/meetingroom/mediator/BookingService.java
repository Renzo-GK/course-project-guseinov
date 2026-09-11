package ru.ncfu.meetingroom.mediator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncfu.meetingroom.entity.*;
import ru.ncfu.meetingroom.foundation.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookings;
    private final MeetingRoomRepository rooms;
    private final UserRepository users;

    public BookingService(BookingRepository bookings, MeetingRoomRepository rooms, UserRepository users) {
        this.bookings=bookings; this.rooms=rooms; this.users=users;
    }

    @Transactional
    public Booking create(String username, Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt == null || endAt == null || !startAt.isBefore(endAt))
            throw new IllegalArgumentException("Некорректный интервал бронирования");
        if (bookings.existsByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatus(roomId, endAt, startAt, "ACTIVE"))
            throw new IllegalStateException("Переговорная уже занята в указанном интервале");
        User user = users.findByUsername(username).orElseThrow();
        MeetingRoom room = rooms.findById(roomId).orElseThrow();
        Booking saved = bookings.save(new Booking(user, room, startAt, endAt));
        return bookings.findWithRelationsById(saved.getId()).orElseThrow();
    }

    @Transactional(readOnly=true)
    public List<Booking> mine(String username) {
        return bookings.findByUserUsernameOrderByStartAtDesc(username);
    }

    @Transactional(readOnly=true)
    public List<Booking> availability(Long roomId, LocalDateTime startAt, LocalDateTime endAt) {
        return bookings.findByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatusOrderByStartAt(roomId, endAt, startAt, "ACTIVE");
    }

    @Transactional
    public void cancel(Long bookingId, String username) {
        Booking booking = bookings.findWithRelationsById(bookingId).orElseThrow();
        if (!booking.getUser().getUsername().equals(username)) throw new SecurityException("Недостаточно прав");
        booking.setStatus("CANCELLED");
    }

    @Transactional(readOnly=true)
    public List<Booking> all() { return bookings.findAllByOrderByStartAtDesc(); }

    @Transactional(readOnly=true)
    public long countActive(){ return bookings.countByStatus("ACTIVE"); }

    @Transactional(readOnly=true)
    public long countCancelled(){ return bookings.countByStatus("CANCELLED"); }
}
