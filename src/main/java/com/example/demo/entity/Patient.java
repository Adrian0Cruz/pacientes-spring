package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "El documento es obligatorio")
    @Column(nullable = false, unique = true)
    private String documentNumber;

    private String phone;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un formato de email válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(nullable = false)
    private LocalDate birthDate;

    // 1. Constructor vacío (ESTRICTAMENTE OBLIGATORIO para JPA)
    public Patient() {
    }

    // 2. Constructor para crear nuevos pacientes (Sin ID, lo genera la BD)
    public Patient(String firstName, String lastName, String documentNumber, String phone, String email,
            LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
    }

    // 3. Constructor con todos los campos incluyendo ID (Opcional, muy útil para
    // hacer Tests)
    public Patient(Long id, String firstName, String lastName, String documentNumber, String phone, String email,
            LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.email = email;
        this.birthDate = birthDate;
    }

    // --- GETTERS Y SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Patient [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", documentNumber="
                + documentNumber + ", phone=" + phone + ", email=" + email + ", birthDate=" + birthDate + "]";
    }
}