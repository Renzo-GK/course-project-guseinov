package ru.ncfu.meetingroom.foundation;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.Booking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatus(
        Long roomId, LocalDateTime endAt, LocalDateTime startAt, String status);

    @EntityGraph(attributePaths = {"room", "user"})
    List<Booking> findByUserUsernameOrderByStartAtDesc(String username);

    @EntityGraph(attributePaths = {"room", "user"})
    List<Booking> findByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatusOrderByStartAt(
        Long roomId, LocalDateTime endAt, LocalDateTime startAt, String status);

    @EntityGraph(attributePaths = {"room", "user"})
    List<Booking> findAllByOrderByStartAtDesc();

    @EntityGraph(attributePaths = {"room", "user"})
    Optional<Booking> findWithRelationsById(Long id);
    long countByStatus(String status);
}
