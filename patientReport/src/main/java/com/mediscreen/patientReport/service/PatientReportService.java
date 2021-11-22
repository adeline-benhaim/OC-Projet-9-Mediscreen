package com.mediscreen.patientReport.service;

import java.util.List;

public interface PatientReportService {

    /**
     * Create a report that includes patient demographics, as well as their diabetes risk level
     *
     * @param patId id of the patient whose report is sought
     * @return a string with report by patient id
     */
    String calculateRiskLevel(int patId);

    /**
     * Create a list of reports with all patients with the same lastname that includes patient demographics, as well as their diabetes risk level
     *
     * @param lastName of the patient's list whose report is sought
     * @return a list with with all patients found with the same lastname
     */
    List<String> calculateRiskLevelFamily(String lastName);
}
