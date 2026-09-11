package ru.ncfu.meetingroom;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import ru.ncfu.meetingroom.auth.JwtService;
import ru.ncfu.meetingroom.control.AuthMeResponse;
import ru.ncfu.meetingroom.control.RegisterRequest;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.mediator.UserService;
import ru.ncfu.meetingroom.presentation.AuthController;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Test
    void meReturnsPrincipalAndRole() {
        UserService service = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtService jwtService = mock(JwtService.class);
        AuthController controller = new AuthController(service, authenticationManager, jwtService);
        var auth = new UsernamePasswordAuthenticationToken("alice", "n/a", List.of(() -> "ROLE_USER"));
        AuthMeResponse response = controller.me(auth);
        assertEquals("alice", response.username());
        assertEquals("ROLE_USER", response.role());
    }

    @Test
    void registerReturnsSafeFields() {
        UserService service = mock(UserService.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtService jwtService = mock(JwtService.class);
        AuthController controller = new AuthController(service, authenticationManager, jwtService);
        User user = new User("alice", "encoded", "ROLE_USER");
        user.setId(12L);
        when(service.register("alice", "secret1")).thenReturn(user);

        Map<String,Object> response = controller.register(new RegisterRequest("alice", "secret1"));

        assertEquals(12L, response.get("id"));
        assertEquals("alice", response.get("username"));
        assertEquals("ROLE_USER", response.get("role"));
        assertFalse(response.containsKey("password"));
    }
}
