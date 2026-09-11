package ru.ncfu.meetingroom.foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.AuditLog;
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
