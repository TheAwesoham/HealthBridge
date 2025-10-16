package com.app.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.doctor.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    List<Doctor> findBySpecializationIgnoreCase(String specialization);
    List<Doctor> findByLocationIgnoreCase(String location);
}
