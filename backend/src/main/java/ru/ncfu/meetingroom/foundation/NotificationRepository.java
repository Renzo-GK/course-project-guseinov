package ru.ncfu.meetingroom.foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Long> {}
