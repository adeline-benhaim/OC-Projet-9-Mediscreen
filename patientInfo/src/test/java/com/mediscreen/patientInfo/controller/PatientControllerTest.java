package com.mediscreen.patientInfo.controller;

import com.mediscreen.patientInfo.config.DataSourceTest;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientServiceImpl patientService;

    @Test
    @DisplayName("GET request (/patientInfo) must return an HTTP 200 response")
    public void getAllPatientsTest() throws Exception {

        //GIVEN

        //THEN
        mockMvc.perform(get("/patientInfo"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{firstName}/{lastName}) with an exiting patient must return an HTTP 200 response")
    void getPatientListTest() throws Exception {

        //GIVEN
        List<Patient>patientList = new ArrayList<>();
        Patient patient = Patient.builder().firstName("firstname1").lastName("lastname1").build();
        patientList.add(patient);

        //WHEN
        when(patientService.getPatientListByFistNameAndLastName("firstname1", "lastname1")).thenReturn(patientList);

        //THEN
        mockMvc.perform(get("/patientInfo/search/firstname1/lastname1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{firstName}/{lastName}) with an unknown patient must return an HTTP 404 response")
    void getPatientListWithUnknownFirstAndLastNameTest() throws Exception {

        //WHEN
        when(patientService.getPatientListByFistNameAndLastName("firstname8", "lastname8")).thenThrow(new PatientNotFoundException(""));

        //THEN
        mockMvc.perform(get("/patientInfo/search/firstname8/lastname8"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{id}) with an exiting patient must return an HTTP 200 response")
    void getPatientById() throws Exception {

        //GIVEN
        List<Patient>patientList = new ArrayList<>();
        Patient patient = Patient.builder().firstName("firstname1").lastName("lastname1").build();
        patientList.add(patient);

        //WHEN
        when(patientService.getPatientById(0)).thenReturn(java.util.Optional.ofNullable(patientList.get(0)));

        //THEN
        mockMvc.perform(get("/patientInfo/search/0"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{id}) with an unknown id patient must return an HTTP 404 response")
    void getPatientByUnknownId() throws Exception {

        //WHEN
        when(patientService.getPatientById(2)).thenThrow(new PatientNotFoundException(""));

        //THEN
        mockMvc.perform(get("/patientInfo/search/2"))
                .andExpect(status().isNotFound());
    }
}
