package com.mediscreen.clientui.service;

import com.mediscreen.clientui.proxies.PatientReportProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientReportServiceTest {

    @Mock
    private PatientReportProxy patientReportProxy;
    @InjectMocks
    private ClientReportServiceImpl clientReportService;

    @Test
    @DisplayName("Post assess by id")
    void postAssessByIdTest() {

        //GIVEN
        int id = 1;

        //WHEN
        clientReportService.postAssessById(id);

        //THEN
        Mockito.verify(patientReportProxy, Mockito.times(1)).postAssessById(1);
    }

    @Test
    @DisplayName("Post assess by familyName")
    void postAssessByfamilyNameTest() {

        //GIVEN
        String familyName = "dupond";

        //WHEN
        clientReportService.postAssessByName(familyName);

        //THEN
        Mockito.verify(patientReportProxy, Mockito.times(1)).postAssessByName(familyName);
    }
}
