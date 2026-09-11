package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
public class Notification {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) private User user;
    @Column(nullable=false) private String message;
    @Column(nullable=false) private LocalDateTime createdAt;
    private boolean readFlag;
    public Notification() {}
    public Notification(User user,String message){this.user=user;this.message=message;this.createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public User getUser(){return user;} public String getMessage(){return message;} public LocalDateTime getCreatedAt(){return createdAt;} public boolean isReadFlag(){return readFlag;}
    public void setId(Long id){this.id=id;} public void setUser(User user){this.user=user;} public void setMessage(String message){this.message=message;} public void setCreatedAt(LocalDateTime x){this.createdAt=x;} public void setReadFlag(boolean x){this.readFlag=x;}
}
