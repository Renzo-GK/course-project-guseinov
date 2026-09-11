package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name="buildings")
public class Building {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String name;
    private String address;
    public Building() {}
    public Building(String name, String address){this.name=name;this.address=address;}
    public Long getId(){return id;} public String getName(){return name;} public String getAddress(){return address;}
    public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setAddress(String address){this.address=address;}
}
