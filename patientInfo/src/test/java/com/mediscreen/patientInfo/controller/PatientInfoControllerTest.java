package com.mediscreen.patientInfo.controller;

import com.mediscreen.patientInfo.exceptions.PatientAlreadyExistException;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientInfoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.mediscreen.patientInfo.config.DataSourceTest.asJsonString;
import static com.mediscreen.patientInfo.model.Patient.Sex.F;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientInfoController.class)
public class PatientInfoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientInfoServiceImpl patientService;

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
        Mockito.when(patientService.getPatientListByFistNameAndLastName("firstname1", "lastname1")).thenReturn(patientList);

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
    void getPatientByIdTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder().firstName("firstname1").lastName("lastname1").build();

        //WHEN
        when(patientService.getPatientById(0)).thenReturn(java.util.Optional.ofNullable(patient));

        //THEN
        mockMvc.perform(get("/patientInfo/search/0"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET request (/patientInfo/search/{id}) with an unknown id patient must return an HTTP 404 response")
    void getPatientByUnknownIdTest() throws Exception {

        //WHEN
        when(patientService.getPatientById(2)).thenThrow(new PatientNotFoundException(""));

        //THEN
        mockMvc.perform(get("/patientInfo/search/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with a existing patient must return an HTTP 200 response")
    public void putExistingPatient() throws Exception {

        //GIVEN
        Patient patient = Patient.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(asJsonString(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with an unknown patient id must return an HTTP 400 response")
    public void putNoExistingPatient() throws Exception {

        //GIVEN
        Patient patient = Patient.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .build();

        doThrow(PatientNotFoundException.class).when(patientService).updatePatient(patient);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(String.valueOf(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT request (/patientInfo/update) with already existing patient id must return an HTTP 400 response")
    public void putAlreadyExistingPatient() throws Exception {

        //GIVEN
        Patient patient = Patient.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .build();

        doThrow(PatientAlreadyExistException.class).when(patientService).updatePatient(patient);

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .put("/patientInfo/update")
                .content(String.valueOf(patient))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST request (/patient/add) with a new patient must return an HTTP 200 response")
    public void postANewPatientTest() throws Exception {

        //GIVEN
        Patient patient = Patient.builder()
                .firstName("firstname")
                .lastName("lastname")
                .sex(F)
                .dob("1950-05-05")
                .build();

        //THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/patient/add")
                .param("firstName", patient.getFirstName())
                .param("lastName", patient.getLastName())
                .param("dob", patient.getDob())
                .param("sex", String.valueOf(patient.sex))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
