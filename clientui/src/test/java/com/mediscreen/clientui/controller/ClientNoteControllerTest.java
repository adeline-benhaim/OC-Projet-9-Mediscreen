package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.AppointmentBean;
import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.service.ClientInfoService;
import com.mediscreen.clientui.service.ClientNoteService;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
public class ClientNoteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ClientInfoService clientInfoService;
    @MockBean
    ClientNoteService clientNoteService;

    @Test
    @DisplayName("GET request (/note/{id}) must return note view")
    public void getAppointmentByIdTest() throws Exception {

        AppointmentBean appointmentBean = AppointmentBean.builder().appointmentId("1").patId(1).note("note").doctorName("name").date(LocalDateTime.now(ZoneId.of("Europe/Paris"))).build();
        when(clientNoteService.getAppointmentById("1")).thenReturn(appointmentBean);
        PatientBean patientBean = PatientBean.builder().build();
        when(clientInfoService.getPatientById(appointmentBean.getPatId())).thenReturn(patientBean);

        mockMvc.perform(get("/note/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("NoteView"));
    }

    @Test
    @DisplayName("GET request (/note/{id}) with unknown appointment id must return list of patients")
    public void getAppointmentByUnknownIdTest() throws Exception {

        when(clientNoteService.getAppointmentById("1")).thenThrow(FeignException.class);

        mockMvc.perform(get("/note/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    @DisplayName("POST request (/patHistory/add) must save a new note")
    public void postNewNoteTest() throws Exception {

        mockMvc.perform(post("/patHistory/add"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/searchById/0"));
    }

    @Test
    @DisplayName("GET request (/updateNote/{id}) must return form update note")
    public void getAppointmentByIdForUpdateTest() throws Exception {

        AppointmentBean appointmentBean = AppointmentBean.builder().build();
        when(clientNoteService.getAppointmentById("1")).thenReturn(appointmentBean);
        PatientBean patientBean = PatientBean.builder().build();
        when(clientInfoService.getPatientById(appointmentBean.getPatId())).thenReturn(patientBean);

        mockMvc.perform(get("/updateNote/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("FormUpdateNote"));
    }

    @Test
    @DisplayName("GET request (/updateNote/{id}) with unknown appointment id must return list of patients")
    public void getAppointmentByUnknownIdForUpdateTest() throws Exception {

        when(clientNoteService.getAppointmentById("1")).thenThrow(FeignException.class);

        mockMvc.perform(get("/updateNote/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("POST request (/patHistory/update) must update a note")
    public void postUpdatingNoteTest() throws Exception {

        AppointmentBean appointmentBean = AppointmentBean.builder().build();
        when(clientNoteService.updateNote(appointmentBean)).thenReturn(appointmentBean);

        mockMvc.perform(post("/patHistory/update"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/searchById/0"));
    }
}
