package com.mediscreen.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "mediscreen-patient-report", url = "${patient.report.proxy.url}")
public interface PatientReportProxy {

    /**
     * Post a report that includes patient demographics, as well as their diabetes risk level
     *
     * @param patId id of the patient whose report is sought
     * @return a string with report by patient id
     */
    @PostMapping("/assess/id")
    String postAssessById(@RequestParam(value = "patId") int patId);

    /**
     * Post a list of reports with all patients with the same lastname that includes patient demographics, as well as their diabetes risk level
     *
     * @param lastName of the patient's list whose report is sought
     * @return a list with with all patients found with the same lastname
     */
    @PostMapping("/assess/familyName")
    List<String> postAssessByName(@RequestParam(value = "familyName") String lastName);
}
