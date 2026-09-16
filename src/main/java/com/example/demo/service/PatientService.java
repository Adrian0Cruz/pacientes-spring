package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Inyección de dependencias por constructor
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado"));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedData) {
        Patient existingPatient = getPatientById(id); // Reutilizamos el método que lanza 404

        existingPatient.setFirstName(updatedData.getFirstName());
        existingPatient.setLastName(updatedData.getLastName());
        existingPatient.setDocumentNumber(updatedData.getDocumentNumber());
        existingPatient.setPhone(updatedData.getPhone());
        existingPatient.setEmail(updatedData.getEmail());
        existingPatient.setBirthDate(updatedData.getBirthDate());

        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = getPatientById(id); // Validamos que exista antes de borrar
        patientRepository.delete(patient);
    }
}