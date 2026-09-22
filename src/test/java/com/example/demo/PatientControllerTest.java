package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional; // <-- NUEVO IMPORT
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // <-- NUEVA ANOTACIÓN: Deshace los cambios en la BD al terminar el test
@SuppressWarnings("null")
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser // Simula un usuario autenticado para que pase el filtro
    public void testCreatePatient_Success() throws Exception {
        // ... (el resto del código se queda exactamente igual)
        String patientJson = """
                {
                    "firstName": "Juan",
                    "lastName": "Perez",
                    "documentNumber": "12345678999",
                    "email": "juan999@example.com",
                    "birthDate": "1990-01-01"
                }
                """;

        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientJson))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
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

    @Test
    public void testCreatePatient_Unauthorized() throws Exception {
        // Al no tener @WithMockUser ni enviar token, debe dar 401
        String patientJson = """
                {
                    "firstName": "Juan",
                    "lastName": "Perez",
                    "documentNumber": "12345678999",
                    "email": "juan999@example.com",
                    "birthDate": "1990-01-01"
                }
                """;
        mockMvc.perform(post("/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(patientJson))
                .andExpect(status().isUnauthorized());
    }
}