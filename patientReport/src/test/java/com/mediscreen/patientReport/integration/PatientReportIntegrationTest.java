package com.mediscreen.patientReport.integration;

import com.mediscreen.patientReport.beans.AppointmentBean;
import com.mediscreen.patientReport.beans.PatientBean;
import com.mediscreen.patientReport.proxies.PatientInfoProxy;
import com.mediscreen.patientReport.proxies.PatientNoteProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.mediscreen.patientReport.beans.PatientBean.Sex.F;
import static com.mediscreen.patientReport.beans.PatientBean.Sex.M;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class PatientReportIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PatientInfoProxy patientInfoProxy;
    @MockBean
    private PatientNoteProxy patientNoteProxy;



    @Test
    @DisplayName("POST request (/assess/id) with an existing patient id must return an HTTP 200 response")
    public void postAssessByIdTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(1).sex(M).dob("2000-05-01").build();
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 3L));

        mockMvc.perform(
                post("/assess/id")
                .param("patId", "1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST request (/assess/familyName) with an existing family name must return an HTTP 200 response")
    public void postAssessByFamilyNameTest() throws Exception {

        PatientBean patientBean = PatientBean.builder().patId(1).firstName("Pierre").lastName("Dupond").sex(M).dob("2000-05-01").build();
        PatientBean patientBean1 = PatientBean.builder().patId(2).firstName("Paul").lastName("Dupond").sex(M).dob("1950-05-01").build();
        PatientBean patientBean2 = PatientBean.builder().patId(3).firstName("Julie").lastName("Dupond").sex(F).dob("2010-05-01").build();
        List<PatientBean> patientBeans = new ArrayList<>();
        patientBeans.add(patientBean);
        patientBeans.add(patientBean1);
        patientBeans.add(patientBean2);
        AppointmentBean appointmentBean = AppointmentBean.builder().patId(1).appointmentId("1").note("fumeur taille poids").build();
        AppointmentBean appointmentBean1 = AppointmentBean.builder().patId(2).appointmentId("2").note("fumeur taille").build();
        AppointmentBean appointmentBean2 = AppointmentBean.builder().patId(3).appointmentId("3").note("fumeur taille").build();
        List<AppointmentBean> appointmentBeanList = new ArrayList<>();
        appointmentBeanList.add(appointmentBean);
        List<AppointmentBean> appointmentBeanList1 = new ArrayList<>();
        appointmentBeanList1.add(appointmentBean1);
        List<AppointmentBean> appointmentBeanList2 = new ArrayList<>();
        appointmentBeanList2.add(appointmentBean2);

        //WHEN
        when(patientInfoProxy.getPatientById(1)).thenReturn(patientBean);
        when(patientInfoProxy.getPatientById(2)).thenReturn(patientBean1);
        when(patientInfoProxy.getPatientById(3)).thenReturn(patientBean2);
        when(patientNoteProxy.findByPatId(1, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList, 2L));
        when(patientNoteProxy.findByPatId(2, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList1, 2L));
        when(patientNoteProxy.findByPatId(3, Pageable.unpaged())).thenReturn(Pair.of(appointmentBeanList2, 2L));
        when(patientInfoProxy.getAllPatient()).thenReturn(patientBeans);

        mockMvc.perform(
                post("/assess/familyName")
                        .param("familyName", "Dupond"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
