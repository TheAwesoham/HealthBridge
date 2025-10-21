package com.app.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.doctor.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}