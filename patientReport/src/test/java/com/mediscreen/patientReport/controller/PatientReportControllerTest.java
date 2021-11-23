package com.mediscreen.patientReport.controller;

import com.mediscreen.patientReport.service.PatientReportServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientReportController.class)
public class PatientReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientReportServiceImpl patientReportService;

    @Test
    @DisplayName("POST request (/assess/id) with a existing patient id must return an HTTP 200 response")
    public void postAssessByExistingIdTest() throws Exception {

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/assess/id")
                .param("patId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/assess/id) with an unknown patient id must return an HTTP 404 response")
    public void postAssessByUnknownIdTest() throws Exception {

        //WHEN
        doThrow(FeignException.class).when(patientReportService).calculateRiskLevel(1);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/assess/id")
                .param("patId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST request (/assess/familyName) with a existing family name must return an HTTP 200 response")
    public void postAssessByExistingFamilyNameTest() throws Exception {

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/assess/familyName")
                .param("familyName", "Dupond")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/assess/familyName) with an unknown family name must return an HTTP 404 response")
    public void postAssessByUnknownFamilyNameTest() throws Exception {

        //WHEN
        doThrow(FeignException.class).when(patientReportService).calculateRiskLevelFamily("Dupond");

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/assess/familyName")
                .param("familyName", "Dupond")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
