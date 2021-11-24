package com.mediscreen.clientui.service;

import com.mediscreen.clientui.proxies.PatientReportProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientReportServiceImpl implements ClientReportService {

    @Autowired
    PatientReportProxy patientReportProxy;

    @Override
    public String postAssessById(int patId) {
        return patientReportProxy.postAssessById(patId);
    }

    @Override
    public List<String> postAssessByName(String lastName) {
        return patientReportProxy.postAssessByName(lastName);
    }
}
