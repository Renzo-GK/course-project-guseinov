package ru.ncfu.meetingroom.foundation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
