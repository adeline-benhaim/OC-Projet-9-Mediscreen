package com.mediscreen.patientInfo.controller;

import com.mediscreen.patientInfo.exceptions.PatientAlreadyExistException;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api("API for CRUD operations for patientInfo")
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @ApiOperation("Get all patients")
    @GetMapping("/patientInfo")
    public ResponseEntity<Iterable<Patient>> getAllPatient() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @ApiOperation("Get a list of patients found by firstname and lastname")
    @GetMapping("/patientInfo/search/{firstName}/{lastName}")
    public ResponseEntity<List<Patient>> getPatientList(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        try {
            List<Patient> patientList = patientService.getPatientListByFistNameAndLastName(firstName, lastName);
            return ResponseEntity.ok(patientList);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Get a patient found by id")
    @GetMapping("/patientInfo/search/{id}")
    public ResponseEntity<Optional<Patient>> getPatientById(@PathVariable("id") int id) {
        try {
            Optional<Patient> patient = patientService.getPatientById(id);
            return ResponseEntity.ok(patient);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation("Update a patient")
    @PutMapping("/patientInfo/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        try {
            Patient patientToUpdate = patientService.updatePatient(patient);
            return ResponseEntity.ok(patientToUpdate);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PatientAlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Add a new patient")
    @PostMapping("/patient/add")
    public ResponseEntity<Patient> postPatient(@RequestParam("firstName") String firstName,
                                               @RequestParam("lastName") String lastName,
                                               @RequestParam("dob") String birthdate,
                                               @RequestParam("sex") Patient.Sex sex,
                                               @RequestParam(value = "address", required = false) String address,
                                               @RequestParam(value = "phone", required = false) String phone) {
        try {
            Patient patient = Patient.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .dob(birthdate)
                    .sex(sex)
                    .address(address)
                    .phone(phone)
                    .build();
            Patient patientToCreate = patientService.createPatient(patient);
            return ResponseEntity.ok(patientToCreate);
        } catch (PatientAlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
