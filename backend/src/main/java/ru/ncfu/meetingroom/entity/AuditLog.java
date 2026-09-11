package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="audit_logs")
public class AuditLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String action;
    @Column(nullable=false) private LocalDateTime createdAt;
    public AuditLog() {}
    public AuditLog(String username,String action){this.username=username;this.action=action;this.createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public String getUsername(){return username;} public String getAction(){return action;} public LocalDateTime getCreatedAt(){return createdAt;}
    public void setId(Long id){this.id=id;} public void setUsername(String username){this.username=username;} public void setAction(String action){this.action=action;} public void setCreatedAt(LocalDateTime x){this.createdAt=x;}
}
