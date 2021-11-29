package com.mediscreen.clientui.controller;

import com.mediscreen.clientui.beans.PatientBean;
import com.mediscreen.clientui.service.ClientInfoService;
import com.mediscreen.clientui.service.ClientReportService;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
public class ClientReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ClientInfoService clientInfoService;
    @MockBean
    ClientReportService clientReportService;

    @Test
    @DisplayName("GET request (/assess/{id}) must return report view")
    public void getReportByPatientIdTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().build();
        when(clientInfoService.getPatientById(1)).thenReturn(patientBean);
        when(clientReportService.postAssessById(1)).thenReturn("assessById");
        when(clientReportService.postAssessByName(patientBean.getLastName())).thenReturn(List.of("assess1","assess2"));

        mockMvc.perform(get("/assess/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("Report"));
    }

    @Test
    @DisplayName("GET request (/assess/{id}) with unknown patient id must return list of patient")
    public void getReportByUnknownPatientIdTest() throws Exception {

        when(clientInfoService.getPatientById(1)).thenThrow(FeignException.class);

        mockMvc.perform(get("/assess/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/patients"));
    }

}
