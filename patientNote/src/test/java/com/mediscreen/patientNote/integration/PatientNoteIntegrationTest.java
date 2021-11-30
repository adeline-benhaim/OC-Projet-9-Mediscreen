package com.mediscreen.patientNote.integration;

import com.mediscreen.patientNote.controller.NoteController;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.mediscreen.patientNote.config.DataSourceTest.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NoteController.class)
public class PatientNoteIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NoteService noteService;

    @Test
    @DisplayName("GET request (/patientNote/{patId}) must return a patient list of appointment found by patient id")
    public void getPatientAppointmentByPatIdTest() throws Exception {

        mockMvc.perform(get("/patientNote/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/patientNote/{patId}) with unknown patId must return an HTTP 200 response")
    public void getPatientAppointmentByUnknownPatIdTest() throws Exception {

        mockMvc.perform(get("/patientNote/50"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/appointment/{id}}) must return an appointment found by id")
    public void getAppointmentByIdTest() throws Exception {

        mockMvc.perform(get("/appointment/61a0d9fd918f0c0fe7d71235"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/appointment/add) return an HTTP 200 response")
    public void postNewAppointmentTest() throws Exception {

        Appointment appointment = Appointment.builder().patId(1).note("nouvelle note").doctorName("doctor 1").build();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/appointment/add")
                .content(asJsonString(appointment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT request (/note/update) with no existing appointment id return an HTTP 404 response")
    public void putAppointmentWithUnknownIdTest() throws Exception {

        //GIVEN
        Appointment appointment = Appointment.builder().appointmentId("5656").patId(1).note("nouvelle note").doctorName("doctor 1").doctorName("doctor 1").build();

        //THEN

        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(appointment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
