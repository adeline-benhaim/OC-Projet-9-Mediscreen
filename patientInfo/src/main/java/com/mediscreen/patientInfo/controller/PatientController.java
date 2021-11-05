package com.mediscreen.patientInfo.controller;

import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/patientInfo/search")
    public ResponseEntity<List<Patient>> getPatientList(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        try {
            List<Patient> patientList = patientService.getPatientList(firstName, lastName);
            return ResponseEntity.ok(patientList);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

        @GetMapping("/patientInfo/search/id")
        public ResponseEntity<Optional<Patient>> getPatientById ( @RequestParam("id") int id){
            Optional<Patient> patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        }
    }
