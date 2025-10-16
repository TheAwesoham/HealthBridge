package com.app.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.doctor.entity.Patient;
import com.app.doctor.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient (Patient patient){
        return patientRepository.save(patient);
    }
    
    public Optional<Patient> getPatientById(Long id){
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Patient updatepPatient(Long id, Patient updatedPatient){
        return patientRepository.findById(id).map(patient -> {
            patient.setName(updatedPatient.getName());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new RuntimeException("patient not found with id " + id));
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("patient not found with id " + id);
        }
        patientRepository.deleteById(id);
    }
}