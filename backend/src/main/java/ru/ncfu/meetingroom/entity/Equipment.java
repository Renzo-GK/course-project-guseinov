package ru.ncfu.meetingroom.entity;

import jakarta.persistence.*;

@Entity
@Table(name="equipment")
public class Equipment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true) private String name;
    private String description;
    public Equipment() {}
    public Equipment(String name,String description){this.name=name;this.description=description;}
    public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;}
    public void setId(Long id){this.id=id;} public void setName(String name){this.name=name;} public void setDescription(String description){this.description=description;}
}
