package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreatePatient_Success() throws Exception {
        // JSON válido con todos los campos obligatorios
        String patientJson = """
                {
                    "firstName": "Juan",
                    "lastName": "Perez",
                    "documentNumber": "123456789",
                    "email": "juan@example.com",
                    "birthDate": "1990-01-01"
                }
                """;

        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientJson))
                .andExpect(status().isCreated()); // Esperamos un 201 Created
    }

    @Test
    public void testCreatePatient_Failure_InvalidData() throws Exception {
        // JSON sin email y sin apellido (debe fallar la validación)
        String invalidPatientJson = """
                {
                    "firstName": "Juan",
                    "documentNumber": "987654321",
                    "birthDate": "1990-01-01"
                }
                """;

        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPatientJson))
                .andExpect(status().isBadRequest()); // Esperamos un 400 Bad Request
    }
}