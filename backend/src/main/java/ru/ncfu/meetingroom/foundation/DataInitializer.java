package ru.ncfu.meetingroom.foundation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ncfu.meetingroom.entity.MeetingRoom;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.entity.Workspace;
import ru.ncfu.meetingroom.entity.Equipment;

@Configuration
public class DataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner init(UserRepository users,
                           MeetingRoomRepository rooms,
                           PasswordEncoder encoder,
                           WorkspaceRepository workspaces,
                           EquipmentRepository equipment) {
        return args -> {
            if (users.findByUsername("user").isEmpty()) {
                users.save(new User("user", encoder.encode("user123"), "ROLE_USER"));
                log.info("Created demo user: user");
            }

            if (users.findByUsername("admin").isEmpty()) {
                users.save(new User("admin", encoder.encode("admin123"), "ROLE_ADMIN"));
                log.info("Created demo user: admin");
            }

            createRoomIfMissing(rooms, "Альфа", 8, "1 этаж", "Проектор, TV, доска");
            createRoomIfMissing(rooms, "Бета", 12, "2 этаж", "Проектор, видеоконференция");
            createRoomIfMissing(rooms, "Гамма", 20, "2 этаж", "Экран, видеоконференция, доска");
            if (workspaces.count()==0) {
                workspaces.save(new Workspace("Open Space A", "OPEN_SPACE", 20));
                workspaces.save(new Workspace("Coworking B", "COWORKING", 12));
            }
            if (equipment.count()==0) {
                equipment.save(new Equipment("Проектор", "Мультимедийный проектор"));
                equipment.save(new Equipment("Видеоконференция", "Комплект для видеосвязи"));
                equipment.save(new Equipment("TV", "Панель для демонстрации"));
            }

            log.info("Demo data check completed. Users: {}, rooms: {}, workspaces: {}, equipment: {}", users.count(), rooms.count(), workspaces.count(), equipment.count());
        };
    }

    private void createRoomIfMissing(MeetingRoomRepository rooms,
                                     String name,
                                     int capacity,
                                     String location,
                                     String equipment) {
        if (rooms.findAll().stream().noneMatch(room -> room.getName().equals(name))) {
            rooms.save(new MeetingRoom(name, capacity, location, equipment));
            log.info("Created meeting room: {}", name);
        }
    }
}
