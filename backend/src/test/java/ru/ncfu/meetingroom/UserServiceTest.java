package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.foundation.UserRepository;
import ru.ncfu.meetingroom.mediator.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private final UserRepository users = mock(UserRepository.class);
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserService service = new UserService(users, encoder);

    @Test
    void registerCreatesEncodedUser() {
        when(users.findByUsername("alice")).thenReturn(Optional.empty());
        when(users.save(any(User.class))).thenAnswer(inv -> {
            User u = inv.getArgument(0);
            u.setId(10L);
            return u;
        });

        User result = service.register("alice", "secret1");

        assertEquals(10L, result.getId());
        assertEquals("alice", result.getUsername());
        assertEquals("ROLE_USER", result.getRole());
        assertTrue(encoder.matches("secret1", result.getPassword()));
        verify(users).save(any(User.class));
    }

    @Test
    void registerRejectsDuplicate() {
        when(users.findByUsername("alice")).thenReturn(Optional.of(new User("alice", "hash", "ROLE_USER")));
        assertThrows(IllegalStateException.class, () -> service.register("alice", "secret1"));
        verify(users, never()).save(any());
    }

    @Test
    void countDelegates() {
        when(users.count()).thenReturn(7L);
        assertEquals(7L, service.count());
    }
}
