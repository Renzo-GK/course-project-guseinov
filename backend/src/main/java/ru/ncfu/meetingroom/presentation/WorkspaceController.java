package ru.ncfu.meetingroom.presentation;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.entity.Workspace;
import ru.ncfu.meetingroom.foundation.WorkspaceRepository;
import java.util.List;
@RestController @RequestMapping("/api/workspaces")
public class WorkspaceController {
    private final WorkspaceRepository repo; public WorkspaceController(WorkspaceRepository repo){this.repo=repo;}
    @GetMapping public List<Workspace> all(){return repo.findAll();}
}
