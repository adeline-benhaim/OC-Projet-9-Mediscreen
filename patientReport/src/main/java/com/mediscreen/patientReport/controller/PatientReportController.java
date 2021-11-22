package com.mediscreen.patientReport.controller;

import com.mediscreen.patientReport.service.PatientReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientReportController {

    @Autowired
    PatientReportService patientReportService;

    /**
     * Post a report that includes patient demographics, as well as their diabetes risk level
     *
     * @param patId id of the patient whose report is sought
     * @return a string with report by patient id
     */
    @PostMapping("/assess/id")
    public String postAssessById(@RequestParam("patId") int patId) {
        return patientReportService.calculateRiskLevel(patId);
    }

    /**
     * Post a list of reports with all patients with the same lastname that includes patient demographics, as well as their diabetes risk level
     *
     * @param lastName of the patient's list whose report is sought
     * @return a list with with all patients found with the same lastname
     */
    @PostMapping("/assess/familyName")
    public List<String> postAssessByName(@RequestParam("familyName") String lastName) {
        return patientReportService.calculateRiskLevelFamily(lastName);
    }

}
