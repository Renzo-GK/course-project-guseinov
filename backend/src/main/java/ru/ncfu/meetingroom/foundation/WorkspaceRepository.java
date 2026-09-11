package ru.ncfu.meetingroom.foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ncfu.meetingroom.entity.Workspace;
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {}
