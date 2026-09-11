package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name="meeting_rooms")
public class MeetingRoom {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private int capacity;
    private String location;
    private String equipment;

    public MeetingRoom() {}
    public MeetingRoom(String name, int capacity, String location, String equipment) {
        this.name=name; this.capacity=capacity; this.location=location; this.equipment=equipment;
    }
    public Long getId(){return id;}
    public String getName(){return name;}
    public int getCapacity(){return capacity;}
    public String getLocation(){return location;}
    public String getEquipment(){return equipment;}
    public void setId(Long id){this.id=id;}
    public void setName(String name){this.name=name;}
    public void setCapacity(int capacity){this.capacity=capacity;}
    public void setLocation(String location){this.location=location;}
    public void setEquipment(String equipment){this.equipment=equipment;}
}
