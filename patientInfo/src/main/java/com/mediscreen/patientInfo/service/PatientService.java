package com.mediscreen.patientInfo.service;

import com.mediscreen.patientInfo.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    List<Patient> getPatientList(String firstName, String lastName);

    /**
     * Find a patient by id
     *
     * @param id of wanted patient
     * @return patient found
     */
    Optional<Patient> getPatientById(int id);
}
