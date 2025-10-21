package com.app.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.doctor.entity.Appointment;
import com.app.doctor.repository.AppointmentRepository;

@Service
public class AppointmentCleanupService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;

    //runs everyday at 3 AM 
    @Scheduled(cron = "0 0 3 * * ?")
    public void removeExpiredAppointments() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        allAppointments.stream()
            .filter(app -> app.getAppointmentTime() != null && app.getAppointmentTime().isBefore(now))
            .forEach(app -> appointmentRepository.deleteById(app.getId()));
    }
}