package com.mediscreen.patientInfo.service;

import com.mediscreen.patientInfo.exceptions.PatientAlreadyExistException;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.repository.PatientInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientInfoServiceImpl implements PatientInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PatientInfoService.class);

    @Autowired
    PatientInfoRepository patientInfoRepository;

    /**
     * Find all patients
     *
     * @return a list with all patients sorted by lastName in alphabetical order
     */
    public Iterable<Patient> getAllPatients() {
        logger.info("Get all patients");
        return patientInfoRepository.findAllByOrderByLastNameAsc();
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
        List<Patient> patientList = patientInfoRepository.findByFirstNameAndLastName(firstName, lastName);
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
        Optional<Patient> patient = patientInfoRepository.findById(id);
        if (patient.isPresent()) return patient;
        logger.error("No patient found with this id : {} ", id);
        throw new PatientNotFoundException("No patient found with this id : " + id);
    }

    /**
     * Update an existing patient information
     *
     * @param patient to update
     * @return patient with information updated
     */
    @Override
    @Transactional
    public Patient updatePatient(Patient patient) {
        logger.info("Update a patient id : {} ", patient.getPatId());
        Optional<Patient> patientToUpdate = patientInfoRepository.findById(patient.getPatId());
        if (patientToUpdate.isPresent()) {
            try {
                Patient patientUpdated = Patient.builder()
                        .patId(patient.getPatId())
                        .firstName(patient.getFirstName())
                        .lastName(patient.getLastName())
                        .address(patient.getAddress())
                        .dob(patient.getDob())
                        .sex(patient.getSex())
                        .phone(patient.getPhone())
                        .build();
                patientInfoRepository.save(patientUpdated);
                if (patientInfoRepository.findByFirstNameAndLastNameAndDob(patientUpdated.getFirstName(), patientUpdated.getLastName(), patientUpdated.getDob()).size() > 1) {
                    logger.error("Patient {} {} already exist with this birthdate : {}", patient.getFirstName(), patient.getLastName(), patient.getDob());
                    throw new PatientAlreadyExistException("Patient " + patient.getFirstName() + ' ' + patient.getLastName() + " already exist with this birthdate : " + patient.getDob());
                }
                logger.info("Patient id : {} ", patientUpdated.getPatId() + " updated");
                return patientUpdated;
            } catch (DataIntegrityViolationException e) {
                throw new PatientNotFoundException("Mandatory parameters are missing for updating the patient");
            }
        }
        throw new PatientNotFoundException("No patient found with this id : " + patient.getPatId());
    }

    /**
     * Create and save a new patient if don't already exist
     *
     * @param patient information to be created
     * @return patient created if don't already exist
     */
    @Override
    public Patient createPatient(Patient patient) {
        logger.info("Create a new patient : {} {} ", patient.getFirstName(), patient.getLastName());
        if (patientInfoRepository.findByFirstNameAndLastNameAndDob(patient.getFirstName(), patient.getLastName(), patient.getDob()).size() > 0) {
            logger.error("Patient {} {} already exist with this birthdate : {}", patient.getFirstName(), patient.getLastName(), patient.getDob());
            throw new PatientAlreadyExistException("Patient " + patient.getFirstName() + ' ' + patient.getLastName() + " already exist with this birthdate : " + patient.getDob());
        }
        logger.info("New patient created");
        return patientInfoRepository.save(patient);
    }
}
