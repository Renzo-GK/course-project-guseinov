package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bookings")
public class Booking {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private User user;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    private MeetingRoom room;

    @Column(nullable=false)
    private LocalDateTime startAt;
    @Column(nullable=false)
    private LocalDateTime endAt;

    @Column(nullable=false)
    private String status = "ACTIVE";

    public Booking() {}
    public Booking(User user, MeetingRoom room, LocalDateTime startAt, LocalDateTime endAt) {
        this.user=user; this.room=room; this.startAt=startAt; this.endAt=endAt;
    }
    public Long getId(){return id;}
    public User getUser(){return user;}
    public MeetingRoom getRoom(){return room;}
    public LocalDateTime getStartAt(){return startAt;}
    public LocalDateTime getEndAt(){return endAt;}
    public String getStatus(){return status;}
    public void setId(Long id){this.id=id;}
    public void setUser(User user){this.user=user;}
    public void setRoom(MeetingRoom room){this.room=room;}
    public void setStartAt(LocalDateTime startAt){this.startAt=startAt;}
    public void setEndAt(LocalDateTime endAt){this.endAt=endAt;}
    public void setStatus(String status){this.status=status;}
}
