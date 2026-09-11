package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name="workspaces")
public class Workspace {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    private String type;
    private int capacity;
    public Workspace() {}
    public Workspace(String name,String type,int capacity){this.name=name;this.type=type;this.capacity=capacity;}
    public Long getId(){return id;} public String getName(){return name;} public String getType(){return type;} public int getCapacity(){return capacity;}
    public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setType(String type){this.type=type;} public void setCapacity(int capacity){this.capacity=capacity;}
}
