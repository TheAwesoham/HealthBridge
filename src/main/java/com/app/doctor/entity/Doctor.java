package com.app.doctor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Doctor {
    
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String specialization;
    
    private String location;
}
