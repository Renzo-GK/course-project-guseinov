package ru.ncfu.meetingroom.presentation;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.entity.Equipment;
import ru.ncfu.meetingroom.foundation.EquipmentRepository;
import java.util.List;
@RestController @RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentRepository repo; public EquipmentController(EquipmentRepository repo){this.repo=repo;}
    @GetMapping public List<Equipment> all(){return repo.findAll();}
}
