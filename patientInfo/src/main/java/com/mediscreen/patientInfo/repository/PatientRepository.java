package com.mediscreen.patientInfo.repository;

import com.mediscreen.patientInfo.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    /**
     * Find patients by first and last name
     *
     * @param firstName of wanted patient
     * @param lastName  of wanted patient
     * @return a list of patients whose first and last name are identical to those sought
     */
    List<Patient> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Find all patients
     *
     * @return a list with all patients sorted by lastName in alphabetical order
     */
    Iterable<Patient> findAllByOrderByLastNameAsc();

    /**
     * Find a patient based on their firstName, lastName and birthdate
     *
     * @param firstName of wanted patient
     * @param lastName of wanted patient
     * @param birthdate of wanted patient
     * @return a patient if exist
     */
    Patient findByFirstNameAndLastNameAndDob(String firstName, String lastName, String birthdate);
}
