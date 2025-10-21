package com.app.doctor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Patient {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    private int age;
    private String gender;

    private String location;
}
