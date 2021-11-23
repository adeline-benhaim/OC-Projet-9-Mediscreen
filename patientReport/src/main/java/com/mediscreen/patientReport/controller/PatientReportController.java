package com.mediscreen.patientReport.controller;

import com.mediscreen.patientReport.service.PatientReportService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> postAssessById(@RequestParam(value = "patId") int patId) {
        try {
            String report = patientReportService.calculateRiskLevel(patId);
            return ResponseEntity.ok(report);
        } catch (FeignException e) {
            return new ResponseEntity(e.contentUTF8(), HttpStatus.NOT_FOUND);
        }
    }

        /**
         * Post a list of reports with all patients with the same lastname that includes patient demographics, as well as their diabetes risk level
         *
         * @param lastName of the patient's list whose report is sought
         * @return a list with with all patients found with the same lastname
         */
        @PostMapping("/assess/familyName")
        public ResponseEntity<List<String>> postAssessByName (@RequestParam(value = "familyName") String lastName){
            try {
                List<String> reportList = patientReportService.calculateRiskLevelFamily(lastName);
                return ResponseEntity.ok(reportList);
            } catch (FeignException e) {
                return new ResponseEntity(e.contentUTF8(), HttpStatus.NOT_FOUND);
            }

        }

    }
