package com.mediscreen.patientNote.controller;

import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientServiceImpl;
import com.mediscreen.patientNote.exceptions.AppointmentNotFoundException;
import com.mediscreen.patientNote.model.Appointment;
import com.mediscreen.patientNote.service.NoteServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NoteController.class)
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NoteServiceImpl noteService;

    @Test
    @DisplayName("GET request (/patientNote/{patId}) with an existing patient id must return an HTTP 200 response")
    public void getAllNotesByPatIdTest() throws Exception {

        //GIVEN
        List<Appointment> appointmentList = new ArrayList<>();
        Appointment appointment = Appointment.builder().patId(1).build();
        appointmentList.add(appointment);
        Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("date")));
        Page<Appointment> appointmentPage = new PageImpl<>(appointmentList,pageable,appointment.getAppointmentId());
        Pair<List<Appointment>, Long> listLongPair = Pair.of(appointmentList,appointmentPage.getTotalElements());

        //WHEN
        Mockito.when(noteService.findByPatId(1, pageable )).thenReturn(listLongPair);

        //THEN
        mockMvc.perform(get("/patientNote/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/appointment/{id}) with a appointment id must return an HTTP 200 response")
    public void getAppointmentByIdTest() throws Exception {

        //GIVEN
        Appointment appointment = Appointment.builder().appointmentId(1).build();

        //WHEN
        Mockito.when(noteService.findByAppointmentId(1)).thenReturn(java.util.Optional.ofNullable(appointment));

        //THEN
        mockMvc.perform(get("/appointment/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/appointment/{id}) with an unknowns appointment id must return an HTTP 404 response")
    public void getAppointmentByUnknownIdTest() throws Exception {

        //WHEN
        when(noteService.findByAppointmentId(1)).thenThrow(new AppointmentNotFoundException(""));

        //THEN
        mockMvc.perform(get("/appointment/1"))
                .andExpect(status().isNotFound());
    }
}