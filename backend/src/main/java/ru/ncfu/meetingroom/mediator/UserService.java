package ru.ncfu.meetingroom.mediator;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.foundation.UserRepository;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public User register(String username, String password) {
        if (users.findByUsername(username).isPresent()) {
            throw new IllegalStateException("Пользователь уже существует");
        }
        return users.save(new User(username, encoder.encode(password), "ROLE_USER"));
    }

    @Transactional(readOnly = true)
    public long count() {
        return users.count();
    }
}
