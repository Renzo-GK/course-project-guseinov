package ru.ncfu.meetingroom.presentation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ncfu.meetingroom.auth.JwtService;
import ru.ncfu.meetingroom.control.*;
import ru.ncfu.meetingroom.entity.User;
import ru.ncfu.meetingroom.mediator.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService users, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users=users; this.authenticationManager=authenticationManager; this.jwtService=jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails details = (UserDetails) auth.getPrincipal();
        String role = details.getAuthorities().stream().findFirst().map(a->a.getAuthority()).orElse("ROLE_USER");
        return new LoginResponse(jwtService.generateToken(details), details.getUsername(), role);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String,Object> register(@Valid @RequestBody RegisterRequest r) {
        User u=users.register(r.username(),r.password());
        return Map.of("id",u.getId(),"username",u.getUsername(),"role",u.getRole());
    }

    @GetMapping("/me")
    public AuthMeResponse me(Authentication authentication) {
        String role=authentication.getAuthorities().stream().map(a->a.getAuthority()).findFirst().orElse("ROLE_USER");
        return new AuthMeResponse(authentication.getName(),role);
    }
}
