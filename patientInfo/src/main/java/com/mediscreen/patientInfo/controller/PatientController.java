package com.mediscreen.patientInfo.controller;

import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @GetMapping("/patientInfo")
    public ResponseEntity<Iterable<Patient>> getAllPatient() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/patientInfo/search/{firstName}/{lastName}")
    public ResponseEntity<List<Patient>> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        try {
            List<Patient> patientList = patientService.getPatientListByFistNameAndLastName(firstName, lastName);
            return ResponseEntity.ok(patientList);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

        @GetMapping("/patientInfo/search/{id}")
        public ResponseEntity<Optional<Patient>> getPatientById ( @PathVariable("id") int id){
        try {
            Optional<Patient> patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        }
    }
