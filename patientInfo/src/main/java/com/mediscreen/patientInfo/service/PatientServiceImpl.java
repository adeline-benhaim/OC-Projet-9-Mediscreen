package com.mediscreen.patientInfo.service;

import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    PatientRepository patientRepository;

    public Iterable<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    @Override
    public List<Patient> getPatientListByFistNameAndLastName(String firstName, String lastName) {
        logger.info("Get a patient by firstName : {} " + "and lastName : {} ", firstName, lastName);
        List<Patient> patientList = patientRepository.findByFirstNameAndLastName(firstName, lastName);
        if (!patientList.isEmpty()) return patientList;
        logger.error("No patient found with this name : {} {} ", firstName, lastName);
        throw new PatientNotFoundException("No patient found with this name : " + firstName + " " + lastName);
    }

    /**
     * Find a patient by id
     *
     * @param id of wanted patient
     * @return patient found
     */
    @Override
    public Optional<Patient> getPatientById(int id) {
        logger.info("Get a patient by id : {} ", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) return patient;
        logger.error("No patient found with this id : {} ", id);
        throw new PatientNotFoundException("No patient found with this id : " + id);
    }
}
