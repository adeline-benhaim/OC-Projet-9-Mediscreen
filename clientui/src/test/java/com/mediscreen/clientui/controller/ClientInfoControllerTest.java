package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.service.ClientInfoService;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
public class ClientInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ClientInfoService clientInfoService;

    @Test
    @DisplayName("GET request (/search/{firstName}/{lastName}) must return a list of patient found by firstName and lastName")
    public void searchByFirstNameAndLastNameTest() throws Exception {

        mockMvc.perform(get("/search/lucas/ferguson"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("SearchPatient"));
    }

    @Test
    @DisplayName("GET request (/search/{firstName}/{lastName}) with unknown firstName and lastName must return an empty list of patient")
    public void searchByUnknownFirstNameAndLastNameTest() throws Exception {

        when(clientInfoService.getPatientList("luc", "ferguson")).thenThrow(FeignException.class);
        mockMvc.perform(get("/search/luc/ferguson"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("SearchPatient"));
    }

    @Test
    @DisplayName("POST request (/search/{firstname}/{lastname}) must search a patient by fistName and lastName")
    public void postSearchPatientTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().firstName("test").lastName("test").sex(PatientBean.Sex.F).dob("1950-05-12").address("address").phone("0656565656").build();

        mockMvc.perform(post("/search/test/test")
                .param("firstName", patientBean.getFirstName())
                .param("lastName", patientBean.getLastName()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/search/" + patientBean.getFirstName() + "/" + patientBean.getLastName()))
                .andReturn().getResponse().containsHeader("PatientBean");
    }

    @Test
    @DisplayName("GET request (/) must redirect /patients page")
    public void getHomePageTest() throws Exception {

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    @DisplayName("GET request (/patients) must return a list of all patients")
    public void getAllPatientsTest() throws Exception {

        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ListPatients"))
                .andReturn().getResponse().containsHeader("allPatientsBeanList");
    }

    @Test
    @DisplayName("GET request (/patients) with no patient must return an empty list of patients")
    public void getPatientsEmptyTest() throws Exception {

        when(clientInfoService.getAllPatient()).thenThrow(FeignException.class);

        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ListPatients"))
                .andReturn().getResponse().containsHeader("AllPatientBeanList");
    }

    @Test
    @DisplayName("GET request (/searchById/{id}) must return a patient found by id")
    public void searchPatientByIdTest() throws Exception {

        when(clientInfoService.getPatientById(1)).thenThrow(FeignException.class);

        mockMvc.perform(get("/searchById/1")
                .param("patId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    @DisplayName("GET request (/updatePatient/{id}) must return form update patient")
    public void getFormUpdatePatientTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(1).firstName("test").lastName("test").sex(PatientBean.Sex.F).dob("1950-05-12").build();
        when(clientInfoService.getPatientById(1)).thenReturn(patientBean);

        mockMvc.perform(get("/updatePatient/1")
                .param("patId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("FormUpdatePatient"));
    }

    @Test
    @DisplayName("GET request (/updatePatient/{id}) with unknown patient id must return list of patients")
    public void getFormUpdatePatientWithUnknownPatIdTest() throws Exception {

        when(clientInfoService.getPatientById(1)).thenThrow(FeignException.class);

        mockMvc.perform(get("/updatePatient/1")
                .param("patId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

    @Test
    @DisplayName("POST request (/updatePatient) must update a patient")
    public void postUpdatePatientTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(1).firstName("test").lastName("test").sex(PatientBean.Sex.F).dob("1950-05-12").build();

        mockMvc.perform(post("/updatePatient")
                .param("patId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/searchById/" + patientBean.getPatId()));
    }

    @Test
    @DisplayName("GET request (/addPatient) must return form new patient")
    public void getFormNewPatientTest() throws Exception {

        mockMvc.perform(get("/addPatient"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("FormNewPatient"));
    }

    @Test
    @DisplayName("POST request (/addPatient) must save a new patient")
    public void postNewPatientTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(12).firstName("test").lastName("test").sex(PatientBean.Sex.F).dob("1950-05-12").build();
        when(clientInfoService.postPatient(patientBean.getFirstName(), patientBean.getLastName(), patientBean.getDob(), patientBean.getSex(), patientBean.getAddress(), patientBean.getPhone()))
                .thenReturn(patientBean);

        mockMvc.perform(post("/addPatient")
                .param("firstName", patientBean.getFirstName())
                .param("lastName", patientBean.getLastName())
                .param("dob", patientBean.getDob())
                .param("sex", String.valueOf(patientBean.getSex())))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/searchById/12"))
                .andReturn().getResponse().containsHeader("PatientBean");
    }

    @Test
    @DisplayName("POST request (/addPatient) with already existing patient must return form new patient")
    public void postAlreadyExistingPatientTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(12).firstName("test").lastName("test").sex(PatientBean.Sex.F).dob("1950-05-12").build();
        when(clientInfoService.postPatient(patientBean.getFirstName(), patientBean.getLastName(), patientBean.getDob(), patientBean.getSex(), patientBean.getAddress(), patientBean.getPhone()))
                .thenThrow(FeignException.class);

        mockMvc.perform(post("/addPatient")
                .param("firstName", patientBean.getFirstName())
                .param("lastName", patientBean.getLastName())
                .param("dob", patientBean.getDob())
                .param("sex", String.valueOf(patientBean.getSex())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("FormNewPatient"))
                .andReturn().getResponse().containsHeader("PatientBean");
    }
}
