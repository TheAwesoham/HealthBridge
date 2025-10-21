package com.app.doctor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.doctor.entity.Appointment;
import com.app.doctor.entity.Doctor;
import com.app.doctor.entity.Patient;
import com.app.doctor.repository.AppointmentRepository;
import com.app.doctor.repository.DoctorRepository;
import com.app.doctor.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Transactional
    public Appointment bookAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (patientOpt.isPresent() && doctorOpt.isPresent()) {
            //check for double booking of the doctor at the same time
            boolean isBooked = appointmentRepository.findAll().stream()
                .anyMatch(a -> a.getDoctor().getId().equals(doctorId)
                        && a.getAppointmentTime().equals(appointmentTime));
            if (isBooked) {
                //if doctor already has an appointment at that time
                return null;
            }
            Appointment appointment = new Appointment();
            appointment.setPatient(patientOpt.get());
            appointment.setDoctor(doctorOpt.get());
            appointment.setAppointmentTime(appointmentTime);


            Appointment savedAppointment = appointmentRepository.save(appointment);

            return savedAppointment;
        }
        return null;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public Appointment updateAppointmentTime(Long appointmentId, LocalDateTime newTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("appointment not found with id " + appointmentId));
        appointment.setAppointmentTime(newTime);
        return appointmentRepository.save(appointment);
    }

    public boolean deleteAppointment(Long id) {
        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

}