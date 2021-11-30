package com.mediscreen.patientInfo.service;

import com.mediscreen.patientInfo.config.DataSourceTest;
import com.mediscreen.patientInfo.exceptions.PatientAlreadyExistException;
import com.mediscreen.patientInfo.exceptions.PatientNotFoundException;
import com.mediscreen.patientInfo.model.Patient;
import com.mediscreen.patientInfo.repository.PatientInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static com.mediscreen.patientInfo.model.Patient.Sex.F;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientInfoServiceImplTest {

    @Mock
    PatientInfoRepository patientInfoRepository;
    @InjectMocks
    DataSourceTest dataSourceTest;
    @InjectMocks
    PatientInfoServiceImpl patientService;

    @BeforeEach
    void clear() {
        dataSourceTest.clearPatientListMocked();
    }

    @Test
    @DisplayName("Get the list of all the patients present in the data source")
    void getAllPatientsTest() {

        //GIVEN
        when(patientInfoRepository.findAllByOrderByLastNameAsc()).thenReturn(dataSourceTest.getAllPatientsMocked());

        //WHEN
        Iterable<Patient> patientIterable = patientService.getAllPatients();

        //THEN
        assertEquals(patientIterable, dataSourceTest.getPatientMocked());
    }

    @Test
    @DisplayName("Get a list of existing patients found by first and last name")
    void getPatientListByFistNameAndLastNameTest() {

        //GIVEN
        Mockito.when(patientInfoRepository.findByFirstNameAndLastName("firstname1", "lastname1")).thenReturn(dataSourceTest.getAllPatientsMocked());

        //WHEN
        patientService.getPatientListByFistNameAndLastName("firstname1", "lastname1");

        //THEN
        Mockito.verify(patientInfoRepository, Mockito.times(1)).findByFirstNameAndLastName("firstname1", "lastname1");
    }

    @Test
    @DisplayName("Get a list of patients with unknown first and last name throw a patientNotFoundException")
    void getPatientListByUnknownFistNameAndLastNameTest() {

        //GIVEN
        Mockito.when(patientInfoRepository.findByFirstNameAndLastName("firstname2", "lastname2")).thenReturn(new ArrayList<>());

        //THEN
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientListByFistNameAndLastName("firstname2", "lastname2"));
    }

    @Test
    @DisplayName("Get an existing patient found by id")
    void getPatientByIdTest() {

        //GIVEN
        Mockito.when(patientInfoRepository.findById(0)).thenReturn(java.util.Optional.ofNullable(dataSourceTest.getAllPatientsMocked().get(0)));

        //WHEN
        patientService.getPatientById(0);

        //THEN
        Mockito.verify(patientInfoRepository, Mockito.times(1)).findById(0);
    }

    @Test
    @DisplayName("Get a patient with an unknown id")
    void getPatientByUnknownIdTest() {

        //GIVEN
        Mockito.when(patientInfoRepository.findById(2)).thenReturn(Optional.empty());

        //THEN
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(2));
    }

    @Test
    @DisplayName("Update a patient present in Database")
    void updatePatientTest() {

        //GIVEN
        Patient patient = Patient.builder().patId(1).lastName("lastname").build();
        Patient newPatient = Patient.builder().patId(1).lastName("lastnameUpdated").build();

        //WHEN
        Mockito.when(patientInfoRepository.findById(patient.getPatId())).thenReturn(Optional.of(patient));

        //THEN
        assertEquals("lastnameUpdated", patientService.updatePatient(newPatient).getLastName());
        verify(patientInfoRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Try to update a unknown patient ")
    void updateUnknownPatientTest() {

        //GIVEN
        Patient patient = Patient.builder().patId(1).build();

        //WHEN
        Mockito.when(patientInfoRepository.findById(1)).thenReturn(Optional.empty());

        //THEN
        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(patient));
    }

    @Test
    @DisplayName("Try to update a patient with the information of another already existing patient")
    void updatePatientWithInfoOfAnotherAlreadyExistingPatientTest() {

        //GIVEN
        Patient patient = Patient.builder().patId(1).firstName("firstname2").lastName("lastname2").dob("1950-01-01").build();

        //WHEN
        Mockito.when(patientInfoRepository.findById(1)).thenReturn(Optional.of(patient));
        Mockito.when(patientInfoRepository.findByFirstNameAndLastNameAndDob(patient.getFirstName(), patient.getLastName(), patient.getDob())).thenReturn(dataSourceTest.getAllPatientsMocked());

        //THEN
        assertThrows(PatientAlreadyExistException.class, () -> patientService.updatePatient(patient));
    }

    @Test
    @DisplayName("Create a new patient")
    void createANewPatientTest() {

        // GIVEN
        Patient newPatient = Patient.builder()
                .firstName("firstname8")
                .lastName("lastname8")
                .dob("1950-02-10")
                .sex(F)
                .build();
        when(patientInfoRepository.findByFirstNameAndLastNameAndDob(newPatient.getFirstName(),newPatient.getLastName(),newPatient.getDob())).thenReturn(dataSourceTest.getPatientMocked());

        // WHEN
        patientService.createPatient(newPatient);

        // THEN
        verify(patientInfoRepository, Mockito.times(1)).save(newPatient);
    }

    @Test
    @DisplayName("Try to create a patient who already exist")
    void createAPatientAlreadyExistingTest() {

        // GIVEN
        Patient newPatient = Patient.builder()
                .firstName("firstname8")
                .lastName("lastname8")
                .dob("1950-02-10")
                .sex(F)
                .build();
        when(patientInfoRepository.findByFirstNameAndLastNameAndDob(newPatient.getFirstName(),newPatient.getLastName(),newPatient.getDob())).thenReturn(dataSourceTest.getAllPatientsMocked());


        // THEN
        assertThrows(PatientAlreadyExistException.class, ()-> patientService.createPatient(newPatient));
    }
}
