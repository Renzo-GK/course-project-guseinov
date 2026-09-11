package ru.ncfu.meetingroom.control;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ncfu.meetingroom.auth.JwtAuthenticationFilter;
import ru.ncfu.meetingroom.foundation.UserRepository;

@Configuration
@org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
    @Bean UserDetailsService userDetailsService(UserRepository repo){
        return username -> repo.findByUsername(username)
            .map(u -> User.withUsername(u.getUsername()).password(u.getPassword()).roles(u.getRole().replace("ROLE_","")).build())
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }
    @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{return config.getAuthenticationManager();}
    @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception{
        http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a->a
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS,"/**").permitAll()
                .requestMatchers("/api/auth/login","/api/auth/register","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll()
                .requestMatchers("/api/auth/me","/api/rooms/**","/api/bookings/**","/api/workspaces/**","/api/equipment/**").authenticated()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
