package ru.ncfu.meetingroom.foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.Building;
public interface BuildingRepository extends JpaRepository<Building, Long> {}
