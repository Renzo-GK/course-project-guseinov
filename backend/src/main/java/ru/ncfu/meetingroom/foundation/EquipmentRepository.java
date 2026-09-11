package ru.ncfu.meetingroom.foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.Equipment;
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {}
