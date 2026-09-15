package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // No necesitas escribir nada aquí adentro por ahora.
    // JpaRepository ya incluye save(), findById(), findAll(), deleteById(), etc.

}